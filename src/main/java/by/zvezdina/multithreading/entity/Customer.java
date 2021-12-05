package by.zvezdina.multithreading.entity;

import by.zvezdina.multithreading.util.CustomerGeneratorId;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Customer implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private long customerId;
    private CustomerState state;

    public Customer() {
        this.customerId = CustomerGeneratorId.generateId();
        this.state = CustomerState.values()[(int) (Math.random() * CustomerState.values().length)];
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(5));
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread interrupted {}", e);
        }

        McDonalds mcDonalds = McDonalds.getInstance();
        logger.log(Level.INFO, "New customer in McDonald's: {}", this);

        switch (state) {
            case NEW_COMER:
                CashDesk desk = mcDonalds.findDeskWithShortestQueue();
                logger.log(Level.INFO, "Customer №{} found shortest queue at desk №{}",
                        customerId, desk.getCashDeskId());
                desk.acceptOrder();
            case HAS_PREORDER:
                FoodStock foodStock = mcDonalds.getFoodStock();
                logger.log(Level.INFO, "Customer №{} lined up to food stock", customerId);
                foodStock.pickUpOrder();
            break;
        }

        logger.log(Level.INFO, "Customer №{} left McDonald's", customerId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (customerId != customer.customerId) return false;
        return state == customer.state;
    }

    @Override
    public int hashCode() {
        int result = (int) (customerId ^ (customerId >>> 32));
        result = 31 * result + state.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("customerId=").append(customerId);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
