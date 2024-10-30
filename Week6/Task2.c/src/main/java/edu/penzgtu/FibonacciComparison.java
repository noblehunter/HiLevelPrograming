package edu.penzgtu;

public class FibonacciComparison {
    public static void main(String[] args) {
        int maxN = 40; // Максимальное значение n для тестирования

        // Тестирование классической реализации
        System.out.println("Классическая реализация:");
        for (int i = 0; i <= maxN; i++) {
            long startTime = System.nanoTime();
            long result = FibonacciClassic.fibonacci(i);
            long endTime = System.nanoTime();
            System.out.printf("Fibonacci(%d) = %d, время: %d нс%n", i, result, (endTime - startTime));
        }

        // Тестирование оптимизированной реализации
        System.out.println("\nОптимизированная реализация:");
        for (int i = 0; i <= maxN; i++) {
            long startTime = System.nanoTime();
            long result = FibonacciOptimized.fibonacci(i);
            long endTime = System.nanoTime();
            System.out.printf("Fibonacci(%d) = %d, время: %d нс%n", i, result, (endTime - startTime));
        }
    }
}
