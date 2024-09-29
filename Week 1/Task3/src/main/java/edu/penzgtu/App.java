package edu.penzgtu;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Создаем объект Scanner для считывания ввода пользователя
        System.out.println("Введите длину ребра додекаэдра: ");
        // Запрашиваем у пользователя ввести длинну ребра додекаэдра
        double edgeLength = input.nextDouble();
        // Вычисляем объем додекаэдра по формуле:
        double volume = (15 + 7 * Math.sqrt(5)) / 4 * Math.pow(edgeLength, 3);
        // Выводим результат на экран
        System.out.println("Объем додекаэдра: " + volume);
    }
}