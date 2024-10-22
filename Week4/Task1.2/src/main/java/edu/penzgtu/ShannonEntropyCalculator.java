package edu.penzgtu;

import java.util.Scanner;

public class ShannonEntropyCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Запрашиваем количество сообщений у пользователя System.out.println("Введите количество сообщений:");
        int n = scanner.nextInt();
        scanner.nextLine(); // Считываем оставшийся символ новой строки

        String[] messages = new String[n];

        // Считываем сообщения
        for (int i = 0; i < n; i++) {
            System.out.println("Введите сообщение " + (i + 1) + ":");
            messages[i] = scanner.nextLine();
        }

        // Вычисляем и выводим энтропию для каждого сообщения for (int i = 0; i < n; i++) {
        double entropy = calculateEntropy(messages[i]);
        System.out.printf("Энтропия сообщения %d: %.4f%n", i + 1, entropy);
    }

        scanner.close();
}

private static double calculateEntropy(String message) {
    int length = message.length();
    if (length == 0) {
        return 0.0; // Если сообщение пустое, энтропия равна 0 }

        int[] frequency = new int[256]; // Массив для хранения частоты символов

        // Подсчет частоты появления каждого символа for (int i = 0; i < length; i++) {
        frequency[message.charAt(i)]++;
    }

    double entropy = 0.0;

    // Вычисление энтропии
    for (int i = 0; i < frequency.length; i++) {
        if (frequency[i] > 0) {
            double probability = (double) frequency[i] / length;
            entropy -= probability * (Math.log(probability) / Math.log(2)); // Логарифм по основанию 2 }
        }

        return entropy;
    }
}




