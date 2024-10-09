package edu.penzgtu;
// Обозначаем класс
public class MergeSort {
// Статистическая сортировка
    public static void mergeSort(double[] array) {
        if (array.length > 1) {
            int mid = array.length / 2;
            double[] left = new double[mid];
            double[] right = new double[array.length - mid];

            // Разделяем массив на две половины
            System.arraycopy(array, 0, left, 0, mid);
            System.arraycopy(array, mid, right, 0, array.length - mid);

            // Отсортировать две половины
            mergeSort(left);
            mergeSort(right);

            // Объединяет две отсортированные половины
            merge(array, left, right);
        }
    }
// Частное статическое слияние
    private static void merge(double[] array, double[] left, double[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        // Копирует все оставшиеся элементы из левого или правого массивов
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
// Для вывода данным
    public static void main(String[] args) {
        double[] data_array = {4.2, 1.1, 3.3, 2.2, 5.5, 6.6};
        System.out.println("Оригинальный числовой ряд:");
        printArray(data_array);

        mergeSort(data_array);

        System.out.println("Отсортированный числовой ряд:");
        printArray(data_array);
    }
// Вывод данных
    private static void printArray(double[] array) {
        for (double element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
