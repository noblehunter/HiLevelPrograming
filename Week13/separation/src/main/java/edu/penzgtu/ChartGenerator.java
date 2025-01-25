package edu.penzgtu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.Map;

public class ChartGenerator {

    public void generateChart(Map<String, Map<Integer, Double>> maxPriceByCityAndYear) {
        CategoryDataset dataset = createDataset(maxPriceByCityAndYear);
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


    private CategoryDataset createDataset(Map<String, Map<Integer, Double>> maxPriceByCityAndYear) {
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
}