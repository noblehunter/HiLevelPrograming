package edu.penzgtu;

import java.util.Scanner;

public class SquareRoot {
    // Добавляем класс сканера для ввода корня
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Вводим числло для нахождения квадратного корня
        System.out.print("Введите число для нахождения квадратного корня: ");
        double number = scanner.nextDouble();

        // Начальное приближение
        double guess = number / 2.0;
        double tolerance = 1e-10; // допустимая ошибка
        double difference;

        // Итерационная формула Герона
        do {
            double nextGuess = (guess + number / guess) / 2.0;
            difference = Math.abs(nextGuess - guess);
            guess = nextGuess;
        } while (difference > tolerance);
        // Вывод данных
        System.out.printf("Квадратный корень числа %.2f равен %.10f%n", number, guess);

        scanner.close();
    }
}

