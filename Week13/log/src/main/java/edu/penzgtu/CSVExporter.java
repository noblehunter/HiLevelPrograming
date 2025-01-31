package edu.penzgtu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {
    private static final Logger logger = LogManager.getLogger(CSVExporter.class);
    public void exportToCSV(List<String[]> data, String filePath, String delimiter) {
        logger.info("Экспорт данных в файл {}", filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] record : data) {
                bw.write(String.join(delimiter, record));
                bw.newLine();
            }
            System.out.println("Данные успешно экспортированы в: " + filePath);
            logger.info("Экспорт данных в файл {} завершен", filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            logger.error("Ошибка при записи в файл: {}", e.getMessage(), e);
        }
    }
}