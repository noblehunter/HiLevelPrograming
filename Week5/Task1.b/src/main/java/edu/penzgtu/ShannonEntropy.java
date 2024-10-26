package edu.penzgtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShannonEntropy {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ShannonEntropy <filename>");
            return;
        }

        String filename = args[0];
        try {
            String content = readFile(filename);
            double entropy = calculateEntropy(content);
            System.out.printf("Энтропия по Шеннону: %.6f%n", entropy);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private static String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    public static double calculateEntropy(String content) {
        int[] frequency = new int[256]; // Массив для частоты символов ASCII
        int totalCharacters = 0; // Общее количество символов
        double entropy = 0.0; // Инициализация переменной entropy

        // Подсчет частоты появления каждого символа
        for (char c : content.toCharArray()) {
            frequency[c]++;
            totalCharacters++;
        }

        // Вычисление энтропии
        for (int freq : frequency) {
            if (freq > 0) { // Только для символов, которые встречаются double probability = (double) freq / totalCharacters; // Вероятность появления символа
                entropy -= totalCharacters * (Math.log(totalCharacters) / Math.log(2)); // логарифм по основанию 2
            }
        }

        return entropy; // Возвращаем рассчитанную энтропию
    }
}




