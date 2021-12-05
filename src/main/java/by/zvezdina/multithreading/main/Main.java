package by.zvezdina.multithreading.main;

import by.zvezdina.multithreading.entity.Customer;
import by.zvezdina.multithreading.entity.factory.CustomerFactory;
import by.zvezdina.multithreading.exception.MultithreadingException;
import by.zvezdina.multithreading.parser.CustomParser;
import by.zvezdina.multithreading.parser.impl.CustomParserImpl;
import by.zvezdina.multithreading.reader.CustomReader;
import by.zvezdina.multithreading.reader.impl.CustomReaderImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    private static final String FILE_PATH = "data/info.txt";

    public static void main(String[] args) {

        CustomReader reader = CustomReaderImpl.getInstance();
        String line = "";

        try {
            line = reader.readFile(FILE_PATH);
        } catch (MultithreadingException e) {
            logger.log(Level.ERROR, e);
        }

        CustomParser parser = CustomParserImpl.getInstance();
        int[] ints = parser.parseLine(line);
        int customNumber = ints[1];

        List<Customer> customers = CustomerFactory.createCustomer(customNumber);

        ExecutorService executor = Executors.newFixedThreadPool(customers.size());
        customers.forEach(executor::submit);
        executor.shutdown();
    }
}
