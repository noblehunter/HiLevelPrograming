package edu.penzgtu;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {
    public void exportToCSV(List<String[]> data, String filePath, String delimiter) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] record : data) {
                bw.write(String.join(delimiter, record));
                bw.newLine();
            }
            System.out.println("Данные успешно экспортированы в: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}