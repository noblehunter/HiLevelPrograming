package edu.penzgtu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Создаем объект Scanner для считывания ввода пользователя
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем у пользователя ввести имя
        System.out.print("Введите ваше имя: ");
        String userName = scanner.nextLine();

        // Выводим приветственное сообщение с именем пользователя
        System.out.println("Привет, " + userName + "!");
    }
}
