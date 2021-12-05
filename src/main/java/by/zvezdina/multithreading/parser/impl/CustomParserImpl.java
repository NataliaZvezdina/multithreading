package by.zvezdina.multithreading.parser.impl;

import by.zvezdina.multithreading.parser.CustomParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class CustomParserImpl implements CustomParser {
    private static final Logger logger = LogManager.getLogger();
    private static final CustomParserImpl instance = new CustomParserImpl();
    private static final String DELIMITER = "\\s";

    private CustomParserImpl() {}

    public static CustomParserImpl getInstance() {
        return instance;
    }

    @Override
    public int[] parseLine(String line) {
        String[] stringNumbers = line.split(DELIMITER);
        int[] numbers = Arrays.stream(stringNumbers)
                .mapToInt(Integer::parseInt).toArray();

        logger.log(Level.INFO, "Result of parsing line: " + Arrays.toString(numbers));
        return numbers;
    }
}
