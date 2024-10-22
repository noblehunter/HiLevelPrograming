package edu.penzgtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CatchExample {
    public static void main(String[] args) {
        // Укажите путь к вашему текстовому файлу
        String filePath = "C:/Users/NobleHunter/Desktop/Projekt/HiLevelPrograming/Week5/Task1.a/src/main/java/edu/penzgtu/numbers.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Читаем файл построчно
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}