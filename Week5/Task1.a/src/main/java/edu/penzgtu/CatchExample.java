package edu.penzgtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatchExample {
    public static void main(String[] args) {
        // Укажите путь к вашему текстовому файлу
        String filePath = "C:/Users/NobleHunter/Desktop/Projekt/HiLevelPrograming/Week5/Task1.a/src/main/java/edu/penzgtu/numbers.txt";

        // Список для хранения строк файла
        List<String> lines = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line); // Добавляем строку в список
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return; // Завершаем программу, если произошла ошибка
        } finally {
            // Закрываем BufferedReader в блоке finally, если он был открыт
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии файла: " + e.getMessage());
                }
            }
        }

        // Выводим содержимое списка на экран
        for (String item : lines) {
            System.out.println(item);
        }
    }
}

