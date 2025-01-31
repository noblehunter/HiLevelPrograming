package edu.penzgtu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.knowm.xchart.*;


import java.util.List;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CSVReaderGUI extends Application {

    @Override
    public void start(Stage stage) {
        // Ось X и Y для графика
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Index");
        yAxis.setLabel("Value");

        // Линейный график
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Sample Chart");

        // Добавляем данные
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data");
        // Добавить данные: series.getData().add(new XYChart.Data<>(x, y));

        lineChart.getData().add(series);

        // Сцена с графиком
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.setTitle("JavaFX Chart Example");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}