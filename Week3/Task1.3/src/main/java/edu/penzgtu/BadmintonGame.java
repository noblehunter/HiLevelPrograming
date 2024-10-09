package edu.penzgtu;
// Добовляем сканер для считывания вводимой информации от пользователя
import java.util.Scanner;
// Обозночаем класс BadmintonGame
public class BadmintonGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
// Выбираем день недели
        System.out.println("Введите день недели (1 - понедельник, 2 - вторник, ..., 7 - воскресенье):");
        int dayOfWeek = scanner.nextInt();
// Выбираем температуру
        System.out.println("Введите температуру (1 - жарко, 2 - тепло, 3 - холодно):");
        int temperature = scanner.nextInt();
// Выбираем осадки
        System.out.println("Введите осадки (1 - ясно, 2 - облачно, 3 - дождь, 4 - снег, 5 - град):");
        int precipitation = scanner.nextInt();
// Определяем наличие ветра
        System.out.println("Введите ветер (1 - есть, 2 - нет):");
        int wind = scanner.nextInt();
// Вводим влажность
        System.out.println("Введите влажность (1 - высокая, 2 - низкая):");
        int humidity = scanner.nextInt();
// Вывод
        if (isGoodWeather(temperature, precipitation, wind, humidity) && isSunday(dayOfWeek)) {
            System.out.println("Да");
        } else {
            System.out.println("Нет");
        }
    }
// Проверка дня недели
    private static boolean isSunday(int dayOfWeek) {
        return dayOfWeek == 7;
    }
// Задаем необходимые условия
    private static boolean isGoodWeather(int temperature, int precipitation, int wind, int humidity) {
        return (temperature == 1 || temperature == 2) // жарко или тепло
                && (precipitation == 1) // ясно
                && (wind == 2) // нет ветра
                && (humidity == 2); // низкая влажность
    }
}
