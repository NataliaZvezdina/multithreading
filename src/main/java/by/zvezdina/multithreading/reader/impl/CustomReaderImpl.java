package by.zvezdina.multithreading.reader.impl;

import by.zvezdina.multithreading.exception.MultithreadingException;
import by.zvezdina.multithreading.reader.CustomReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;

public class CustomReaderImpl implements CustomReader {
    private static final Logger logger = LogManager.getLogger();
    private static final CustomReaderImpl instance = new CustomReaderImpl();

    private CustomReaderImpl() {}

    public static CustomReaderImpl getInstance() {
        return instance;
    }

    @Override
    public String readFile(String filePath) throws MultithreadingException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filePath);
        File file = new File(resource.getFile());

        String lines = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String temp;
            while ((temp = reader.readLine()) != null) {
                lines += temp;
            }

        } catch (IOException e) {
            throw new MultithreadingException("Error while reading file " + filePath, e);
        }

        logger.log(Level.INFO, "File {} was read, content: {}", filePath, lines);
        return lines;
    }
}
