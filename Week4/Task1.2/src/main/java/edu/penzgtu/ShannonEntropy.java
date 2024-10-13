package edu.penzgtu;

import java.util.HashMap;

public class ShannonEntropy {
//  рассчитывает энтропию для строки "hello world"
    public static void main(String[] args) {
        String input = "Проверяем расчетную энтропии заданой строки";
        double entropy = calculateEntropy(input);
        System.out.printf("Энтропия заданной строки: %.2f%n", entropy);

        // Граничное условие: пустая строка
        String emptyInput = "";
        double emptyEntropy = calculateEntropy(emptyInput);
        System.out.printf("Энтропия пустой строки: %.2f%n", emptyEntropy);
    }
// Рассчет энтропии
    public static double calculateEntropy(String input) {
        if(input.isEmpty()) {
            return 0.0;
        }
        // Вывод для расчетной энтропии
        HashMap<Character, Integer> frequencyMap = new HashMap<>();
        for(char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
// Вывод удвоеного значения расчетной энтропии
        double entropy = 0.0;
        int totalLength = input.length();
//Сама формула для расчета энропии
        for(char c : frequencyMap.keySet()) {
            double probability = (double) frequencyMap.get(c) / totalLength;
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }
// Возвращение энтропии
        return entropy;
    }
}
