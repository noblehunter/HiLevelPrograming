package edu.penzgtu;
// Вводим класс SieveOfEratosthenes
public class SieveOfEratosthenes {
    public static void main(String[] args) {
        int n = 30; // ищем простые числа длиной до 30
        boolean[] isPrime = new boolean[n + 1]; // создаем логический массив is Prime размером n + 1
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true; // предполагаем, что все числа изначально являются простыми
        }
        // Выполняем итерацию по массиву, используя цикл for
        for (int p = 2; p * p <= n; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= n; i += p) {
                    isPrime[i] = false;
                }
            }
        }
        // Вывод напечатаного
        System.out.print("Простые числа с точностью до " + n + ": ");
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}