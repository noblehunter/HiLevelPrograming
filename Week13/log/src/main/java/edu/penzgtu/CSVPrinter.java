package edu.penzgtu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CSVPrinter {
    private static final Logger logger = LogManager.getLogger(CSVPrinter.class);
    public void printRecords(List<String[]> records, List<Integer> columnWidths) {
        logger.info("Вывод записей в консоль");
        if (records == null || records.isEmpty()) {
            logger.warn("Нет данных для вывода");
            System.out.println("Нет данных для вывода.");
            return;
        }
        for (String[] record : records) {
            for (int i = 0; i < record.length; i++) {
                String value = record[i];
                int width = (i < columnWidths.size()) ? columnWidths.get(i) : 15;
                System.out.printf("%-" + width + "s| ", value.length() > width ? value.substring(0, width - 3) + "..." : value);
            }
            System.out.println();
        }
        logger.info("Вывод записей в консоль завершен");
    }
}