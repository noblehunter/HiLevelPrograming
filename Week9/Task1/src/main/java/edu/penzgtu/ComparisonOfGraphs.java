package edu.penzgtu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.HashMap;

public class ComparisonOfGraphs {

    public static class FibonacciClassic {
        public static long fibonacci(int n) {
            if (n <= 1) {
                return n;
            }
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static class FibonacciOptimized {
        private static HashMap<Integer, Long> cache = new HashMap<>();

        public static long fibonacci(int n) {
            if (n <= 1) {
                return n;
            }
            if (cache.containsKey(n)) {
                return cache.get(n);
            }
            long result = fibonacci(n - 1) + fibonacci(n - 2);
            cache.put(n, result);
            return result;
        }
    }

    public static void main(String[] args) {
        int maxN = 40; // Максимальное значение n для сравнения
        XYSeries classicSeries = new XYSeries("Classic");
        XYSeries optimizedSeries = new XYSeries("Optimized");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(classicSeries);
        dataset.addSeries(optimizedSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Fibonacci Time Complexity",
                "n (Fibonacci Number)",
                "Time (nanoseconds)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        JFrame frame = new JFrame("Fibonacci Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);

        // Флаг для остановки потока
        final boolean[] running = {true};

        // Создаем отдельный поток для обновления графика
        new Thread(() -> {
            for (int i = 1; i <= maxN && running[0]; i++) {
                long startTime = System.nanoTime();
                FibonacciClassic.fibonacci(i);
                long endTime = System.nanoTime();
                classicSeries.add(i, (endTime - startTime));

                startTime = System.nanoTime();
                FibonacciOptimized.fibonacci(i);
                endTime = System.nanoTime();
                optimizedSeries.add(i, (endTime - startTime));

                // Обновляем график каждые 100 миллисекунд
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Ожидаем 10 секунд
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Останавливаем построение графика
        running[0] = false;

        // Теперь окно останется открытым до тех пор, пока пользователь его не закроет
    }
}

