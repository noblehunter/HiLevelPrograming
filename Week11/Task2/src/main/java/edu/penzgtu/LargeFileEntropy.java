package edu.penzgtu;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LargeFileEntropy {

    private static final int BUFFER_SIZE = 1024 * 1024; // 1MB буфер

    public static double calculateEntropy(double[] probabilities) {
        if (probabilities == null || probabilities.length == 0) {
            throw new IllegalArgumentException("Массив вероятностей не может быть пустым.");
        }
        double entropy = 0.0;
        for (double p : probabilities) {
            if (p < 0 || p > 1) {
                throw new IllegalArgumentException("Вероятности должны быть в диапазоне от 0 до 1.");
            }
            if (p > 0) {
                entropy -= p * (Math.log(p) / Math.log(2));
            }
        }
        return entropy;
    }

    public static double calculateEntropyFromFrequencies(Map<Byte, Integer> frequencies) {
        if (frequencies == null || frequencies.isEmpty()) {
            throw new IllegalArgumentException("Карта частот не может быть или пустой.");
        }

        int totalFrequency = frequencies.values().stream().mapToInt(Integer::intValue).sum();
        if (totalFrequency == 0) return 0.0; // Обработка пустого файла
        double[] probabilities = new double[frequencies.size()];
        int i = 0;
        for (int frequency : frequencies.values()) {
            probabilities[i++] = (double) frequency / totalFrequency;
        }
        return calculateEntropy(probabilities);
    }

    public static double calculateEntropy(byte[] data) {
        Map<Byte, Integer> frequencies = new HashMap<>();
        for (byte b : data) {
            frequencies.put(b, frequencies.getOrDefault(b, 0) + 1);
        }
        return calculateEntropyFromFrequencies(frequencies);
    }

    public static double calculateLargeFileEntropy(String filePath) throws IOException, InterruptedException {
        if (!Files.exists(Paths.get(filePath))) {
            System.err.println("Файл не найден.");
            return 0.0; // Возвращаем 0 для пустого файла
        }
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            double totalEntropy = 0;
            int numThreads = 4;
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            long totalBytesProcessed = 0;

            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] data = Arrays.copyOfRange(buffer, 0, bytesRead);
                EntropyCalculator calculator = new EntropyCalculator(data);
                executor.execute(calculator);
                totalBytesProcessed += bytesRead;
            }
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            return totalEntropy; // Вернем 0 если не было обработки
        }
    }

    public static class EntropyCalculator implements Runnable {
        private final byte[] data;
        private double entropy;

        public EntropyCalculator(byte[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            this.entropy = calculateEntropy(data);
        }

        public double getEntropy() {
            return entropy;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine();
        scanner.close();

        try {
            double entropy = calculateLargeFileEntropy(filePath);
            System.out.println("Приблизительная энтропия файла: " + entropy);
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка: " + e.getMessage());
            if (e.getMessage().contains("Access is denied")) {
                System.err.println("Убедитесь, что у пользователя есть права на чтение файла.");
            }
        }
    }
}