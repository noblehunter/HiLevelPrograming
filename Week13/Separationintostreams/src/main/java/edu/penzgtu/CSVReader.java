import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVReader {

    // Порядок столбцов в соответствии с предоставленным описанием.
    private static final String[] HEADERS = {
            "Уникальный идентификатор транзакции", "Цена", "Дата передачи", "Почтовый индекс", "Тип недвижимости",
            "Старый/Новый", "Продолжительность", "ПАОН", "САОН", "Улица", "Местность", "Город/Город", "Округ", "Графство",
            "Тип категории", "PPD", "Статус записи"
    };

    // Индексы столбцов (используем константы, чтобы не ошибиться с порядком)
    private static final int PRICE_INDEX = 1;
    private static final int DATE_INDEX = 2;
    private static final int CITY_INDEX = 11;

    private static final Pattern DATE_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})"); // регулярное выражение для извлечения года

    private static final Map<String, Map<Integer, Double>> maxPriceByCityAndYear = new HashMap<>();

    public static void processLine(String line, String delimiter) {
        String[] values = line.split(delimiter);

        if (values.length <= Math.max(CITY_INDEX, Math.max(PRICE_INDEX, DATE_INDEX))) {
            System.out.println("Пропускаем строку: " + line + ". Не достаточно данных");
            return; // пропускаем строку, если данные не полные.
        }

        String city = values[CITY_INDEX].trim();
        String dateStr = values[DATE_INDEX].trim();
        String priceStr = values[PRICE_INDEX].trim();
        try {
            Integer year = getYearFromDate(dateStr);
            double price = parsePrice(priceStr);

            System.out.println("Город: " + city + ", год: " + year + ", цена: " + price);

            if (city != null && !city.isEmpty() && year != null && price > 0) {
                maxPriceByCityAndYear.computeIfAbsent(city, k -> new HashMap<>())
                        .compute(year, (k, currentMax) -> currentMax == null || price > currentMax ? price : currentMax);
            } else {
                System.out.println("Строка не подходит по условию. Город: " + city + ", год: " + year + ", цена: " + price);
            }
        } catch (Exception e) {
            System.out.println("Проблема при обработке строки: " + line + ". Ошибка: " + e.getMessage());
        }
    }

    private static double parsePrice(String priceStr) {
        try {
            // удаляем все символы кроме цифр и точки
            String normalizedPriceStr = priceStr.replaceAll("[^\\d.]", "");
            return Double.parseDouble(normalizedPriceStr);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при парсинге цены: " + priceStr);
            return 0; // return zero if the price cannot be parsed
        }
    }

    private static Integer getYearFromDate(String dateStr) {
        System.out.println("Попытка извлечь год из: " + dateStr);
        try {
            // Пытаемся распарсить с помощью нескольких форматов
            LocalDate date = parseDateWithFormat(dateStr, "yyyy-MM-dd HH:mm");
            if (date != null) return date.getYear();

            date = parseDateWithFormat(dateStr, "yyyy-MM-dd");
            if (date != null) return date.getYear();

            date = parseDateWithFormat(dateStr, "dd/MM/yyyy");
            if (date != null) return date.getYear();

            date = parseDateWithFormat(dateStr, "MM/dd/yyyy");
            if (date != null) return date.getYear();

            Matcher matcher = DATE_PATTERN.matcher(dateStr);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
            System.out.println("Не удалось распарсить дату: " + dateStr);
            return null;
        } catch (Exception e) {
            System.out.println("Ошибка при парсинге даты: " + e.getMessage());
            return null;
        }
    }


    private static LocalDate parseDateWithFormat(String dateStr, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }


    public static void readCSVWithThreads(String filePath, String delimiter) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            System.out.println(formatHeaders());
            String line;

            while ((line = br.readLine()) != null) {
                String finalLine = line;
                executor.submit(() -> processLine(finalLine, delimiter));
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии BufferedReader: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException ex) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        createAndShowGui(); //Вызываем метод создания графика после завершения работы потоков.
    }


    private static String formatHeaders() {
        StringBuilder sb = new StringBuilder();
        for (String header : HEADERS) {
            sb.append(header).append(" | ");
        }
        return sb.toString();
    }


    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println("Данные для гистограммы:");
        for (Map.Entry<String, Map<Integer, Double>> cityEntry : maxPriceByCityAndYear.entrySet()) {
            String city = cityEntry.getKey();
            Map<Integer, Double> yearPriceMap = cityEntry.getValue();
            for (Map.Entry<Integer, Double> yearEntry : yearPriceMap.entrySet()) {
                Integer year = yearEntry.getKey();
                Double maxPrice = yearEntry.getValue();
                dataset.addValue(maxPrice, city, year.toString());
                System.out.println("  Город: " + city + ", год: " + year + ", максимальная цена: " + maxPrice);
            }
        }
        return dataset;
    }


    private static void createAndShowGui() {
        CategoryDataset dataset = createDataset();
        JFreeChart barChart = ChartFactory.createBarChart(
                "Максимальные цены в городах по годам",
                "Год",
                "Максимальная цена",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 600)); // задаём размеры
        JFrame frame = new JFrame("Гистограмма максимальных цен");
        frame.setContentPane(chartPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath;
        String delimiter = ",";

        System.out.print("Введите путь к CSV файлу: ");
        filePath = scanner.nextLine();

        readCSVWithThreads(filePath, delimiter);
        scanner.close();
    }
}