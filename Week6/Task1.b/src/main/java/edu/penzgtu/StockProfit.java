package edu.penzgtu;

import java.util.Scanner;

public class StockProfit {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем у пользователя ввод цен акций
        System.out.println("Введите цены акций через пробел:");
        String input = scanner.nextLine();

        // Разбиваем введенную строку на массив строк
        String[] priceStrings = input.split(" ");
        int[] prices = new int[priceStrings.length];

        // Преобразуем строки в целые числа
        for (int i = 0; i < priceStrings.length; i++) {
            prices[i] = Integer.parseInt(priceStrings[i]);
        }

        // Вызываем метод maxProfit для получения максимальной прибыли
        int maxProfit = maxProfit(prices);

        // Выводим результат
        System.out.println("Максимальная прибыль: " + maxProfit);

        scanner.close();
    }

    // Метод для вычисления максимальной прибыли
    public static int maxProfit(int[] prices) {
        // Проверяем, есть ли хотя бы два дня для торговли
        if (prices.length < 2) {
            return 0; // Невозможно получить прибыль
        }

        int minPrice = prices[0]; // Начальная минимальная цена
        int maxProfit = 0; // Изначально максимальная прибыль равна 0

        // Проходим по всем ценам
        for (int i = 1; i < prices.length; i++) {
            // Если текущая цена меньше минимальной, обновляем минимальную цену
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                // Если текущая цена больше минимальной, вычисляем потенциальную прибыль
                int profit = prices[i] - minPrice;
                // Обновляем максимальную прибыль, если текущая прибыль больше
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }

        return maxProfit; // Возвращаем максимальную прибыль
    }
}
