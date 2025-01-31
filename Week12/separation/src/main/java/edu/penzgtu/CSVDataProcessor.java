package edu.penzgtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CSVDataProcessor {

    private final int cityIndex = 9;
    private final int priceIndex = 1;


    public List<String[]> readCSV(String filePath, String delimiter) {
        List<String[]> records = new ArrayList<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.err.println("Ошибка: Файл не существует: " + filePath);
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] values = line.split(delimiter, -1);
                records.add(values);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return records;
    }

    public List<Integer> calculateColumnWidths(List<String[]> records) {
        if (records == null || records.isEmpty()) return new ArrayList<>();
        int columnCount = records.get(0).length;
        List<Integer> columnWidths = new ArrayList<>(columnCount);

        for (int i = 0; i < columnCount; i++) {
            int maxWidth = 0;
            for (String[] record : records) {
                if (record.length > i){
                    maxWidth = Math.max(maxWidth, record[i].length());
                }
            }
            columnWidths.add(maxWidth + 2);
        }
        return columnWidths;
    }


    public List<String[]> filterData(List<String[]> csvData, int filterColumnIndex, String filterValue, boolean caseSensitive) {
        if (csvData == null || csvData.isEmpty() || filterColumnIndex < 0) return new ArrayList<>();
        return csvData.stream()
                .filter(record -> record.length > filterColumnIndex &&
                        (caseSensitive ? record[filterColumnIndex].contains(filterValue) : record[filterColumnIndex].toLowerCase().contains(filterValue.toLowerCase())))
                .collect(Collectors.toList());
    }

    public List<String[]> sortData(List<String[]> data, int sortColumnIndex) {
        if (data == null || data.isEmpty() || sortColumnIndex < 0) return new ArrayList<>();

        return data.stream()
                .sorted(Comparator.comparing(record -> {
                    if (record == null || record.length <= sortColumnIndex) return 0;
                    try{
                        return Integer.parseInt(record[sortColumnIndex].replaceAll("\"", ""));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }))
                .collect(Collectors.toList());
    }

    public Map<String, Integer> calculateMaxPricesByCity(List<String[]> csvData) {
        Map<String, Integer> maxPricesByCity = new HashMap<>();
        for (String[] record : csvData) {
            if (record.length > cityIndex && record.length > priceIndex) {
                String city = record[cityIndex];
                try {
                    int price = Integer.parseInt(record[priceIndex].replaceAll("\"", ""));
                    maxPricesByCity.compute(city, (key, oldValue) ->
                            oldValue == null || price > oldValue ? price : oldValue);
                } catch (NumberFormatException e) {
                    System.err.println("Некорректный формат цены, строка не обработана: " + record[priceIndex]);
                }
            }
        }
        return maxPricesByCity;
    }

    public void printUniqueCitiesAndMaxPrices(List<String[]> csvData) {
        Map<String, Integer> maxPricesByCity = calculateMaxPricesByCity(csvData);

        System.out.println("\nУникальные города и их максимальные цены:");
        maxPricesByCity.forEach((city, maxPrice) -> System.out.println(city + ": " + maxPrice));
    }

    public void printPriceHistogram(List<String[]> csvData) {
        Map<String, Integer> maxPricesByCity = calculateMaxPricesByCity(csvData);

        if (maxPricesByCity.isEmpty()) {
            System.out.println("Нет данных для построения гистограммы цен.");
            return;
        }

        System.out.println("\nГистограмма максимальных цен по городам:");
        int maxPrice = maxPricesByCity.values().stream().max(Integer::compare).orElse(0);
        int scale = maxPrice > 100 ? maxPrice / 100 + 1 : 1;


        maxPricesByCity.forEach((city, price) -> {
            System.out.printf("%-20s| ", city);
            int barLength = price / scale;
            for(int i = 0; i < barLength; i++) {
                System.out.print("*");
            }
            System.out.printf(" (%d)%n", price);
        });
    }
}