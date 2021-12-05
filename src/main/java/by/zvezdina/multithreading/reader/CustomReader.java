package by.zvezdina.multithreading.reader;

import by.zvezdina.multithreading.exception.MultithreadingException;

public interface CustomReader {

    String readFile(String filePath) throws MultithreadingException;
}
