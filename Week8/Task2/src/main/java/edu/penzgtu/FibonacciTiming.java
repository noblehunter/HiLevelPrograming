package edu.penzgtu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FibonacciTiming {

    public static void main(String[] args) {
        List<Integer> nValues = new ArrayList<>();
        List<Long> classicTimes = new ArrayList<>();
        List<Long> optimizedTimes = new ArrayList<>();

        for (int n = 0; n <= 40; n++) {
            // Классическая реализация
            long startTime = System.nanoTime();
            FibonacciClassic.fibonacci(n);
            long endTime = System.nanoTime();
            classicTimes.add(endTime - startTime);
            nValues.add(n);

            // Оптимизированная реализация
            FibonacciOptimized optimizedFibonacci = new FibonacciOptimized();
            startTime = System.nanoTime();
            optimizedFibonacci.fibonacci(n);
            endTime = System.nanoTime();
            optimizedTimes.add(endTime - startTime);
        }

        // Построение графиков
        createChart(nValues, classicTimes, optimizedTimes);
    }

    private static void createChart(List<Integer> nValues, List<Long> classicTimes, List<Long> optimizedTimes) {
        XYSeries classicSeries = new XYSeries("Classic Fibonacci");
        XYSeries optimizedSeries = new XYSeries("Optimized Fibonacci");

        for (int i = 0; i < nValues.size(); i++) {
            classicSeries.add(nValues.get(i), classicTimes.get(i));
            optimizedSeries.add(nValues.get(i), optimizedTimes.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(classicSeries);
        dataset.addSeries(optimizedSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Fibonacci Execution Time Comparison",
                "n (Fibonacci Number)",
                "Time (ns)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame("Fibonacci Timing");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
