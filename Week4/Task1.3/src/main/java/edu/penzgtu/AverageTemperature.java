package edu.penzgtu;

import java.util.HashMap;
// Обьявляем класс AverageTemperature
public class AverageTemperature {
// Изначальные хранимые данные
    public static void main(String[] args) {
        String input = "01@20,02@15,01@25,02@10,03@30";
        HashMap<Integer, Integer> sumMap = new HashMap<>();
        HashMap<Integer, Integer> countMap = new HashMap<>();
// Обработка для сенсоров
        String[] sensorData = input.split(",");
        for (String data : sensorData) {
            String[] values = data.split("@");
            int sensorId = Integer.parseInt(values[0]);
            int temperature = Integer.parseInt(values[1].replace(",", ""));
// Вычисление данных для сенсоров
            sumMap.put(sensorId, sumMap.getOrDefault(sensorId, 0) + temperature);
            countMap.put(sensorId, countMap.getOrDefault(sensorId, 0) + 1);
        }
// Вывод для каждого сенсора
        for (int sensorId : sumMap.keySet()) {
            double averageTemperature = (double) sumMap.get(sensorId) / countMap.get(sensorId);
            System.out.printf("Сенсор id %02d: %.1f\n", sensorId, averageTemperature);
        }
    }
}