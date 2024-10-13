package edu.penzgtu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Обьявляем класс EmailValidation
public class EmailValidation {
// Запуск сканера для считывания вводимой информации
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//Вводим email адрес
        System.out.println("Введите email адрес: ");
        String email = scanner.nextLine();
// Проверочная строка регулярного выражения
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
//Выявление патернов
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
//Вывод программы
        if(matcher.matches()) {
            System.out.println("Email адрес верный");
        } else {
            System.out.println("Email адрес не верный");
        }
    }
}
