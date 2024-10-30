package edu.penzgtu;

import java.util.*;

public class LatinEnglishDictionary {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Считываем количество английских слов в словаре
        System.out.print("Введите количество английских слов в словаре: ");
        int N = Integer.parseInt(scanner.nextLine());
        Map<String, List<String>> dictionary = new HashMap<>();

        // Считываем каждое слово и его переводы
        for (int i = 0; i < N; i++) {
            System.out.print("Введите слово и его переводы (формат: слово - перевод1, перевод2, ...): ");
            String line = scanner.nextLine();
            String[] parts = line.split(" - ");
            String englishWord = parts[0].trim();
            String[] translations = parts[1].split(", ");

            // Добавляем переводы в словарь
            for (String translation : translations) {
                translation = translation.trim(); // Убираем лишние пробелы
                dictionary.putIfAbsent(translation, new ArrayList<>());
                dictionary.get(translation).add(englishWord);
            }
        }

        // Сортируем переводы (латинские слова)
        List<String> sortedTranslations = new ArrayList<>(dictionary.keySet());
        Collections.sort(sortedTranslations);

        // Выводим латинско-английский словарь
        System.out.println("\nЛатинско-английский словарь:");
        for (String translation : sortedTranslations) {
            List<String> englishWords = dictionary.get(translation);
            Collections.sort(englishWords); // Сортируем английские слова для каждого перевода
            System.out.println(translation + " - " + String.join(", ", englishWords));
        }

        scanner.close();
    }
}
