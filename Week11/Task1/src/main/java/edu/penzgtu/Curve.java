package edu.penzgtu;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
import org.jfree.chart.ChartPanel;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;

public class Curve extends JFrame {

    private final double[] x;
    private final double[] y;
    private final double area;

    public Curve(String title, double[] x, double[] y, double area) {
        super(title);
        this.x = x;
        this.y = y;
        this.area = area;
        setContentPane(createDemoPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Очень важно!
        setSize(800, 600); // Установите начальный размер окна

    }


    private static JFreeChart createChart(double[] x, double[] y) {
        XYSeries series = new XYSeries("Функция");
        for (int i = 0; i < x.length; i++) {
            series.add(x[i], y[i]);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Кривая и криволинейная трапеция",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return chart;
    }


    private JPanel createDemoPanel() {
        JFreeChart chart = createChart(x, y);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.add(new JLabel("Площадь: " + area), BorderLayout.SOUTH);
        return panel;
    }


    //  Место для вашего кода вычисления площади
    private static double calculateArea(double[] x, double[] y) {
        double area = 0;
        for (int i = 0; i < x.length - 1; i++) {
            area += (y[i] + y[i + 1]) * (x[i + 1] - x[i]) / 2.0;
        }
        return area;
    }



    public static void main(String[] args) {

        double[] x = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Примерные данные
        double[] y = {0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100}; // Примерные данные

        double area = calculateArea(x, y);

        Curve demo = new Curve("Криволинейная трапеция", x, y, area);
        demo.setVisible(true); // Не забудьте вызвать setVisible(true)
    }
}