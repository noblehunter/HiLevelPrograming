package edu.penzgtu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// Вводим класс RepeatedCharacterReducer
public class RepeatedCharacterReducer {
// Сканерр для считывания вводимой информации
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> messages = new ArrayList<>();
// Вводим количество сообщений
        System.out.println("Введите количество сообщений:");
        int n = scanner.nextInt();
        scanner.nextLine(); // очистка буфера
//Вводим сами сообщения
        System.out.println("Введите сообщения:");
//В пределах n-го количества сообщений
        for (int i = 0; i < n; i++) {
            String message = scanner.nextLine();
            messages.add(message);
        }
// Вывод результатов работы программы
        System.out.println("\nРезультаты:");
        for (String message : messages) {
            String reducedMessage = reduceRepeatedCharacters(message);
            System.out.println(reducedMessage);
        }
//Закрываем сканер после поледнего введенного n-го сообщения
        scanner.close();
    }
// Сама работа программы
    public static String reduceRepeatedCharacters(String input) {
        // Регулярное выражение для поиска символов, повторяющихся 3 и более раз
        String regex = "(.)\\1{2,}";
        // Заменяем найденные последовательности на один символ
        return input.replaceAll(regex, "$1");
    }
}


