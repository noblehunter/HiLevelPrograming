package edu.penzgtu;

import java.util.*;

public class SchoolLanguages {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Читаем количество школьников
        System.out.print("Введите количество школьников: ");
        int numberOfStudents = scanner.nextInt();
        scanner.nextLine(); // Переход на следующую строку

        // Множество для хранения языков, которые знает хотя бы один школьник
        Set<String> allLanguages = new HashSet<>();
        // Множество для хранения языков, которые знает каждый школьник
        Set<String> commonLanguages = null;

        for (int i = 0; i < numberOfStudents; i++) {
            // Читаем количество языков, которые знает текущий школьник
            System.out.print("Введите количество языков для школьника " + (i + 1) + ": ");
            int numberOfLanguages = scanner.nextInt();
            scanner.nextLine(); // Переход на следующую строку

            // Множество для хранения языков текущего школьника
            Set<String> studentLanguages = new HashSet<>();

            for (int j = 0; j < numberOfLanguages; j++) {
                System.out.print("Введите язык " + (j + 1) + ": ");
                String language = scanner.nextLine().trim();
                studentLanguages.add(language);
                allLanguages.add(language); // Добавляем язык в общее множество
            }

            // Если это первый школьник, инициализируем общее множество языков
            if (commonLanguages == null) {
                commonLanguages = new HashSet<>(studentLanguages);
            } else {
                commonLanguages.retainAll(studentLanguages); // Оставляем только те языки, которые есть у всех
            }
        }

        // Сортируем языки
        List<String> sortedCommonLanguages = new ArrayList<>(commonLanguages);
        Collections.sort(sortedCommonLanguages);

        List<String> sortedAllLanguages = new ArrayList<>(allLanguages);
        Collections.sort(sortedAllLanguages);

        // Выводим результат
        System.out.println("Количество языков, которые знают все школьники: " + sortedCommonLanguages.size());
        for (String language : sortedCommonLanguages) {
            System.out.println(language);
        }

        System.out.println("Количество языков, которые знает хотя бы один школьник: " + sortedAllLanguages.size());
        for (String language : sortedAllLanguages) {
            System.out.println(language);
        }

        scanner.close();
    }
}
