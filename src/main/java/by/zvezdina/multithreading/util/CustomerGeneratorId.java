package by.zvezdina.multithreading.util;

public class CustomerGeneratorId {

    private static long count;

    public static long generateId() {
        return ++count;
    }
}
