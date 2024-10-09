package edu.penzgtu;

public class CountMaxValues {
    public static void main(String[] args) {
        // Пример входного массива
        double[] numbers = {1, 5, 10, 10, 3, -10};

        // Вызов функции для подсчета количества максимальных значений
        int count = countMaxValues(numbers);

        // Вывод результата
        System.out.println("Количество чисел, равных максимальному: " + count);
    }

    public static int countMaxValues(double[] array) {
        if (array.length == 0) {
            return 0; // Если массив пустой, возвращаем 0
        }

        double max = array[0];
        // Находим максимальное значение
        for (double num : array) {
            if (num > max) {
                max = num;
            }
        }

        int count = 0;
        // Считаем, сколько раз максимальное значение встречается в массиве
        for (double num : array) {
            if (num == max) {
                count++;
            }
        }

        return count;
    }
}
