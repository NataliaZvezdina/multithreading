package by.zvezdina.multithreading.entity;

import by.zvezdina.multithreading.util.CashDeskGeneratorId;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class CashDesk {
    private static final Logger logger = LogManager.getLogger();
    private long cashDeskId;
    private AtomicInteger queueSize;
    private ReentrantLock locker;

    public CashDesk() {
        this.cashDeskId = CashDeskGeneratorId.generateId();
        this.queueSize = new AtomicInteger(0);
        this.locker = new ReentrantLock(true);
    }

    public void acceptOrder() {
        long customerId = Long.parseLong(Thread.currentThread().getName().substring(14));
        try {
            locker.lock();
            logger.log(Level.INFO, "Customer №{} is been served at cash desk №{}",
                    customerId, getCashDeskId());
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Error while accepting order at cash desk: {}", e);
        } finally {
            locker.unlock();
        }
        decrementQueueSize();
    }

    public long getCashDeskId() {
        return cashDeskId;
    }

    public void incrementQueueSize() {
        queueSize.incrementAndGet();
    }

    public void decrementQueueSize() {
        queueSize.decrementAndGet();
    }

    public int getQueueSize() {
        return queueSize.get();
    }
}
