package edu.penzgtu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CSVDataProcessor {

    private static final Logger logger = LogManager.getLogger(CSVDataProcessor.class);
    private final int cityIndex = 11;
    private final int priceIndex = 1;


    public List<String[]> readCSV(String filePath, String delimiter) {
        logger.info("Чтение CSV файла: {}", filePath);
        List<String[]> records = new ArrayList<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            logger.error("Файл не существует: {}", filePath);
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
            logger.info("Чтение файла завершено, прочитано {} строк", records.size());
        } catch (IOException e) {
            logger.error("Ошибка при чтении файла: {}", e.getMessage(), e);
            return null;
        }
        return records;
    }

    public List<Integer> calculateColumnWidths(List<String[]> records) {
        logger.info("Расчет ширины столбцов для вывода");
        if (records == null || records.isEmpty()) {
            logger.warn("Нет данных для расчета ширины столбцов");
            return new ArrayList<>();
        }
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
        logger.info("Расчет ширины столбцов завершен, ширина столбцов: {}", columnWidths);
        return columnWidths;
    }


    public List<String[]> filterData(List<String[]> csvData, int filterColumnIndex, String filterValue, boolean caseSensitive) {
        logger.info("Фильтрация данных по столбцу {}, значению {}, с учетом регистра: {}", filterColumnIndex, filterValue, caseSensitive);
        if (csvData == null || csvData.isEmpty() || filterColumnIndex < 0) {
            logger.warn("Нет данных или некорректный индекс столбца для фильтрации");
            return new ArrayList<>();
        }
        List<String[]> filteredData = csvData.stream()
                .filter(record -> record.length > filterColumnIndex &&
                        (caseSensitive ? record[filterColumnIndex].contains(filterValue) : record[filterColumnIndex].toLowerCase().contains(filterValue.toLowerCase())))
                .collect(Collectors.toList());
        logger.info("Фильтрация данных завершена, отфильтровано {} строк", filteredData.size());
        return filteredData;
    }

    public List<String[]> sortData(List<String[]> data, int sortColumnIndex) {
        logger.info("Сортировка данных по столбцу: {}", sortColumnIndex);
        if (data == null || data.isEmpty() || sortColumnIndex < 0) {
            logger.warn("Нет данных или некорректный индекс столбца для сортировки");
            return new ArrayList<>();
        }
        List<String[]> sortedData = data.stream()
                .sorted(Comparator.comparing(record -> {
                    if (record == null || record.length <= sortColumnIndex) return 0;
                    try{
                        return Integer.parseInt(record[sortColumnIndex].replaceAll("\"", ""));
                    } catch (NumberFormatException e) {
                        logger.error("Ошибка при парсинге цены, будет использована 0", e);
                        return 0;
                    }
                }))
                .collect(Collectors.toList());
        logger.info("Сортировка данных завершена, отсортировано {} строк", sortedData.size());
        return sortedData;
    }

    public Map<String, Integer> calculateMaxPricesByCity(List<String[]> csvData) {
        logger.info("Расчет максимальных цен по городам");
        Map<String, Integer> maxPricesByCity = new HashMap<>();
        for (String[] record : csvData) {
            if (record.length > cityIndex && record.length > priceIndex) {
                String city = record[cityIndex];
                try {
                    int price = Integer.parseInt(record[priceIndex].replaceAll("\"", ""));
                    maxPricesByCity.compute(city, (key, oldValue) ->
                            oldValue == null || price > oldValue ? price : oldValue);
                } catch (NumberFormatException e) {
                    logger.error("Некорректный формат цены, строка не обработана: {}", record[priceIndex], e);
                }
            }
        }
        logger.info("Расчет максимальных цен по городам завершен, найдено {} городов", maxPricesByCity.size());
        return maxPricesByCity;
    }

    public void printUniqueCitiesAndMaxPrices(List<String[]> csvData) {
        Map<String, Integer> maxPricesByCity = calculateMaxPricesByCity(csvData);

        System.out.println("\nУникальные города и их максимальные цены:");
        maxPricesByCity.forEach((city, maxPrice) -> System.out.println(city + ": " + maxPrice));
        logger.info("Вывод уникальных городов и их максимальных цен завершен");
    }

    public void printPriceHistogram(List<String[]> csvData) {
        Map<String, Integer> maxPricesByCity = calculateMaxPricesByCity(csvData);

        if (maxPricesByCity.isEmpty()) {
            logger.warn("Нет данных для построения гистограммы цен");
            System.out.println("Нет данных для построения гистограммы цен.");
            return;
        }
        logger.info("Построение гистограммы цен");
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
        logger.info("Построение гистограммы цен завершено");
    }
}
