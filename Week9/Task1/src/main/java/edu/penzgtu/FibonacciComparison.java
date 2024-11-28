package edu.penzgtu;

public class FibonacciComparison {
    public static void main(String[] args) {
        int maxN = 40; // Максимальное значение n для сравнения

        // Сравнение классической реализации
        for (int i = 1; i <= maxN; i++) {
            long startTime = System.nanoTime();
            FibonacciClassic.fibonacci(i);
            long endTime = System.nanoTime();
            System.out.println("Classic Fibonacci(" + i + ") time: " + (endTime - startTime) + " ns");
        }

        // Сравнение оптимизированной реализации
        for (int i = 1; i <= maxN; i++) {
            long startTime = System.nanoTime();
            FibonacciOptimized.fibonacci(i);
            long endTime = System.nanoTime();
            System.out.println("Optimized Fibonacci(" + i + ") time: " + (endTime - startTime) + " ns");
        }
    }
}

