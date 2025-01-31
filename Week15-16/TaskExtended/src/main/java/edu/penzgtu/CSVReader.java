package edu.penzgtu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.*;

public class CSVReader extends Application {

    private static HBox hbox = new HBox();  // Создание HBox для отображения графика

    public static void main(String[] args) {
        launch(args);  // Запуск JavaFX приложения
    }

    @Override
    public void start(Stage primaryStage) {
        // Создаем кнопку для отображения графика
        Button showChartButton = new Button("Show Chart");
        showChartButton.setOnAction(e -> showChart(csvData, 0, "line"));

        // Создаем HBox для размещения компонентов на экране
        HBox root = new HBox();
        root.getChildren().add(showChartButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("CSV Data Processor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Пример данных CSV
    private static List<String[]> csvData = Arrays.asList(
            new String[]{"1", "10"},
            new String[]{"2", "20"},
            new String[]{"3", "30"},
            new String[]{"4", "40"}
    );

    private static void showChart(List<String[]> data, int columnIndex, String chartType) {
        if (data == null || data.isEmpty()) {
            System.out.println("Нет данных для построения графика.");
            return;
        }

        List<String> labels = new ArrayList<>();
        List<Number> values = new ArrayList<>();

        for (String[] record : data) {
            if (record.length > columnIndex) {
                try {
                    values.add(Double.parseDouble(record[columnIndex].replaceAll("\"", "")));
                    labels.add(String.valueOf(data.indexOf(record)));
                } catch (NumberFormatException e) {
                    System.err.println("Некорректное значение для графика: " + record[columnIndex]);
                }
            }
        }

        if (values.isEmpty()) {
            System.err.println("Нет корректных числовых данных для графика.");
            return;
        }

        // Создаем оси X и Y для JavaFX LineChart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Index");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");

        // Создаем сам график
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(chartType + " Chart");

        // Добавляем данные в серию
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data");

        for (int i = 0; i < labels.size(); i++) {
            series.getData().add(new XYChart.Data<>(Double.parseDouble(labels.get(i)), values.get(i)));
        }

        // Добавляем серию на график
        lineChart.getData().add(series);

        // Добавляем график в hbox
        hbox.getChildren().clear();  // Очищаем hbox перед добавлением нового графика
        hbox.getChildren().add(lineChart);  // Добавляем график в hbox
    }
}