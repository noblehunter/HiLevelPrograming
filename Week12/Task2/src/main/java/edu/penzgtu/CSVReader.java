package edu.penzgtu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class CSVReader {

    private static final Logger logger = LogManager.getLogger(CSVReader.class);

    public static void main(String[] args) {
        logger.info("Запуск CSVReader");
        Scanner scanner = new Scanner(System.in);
        String filePath;
        String delimiter;
        int filterColumnIndex = -1;
        String filterValue = "";
        int sortColumnIndex = -1;
        String exportPath = "";


        System.out.print("Введите путь к CSV файлу: ");
        filePath = scanner.nextLine();

        System.out.print("Введите разделитель (или нажмите Enter для ','): ");
        String inputDelimiter = scanner.nextLine();
        delimiter = inputDelimiter.isEmpty() ? "," : inputDelimiter;


        if (delimiter.isEmpty()) {
            System.err.println("Разделитель не может быть пустым, использован разделитель по умолчанию (',')");
            delimiter = ",";
        }
        logger.info("Путь к файлу: {}, разделитель: {}", filePath, delimiter);

        CSVDataProcessor processor = new CSVDataProcessor();
        List<String[]> csvData = processor.readCSV(filePath, delimiter);

        CSVReader reader = new CSVReader();
        reader.displayHistogram(csvData);

        if (csvData != null) {
            CSVPrinter printer = new CSVPrinter();
            printer.printToConsole(csvData);
        }


    }
    private void displayHistogram(List<String[]> csvData) {
        if (csvData == null || csvData.isEmpty()) {
            System.err.println("Нет данных для создания гистограммы.");
            return;
        }

        // Предполагаем, что первый столбец содержит категории, а второй - значения
        CategoryDataset dataset = createDataset(csvData);
        if (dataset == null) {
            System.err.println("Невозможно создать гистограмму: неверный формат данных.");
            return;
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Гистограмма данных CSV", // Заголовок гистограммы
                "Города",               // Заголовок оси X
                "Максимальная цена",                 // Заголовок оси Y
                dataset,                 // Данные гистограммы
                PlotOrientation.VERTICAL, // Ориентация гистограммы
                true,                     // Легенда
                true,                     // Подсказки
                false                     // URL-адреса
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));

        JFrame frame = new JFrame("Гистограмма");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }


    private CategoryDataset createDataset(List<String[]> csvData) {
        if (csvData == null || csvData.isEmpty()) {
            return null;
        }

        for (String[] row : csvData) {
            if (row.length < 12) {
                System.err.println("Ошибка: Недостаточно столбцов в данных CSV.");
                return null;
            }
        }

        CSVDataProcessor processor = new CSVDataProcessor();
        int priceIndex = processor.getPriceIndex(); // Get price index using getter

        Map<String, Double> maxPrices = new HashMap<>();
        for (String[] row : csvData) {
            String city = row[11].replace("\"", "");
            if (row[priceIndex] == null || row[priceIndex].isEmpty()) {
                System.err.println("Пропущено пустое значение в столбце данных");
                continue;
            }
            String priceStr = row[priceIndex].replace("\"", "");
            try {
                double price = Double.parseDouble(priceStr);
                maxPrices.compute(city, (k, v) -> v == null ? price : Math.max(v, price));
            } catch (NumberFormatException e) {
                System.err.println("Не удалось распарсить цену: " + priceStr + ". Пропущено.");
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : maxPrices.entrySet()) {
            dataset.addValue(entry.getValue(), "Максимальная цена", entry.getKey());
        }
        return dataset;
    }
}


