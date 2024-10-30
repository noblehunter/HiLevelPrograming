package edu.penzgtu;

import java.util.*;

public class SalesDatabase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Map<String, Integer>> salesData = new TreeMap<>(); // Дерево для сортировки покупателей

        System.out.println("Введите данные о продажах (формат: Покупатель Товар Количество). Введите 'exit' для завершения ввода:");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 3) {
                System.out.println("Неверный формат ввода. Пожалуйста, введите данные в формате: Покупатель Товар Количество");
                continue;
            }

            String buyer = parts[0];
            String product = parts[1];
            int quantity;

            try {
                quantity = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Количество должно быть числом. Попробуйте снова.");
                continue;
            }

            // Обновляем данные о продажах
            salesData.putIfAbsent(buyer, new TreeMap<>()); // Дерево для сортировки товаров
            Map<String, Integer> products = salesData.get(buyer);
            products.put(product, products.getOrDefault(product, 0) + quantity);
        }

        // Выводим результаты
        System.out.println("\nСписок покупателей и их покупки:");
        for (Map.Entry<String, Map<String, Integer>> entry : salesData.entrySet()) {
            String buyer = entry.getKey();
            Map<String, Integer> products = entry.getValue();

            System.out.println("Покупатель: " + buyer);
            for (Map.Entry<String, Integer> productEntry : products.entrySet()) {
                System.out.println("  Товар: " + productEntry.getKey() + ", Количество: " + productEntry.getValue());
            }
        }

        scanner.close();
    }
}

