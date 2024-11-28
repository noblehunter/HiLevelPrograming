package edu.penzgtu;

import java.util.HashMap;

public class FibonacciOptimized {
    private static HashMap<Integer, Long> cache = new HashMap<>();

    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        long result = fibonacci(n - 1) + fibonacci(n - 2);
        cache.put(n, result);
        return result;
    }

    public static void main(String[] args) {
        int n = 40; // Пример числа для вычисления
        long startTime = System.nanoTime();
        System.out.println("Fibonacci(" + n + ") = " + fibonacci(n));
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime) + " ns");
    }
}
