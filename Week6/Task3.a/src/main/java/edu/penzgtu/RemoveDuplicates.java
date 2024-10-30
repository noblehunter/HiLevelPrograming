package edu.penzgtu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class RemoveDuplicates {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем у пользователя ввод массива
        System.out.print("Введите элементы массива через запятую: ");
        String input = scanner.nextLine();

        // Разделяем строку на элементы массива
        String[] stringArray = input.split(",");

        // Преобразуем строковый массив в массив целых чисел
        int[] inputArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            inputArray[i] = Integer.parseInt(stringArray[i].trim()); // Убираем лишние пробелы
        }

        // Удаляем дубликаты
        int[] uniqueArray = removeDuplicates(inputArray);

        // Выводим результат
        System.out.println("Массив после удаления дубликатов: " + Arrays.toString(uniqueArray));

        scanner.close();
    }

    public static int[] removeDuplicates(int[] array) {
        // Используем HashSet для хранения уникальных элементов
        HashSet<Integer> set = new HashSet<>();

        // Добавляем элементы массива в HashSet
        for (int num : array) {
            set.add(num);
        }

        // Преобразуем HashSet обратно в массив
        int[] uniqueArray = new int[set.size()];
        int index = 0;
        for (int num : set) {
            uniqueArray[index++] = num;
        }

        return uniqueArray;
    }
}
