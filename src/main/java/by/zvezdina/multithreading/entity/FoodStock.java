package by.zvezdina.multithreading.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class FoodStock {
    private static final Logger logger = LogManager.getLogger();
    private static ReentrantLock locker;

    private FoodStock() {
        locker = new ReentrantLock(true);
    }

    private static class SingletonHolder {
        private static final FoodStock instance = new FoodStock();
    }

    public static FoodStock getInstance() {
        return SingletonHolder.instance;
    }

    public void pickUpOrder() {
        long customerId = Long.parseLong(Thread.currentThread().getName().substring(14));

        try {
            locker.lock();
            logger.log(Level.INFO, "Customer №{} is been served at food stock", customerId);
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Error while processing at food stock: {}", e);
            Thread.currentThread().interrupt();
        } finally {
            locker.unlock();
        }
        logger.log(Level.INFO, "Customer №{} got his order and released food stock", customerId);
    }
}
