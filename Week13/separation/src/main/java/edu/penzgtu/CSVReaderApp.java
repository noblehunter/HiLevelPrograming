package edu.penzgtu;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import edu.penzgtu.CSVParser;
import edu.penzgtu.DataAnalyzer;
import edu.penzgtu.ChartGenerator;
import edu.penzgtu.DataRecord;


public class CSVReaderApp {

    private static final String[] HEADERS = {
            "Уникальный идентификатор транзакции", "Цена", "Дата передачи", "Почтовый индекс", "Тип недвижимости",
            "Старый/Новый", "Продолжительность", "ПАОН", "САОН", "Улица", "Местность", "Город/Город", "Округ", "Графство",
            "Тип категории", "PPD", "Статус записи"
    };

    private static final int PRICE_INDEX = 1;
    private static final int DATE_INDEX = 2;
    private static final int CITY_INDEX = 11;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath;
        String delimiter = ",";


        System.out.print("Введите путь к CSV файлу: ");
        filePath = scanner.nextLine();

        CSVParser csvParser = new CSVParser(delimiter, HEADERS, PRICE_INDEX, DATE_INDEX, CITY_INDEX);
        DataAnalyzer dataAnalyzer = new DataAnalyzer();
        ChartGenerator chartGenerator = new ChartGenerator();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executor.submit(() -> {
            List<DataRecord> records = csvParser.parseFile(filePath);
            Map<String, Map<Integer, Double>> maxPriceByCityAndYear = dataAnalyzer.analyze(records);
            chartGenerator.generateChart(maxPriceByCityAndYear);
        });
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        scanner.close();
    }
}