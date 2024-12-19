package edu.penzgtu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class HousePriceAnalysis extends Application {

    private String DATA_FILE;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Запрос пути к файлу у пользователя
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Выбор файла");
        dialog.setHeaderText("Укажите путь к файлу pp-complete.csv:");
        dialog.setContentText("Путь к файлу:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            DATA_FILE = result.get();
            try {
                Map<String, Map<Integer, Double>> cityYearMaxPrices = processData();
                showChart(primaryStage, cityYearMaxPrices);
            } catch (IOException e) {
                showErrorAlert("Ошибка при обработке файла: " + e.getMessage());
            }
        } else {
            primaryStage.close(); // Закрываем окно, если пользователь отменил ввод
        }
    }

    private Map<String, Map<Integer, Double>> processData() throws IOException {
        Map<String, Map<Integer, Double>> cityYearMaxPrices = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            br.readLine(); // Skip header line

            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(new DataProcessingTask(br, cityYearMaxPrices));
            pool.shutdown();

        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла: " + e.getMessage());
        }
        return cityYearMaxPrices;
    }

    private void showChart(Stage primaryStage, Map<String, Map<Integer, Double>> cityYearMaxPrices) {
        primaryStage.setTitle("Гистограммы максимальных цен на жилье");
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Максимальные цены на жилье по городам и годам");
        xAxis.setLabel("Город - Год");
        yAxis.setLabel("Максимальная цена");

        for (Map.Entry<String, Map<Integer, Double>> entry : cityYearMaxPrices.entrySet()) {
            String city = entry.getKey();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(city);
            for (Map.Entry<Integer, Double> yearPrice : entry.getValue().entrySet()) {
                series.getData().add(new XYChart.Data<>(city + " - " + yearPrice.getKey(), yearPrice.getValue()));
            }
            barChart.getData().add(series);
        }

        Scene scene = new Scene(barChart, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Произошла ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class DataProcessingTask extends RecursiveAction {
        private final BufferedReader reader;
        private final Map<String, Map<Integer, Double>> cityYearMaxPrices;

        public DataProcessingTask(BufferedReader reader, Map<String, Map<Integer, Double>> cityYearMaxPrices) {
            this.reader = reader;
            this.cityYearMaxPrices = cityYearMaxPrices;
        }

        @Override
        protected void compute() {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 3) { // Проверка на наличие достаточного количества данных в строке
                        String city = data[1];
                        int year = Integer.parseInt(data[0].substring(0, 4));
                        double price = Double.parseDouble(data[2]);

                        cityYearMaxPrices.computeIfAbsent(city, k -> new HashMap<>()).compute(year, (k, v) -> Math.max(v == null ? 0 : v, price));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}