package edu.penzgtu;

/**
 * Hello world!
 */
public class FibonacciClassic {
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) {
        int n = 40; // Пример числа для вычисления
        long startTime = System.nanoTime();
        System.out.println("Fibonacci(" + n + ") = " + fibonacci(n));
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime) + " ns");
    }
}

