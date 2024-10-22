package edu.penzgtu;
// Добовляем сканер для считывания вводимых данных
import java.util.Scanner;
// Основной класс Main
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
// Ввод для строки поиска (что ищем)
        System.out.println("Введите строку для поиска:");
        String searchString = scanner.nextLine();
// Вводим количество строк в которых мы ищем
        System.out.println("Введите количество строк для поиска:");
        int n = scanner.nextInt();
        scanner.nextLine(); // считываем лишний перевод строки

        int count = 0;
// Ввод для каждой конкретной строки
        for (int i = 0; i < n; i++) {
            System.out.println("Введите строку " + (i + 1) + ":");
            String inputString = scanner.nextLine();
// Подсчет индексов
            int index = 0;
            while ((index = inputString.indexOf(searchString, index)) != -1) {
                count++;
                index += searchString.length();
            }
        }
// Вывод
        System.out.println("Строка '" + searchString + "' встречается " + count + " раз.");
    }
}