package edu.penzgtu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ShannonEntropyCalculator {

    public static void main(String[] args) {
        String inputFilePath = "input.txt"; // Путь к входному файлу
        String outputFilePath = "output.txt"; // Путь к выходному файлу

        try {
            double entropy = calculateShannonEntropy(inputFilePath);
            writeResultToFile(outputFilePath, entropy);
            System.out.println("Энтропия по Шеннону: " + entropy);
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            if (e.getMessage().contains("No such file or directory")) {
                System.err.println("Проверьте, существует ли файл: " + inputFilePath);
            } else if (e.getMessage().contains("Permission denied")) {
                System.err.println("У вас нет прав доступа к файлу: " + inputFilePath);
            }
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static double calculateShannonEntropy(String filePath) throws IOException {
        int[] frequency = new int[256]; // Массив для хранения частоты символов
        int totalCharacters = 0; // Инициализация общего количества символов
        double entropy = 0.0; // Инициализация переменной энтропии

        // Чтение файла построчно
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

        String line;
        while ((line = reader.readLine()) != null) {
            for (char c : line.toCharArray()) {
                frequency[c]++; // Увеличиваем частоту символа
                totalCharacters++; // Увеличиваем общее количество символов }
                }
            }
        }

        // Вычисление энтропии
        for (int i = 0; i < frequency.length; i++) { // Цикл по всем возможным символам
            if (frequency[i] > 0) { // Только для символов, которые встречаются
                double probability = (double) frequency[i] / totalCharacters; // Вычисление вероятности
                entropy -= probability * (Math.log(probability) / Math.log(2)); // Логарифм по основанию 2
            }
        }

        return entropy; // Возвращаем вычисленную энтропию
    }

    private static void writeResultToFile(String filePath, double entropy) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Энтропия по Шеннону: " + entropy);
            writer.newLine();
        }
    }
}
