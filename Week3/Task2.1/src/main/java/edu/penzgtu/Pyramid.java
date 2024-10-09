package edu.penzgtu;

import java.util.Scanner;
// Вводим класс Pyramid
public class Pyramid {
    // Добовляем метод сканера для
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Вводим высоту пирамиды
        System.out.print("Введите высоту пирамиды: ");
        int height = scanner.nextInt();

        // Построение пирамиды
        for (int i = 1; i <= height; i++) {
            // Печатаем пробелы для выравнивания
            for (int j = i; j < height; j++) {
                System.out.print(" ");
            }
            // Печатаем звездочки
            for (int k = 1; k <= (2 * i - 1); k++) {
                System.out.print("@");
            }
            // Переход на следующую строку
            System.out.println();
        }

        scanner.close();
    }
}
