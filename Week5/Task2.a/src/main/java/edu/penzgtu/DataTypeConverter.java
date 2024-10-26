package edu.penzgtu; // Определение пакета, к которому принадлежит данный класс.

import java.util.Scanner; // Импорт класса Scanner для считывания ввода пользователя.

public class DataTypeConverter { // Объявление класса DataTypeConverter.
    public static void main(String[] args) { // Главный метод программы, с которого начинается выполнение.
        Scanner scanner = new Scanner(System.in); // Создание объекта Scanner для считывания данных с консоли.

        // Вывод меню для выбора типа конвертации.
        System.out.println("Выберите тип конвертации:");
        System.out.println("1. Целое число в вещественное число");
        System.out.println("2. Вещественное число в целое число");
        System.out.println("3. Целое число в строку");
        System.out.println("4. Вещественное число в строку");
        System.out.println("5. Строка в целое число");
        System.out.println("6. Строка в вещественное число");
        System.out.print("Введите номер операции: "); // Подсказка для ввода номера операции.

        int choice = scanner.nextInt(); // Считывание выбора пользователя.
        scanner.nextLine(); // Чистим буфер, чтобы избежать проблем с последующим вводом строк.

        // Использование конструкции switch для обработки выбора пользователя.
        switch (choice) {
            case 1: // Конвертация целого числа в вещественное.
                System.out.print("Введите целое число: "); // Запрос на ввод целого числа.
                int intValue = scanner.nextInt(); // Считывание целого числа.
                double doubleValueFromInt = (double) intValue; // Преобразование целого числа в вещественное.
                System.out.println("Вещественное число: " + doubleValueFromInt); // Вывод результата.
                break; // Завершение текущего case.

            case 2: // Конвертация вещественного числа в целое.
                System.out.print("Введите вещественное число: "); // Запрос на ввод вещественного числа.
                double doubleValue = scanner.nextDouble(); // Считывание вещественного числа.
                int intValueFromDouble = (int) doubleValue; // Преобразование вещественного числа в целое.
                System.out.println("Целое число: " + intValueFromDouble); // Вывод результата.
                break;

            case 3: // Конвертация целого числа в строку.
                System.out.print("Введите целое число: "); // Запрос на ввод целого числа.
                int intValueToString = scanner.nextInt(); // Считывание целого числа.
                String stringValueFromInt = Integer.toString(intValueToString); // Преобразование целого числа в строку.
                System.out.println("Строка: " + stringValueFromInt); // Вывод результата.
                break;

            case 4: // Конвертация вещественного числа в строку.
                System.out.print("Введите вещественное число: "); // Запрос на ввод вещественного числа.
                double doubleValueToString = scanner.nextDouble(); // Считывание вещественного числа.
                String stringValueFromDouble = Double.toString(doubleValueToString); // Преобразование вещественного числа в строку.
                System.out.println("Строка: " + stringValueFromDouble); // Вывод результата.
                break;

            case 5: // Конвертация строки в целое число.
                System.out.print("Введите строку: "); // Запрос на ввод строки.
                String stringInputToInt = scanner.nextLine(); // Считывание строки.
                try {
                    int intValueFromString = Integer.parseInt(stringInputToInt); // Попытка преобразования строки в целое число.
                    System.out.println("Целое число: " + intValueFromString); // Вывод результата.
                } catch (NumberFormatException e) { // Обработка исключения в случае ошибки преобразования.
                    System.out.println("Ошибка: строка не может быть преобразована в целое число."); // Сообщение об ошибке.
                }
                break;

            case 6: // Конвертация строки в вещественное число.
                System.out.print("Введите строку: "); // Запрос на ввод строки.
                String stringInputToDouble = scanner.nextLine(); // Считывание строки.
                try {
                    double doubleValueFromString = Double.parseDouble(stringInputToDouble); // Попытка преобразования строки в вещественное число.
                    System.out.println("Вещественное число: " + doubleValueFromString); // Вывод результата.
                } catch (NumberFormatException e) { // Обработка исключения в случае ошибки преобразования.
                    System.out.println("Ошибка: строка не может быть преобразована в вещественное число."); // Сообщение об ошибке.
                }
                break;

            default: // Обработка случая, если пользователь ввел неверный выбор.
                System.out.println("Неверный выбор."); // Сообщение о неверном выборе.
                break;
        }

        scanner.close(); // Закрытие объекта Scanner для освобождения ресурсов.
    }
}
