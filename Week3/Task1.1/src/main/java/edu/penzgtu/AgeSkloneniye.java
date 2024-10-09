package edu.penzgtu;

import java.util.Scanner;

public class AgeSkloneniye {
    public static void main(String[] args) {
        // Исспользуем класс Scanner для ввода с клавиатуры.
        Scanner scanner = new Scanner(System.in);
        // Спрашиваем возраст
        System.out.print("Введите ваш возраст: ");
        int age = scanner.nextInt();
        // Проверяем возраст и определяем правильное склонение по правилам русского языка
        String suffix;

        if (age % 10 == 1 && age % 100 != 11) {
            suffix = "год";
        } else if (age % 10 >= 2 && age % 10 <= 4 && (age % 100 < 10 || age % 100 >= 20)) {
            suffix = "года";
        } else {
            suffix = "лет";
        }
        // Вывод
        System.out.println(age + " " + suffix);

        scanner.close();
    }
}
