package edu.penzgtu;

import java.util.List;

public class CSVPrinter {
    public void printRecords(List<String[]> records, List<Integer> columnWidths) {
        if (records == null || records.isEmpty()) {
            System.out.println("Нет данных для вывода.");
            return;
        }
        for (String[] record : records) {
            for (int i = 0; i < record.length; i++) {
                String value = record[i];
                int width = (i < columnWidths.size()) ? columnWidths.get(i) : 15; // 15 - значение по умолчанию
                System.out.printf("%-" + width + "s| ", value.length() > width ? value.substring(0, width - 3) + "..." : value); // усекаем длинные строки
            }
            System.out.println();
        }
    }
}
