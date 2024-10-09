package edu.penzgtu;
// Задаем класс MedianFinder
public class MedianFinder {
    public static void main(String[] args) {
        double[] data_array = {1.2, 3.4, 5.6, 7.8, 9.0};
//Вывод медианы
        double median = findMedian(data_array);
        System.out.println("Медиана: " + median);
    }

    private static double findMedian(double[] array) {
        // Сортируем массив в порядке возрастания
        sortArray(array);

        // Ищем медиану
        int n = array.length;
        if (n % 2 == 0) { // четное количество элементов
            int mid1 = n / 2 - 1;
            int mid2 = n / 2;
            return (array[mid1] + array[mid2]) / 2.0;
        } else { // нечетное количество элементов
            int mid = n / 2;
            return array[mid];
        }
    }
// Простая пузырьковая сортировка
    private static void sortArray(double[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    double temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }
}