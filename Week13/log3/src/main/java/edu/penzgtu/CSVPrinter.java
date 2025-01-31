package edu.penzgtu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CSVPrinter {
    private static final Logger logger = LogManager.getLogger(CSVPrinter.class);
    public void printToConsole(List<String[]> records) {
        logger.info("Вывод записей в консоль");
        if (records == null || records.isEmpty()) {
            logger.warn("Нет данных для вывода");
            System.out.println("Нет данных для вывода.");
            return;
        }
        for (String[] record : records) {
            for (String cell : record) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
        logger.info("Вывод записей в консоль завершен");
    }
}


