package by.zvezdina.multithreading.entity;

import by.zvezdina.multithreading.exception.MultithreadingException;
import by.zvezdina.multithreading.parser.CustomParser;
import by.zvezdina.multithreading.parser.impl.CustomParserImpl;
import by.zvezdina.multithreading.reader.CustomReader;
import by.zvezdina.multithreading.reader.impl.CustomReaderImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class McDonalds {
    private static final Logger logger = LogManager.getLogger();

    private static McDonalds instance;
    private static AtomicBoolean instanceMark = new AtomicBoolean(false);

    private List<CashDesk> desks;
    private ReentrantLock desksLock = new ReentrantLock();
    private static final String FILE_PATH = "data/info.txt";
    private FoodStock foodStock;


    private McDonalds() {
        CustomReader reader = CustomReaderImpl.getInstance();
        String line = "";
        try {
            line = reader.readFile(FILE_PATH);
        } catch (MultithreadingException e) {
            logger.log(Level.ERROR, "Error while initializing McDonald's: {}", e);
        }

        CustomParser parser = CustomParserImpl.getInstance();
        int deskNumber = parser.parseLine(line)[0];

        desks = new ArrayList<>();
        for (int i = 0; i < deskNumber; i++) {
            CashDesk desk = new CashDesk();
            desks.add(desk);
        }

        foodStock = FoodStock.getInstance();
    }

    public static McDonalds getInstance() {
        while (instance == null) {
            if (instanceMark.compareAndSet(false, true)) {
                instance = new McDonalds();
            }
        }
        return instance;
    }

    public CashDesk findDeskWithShortestQueue() {
        CashDesk desk = null;
        try {
            desksLock.lock();
            Optional<CashDesk> optionalCashDesk = desks.stream()
                    .min(Comparator.comparingInt(CashDesk::getQueueSize));
            desk = optionalCashDesk.get();
            desk.incrementQueueSize();
        } finally {
            desksLock.unlock();
        }
        return desk;
    }

    public FoodStock getFoodStock() {
        return foodStock;
    }
}
