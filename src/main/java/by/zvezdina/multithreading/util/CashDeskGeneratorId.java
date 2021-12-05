package by.zvezdina.multithreading.util;

public class CashDeskGeneratorId {

    private static long count;

    public static long generateId() {
        return ++count;
    }
}
