package edu.penzgtu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Вводи класс IPv6Validator
public class IPv6Validator {
// Сканер для считывания информации
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
// Ввод для ip адреса
        System.out.println("Введите ip адрес: ");
        String ipAddress = scanner.nextLine();;

// Строка регулярного выражения
        String regex = "^([0-9a-fA-F]{1,4}:){7}([0-9a-fA-F]{1,4})$";
// Выявляем паттерны
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ipAddress);
// Вывод работы программы
        if (matcher.matches()) {
            System.out.println("IPv6 адрес действителен.");
        } else {
            System.out.println("IPv6 адрес недействителен.");
        }
    }
}
