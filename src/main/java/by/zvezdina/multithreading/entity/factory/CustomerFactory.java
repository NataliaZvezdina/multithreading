package by.zvezdina.multithreading.entity.factory;

import by.zvezdina.multithreading.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerFactory {

    public static Customer createCustomer() {
        return new Customer();
    }

    public static List<Customer> createCustomer(int number) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            customers.add(new Customer());
        }
        return customers;
    }
}
