package edu.penzgtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CatchExample {
    public static void main(String[] args) {
        // Укажите путь к вашему текстовому файлу
        String filePath = "C:/Users/NobleHunter/Desktop/Projekt/HiLevelPrograming/Week5/Task1.a/src/main/java/edu/penzgtu/numbers.txt";

        // Сначала мы прочитаем файл, чтобы узнать количество строк
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Теперь создаем массив для хранения строк
        String[] data = new String[count]; // Объявление массива // Снова читаем файл, чтобы заполнить массив
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                // Сохраняем строку в массив
                data[index] = line;
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Выводим массив на экран
        for (String item : data) {
            System.out.println(item);
        }
    }
}
