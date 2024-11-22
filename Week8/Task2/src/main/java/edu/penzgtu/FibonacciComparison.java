package edu.penzgtu;

public class FibonacciComparison {
    public static void main(String[] args) {
        int n = 40; // Вы можете изменить это значение для тестирования
        long startTime, endTime;

        // Классическая реализация
        startTime = System.nanoTime();
        long classicResult = FibonacciClassic.fibonacci(n);
        endTime = System.nanoTime();
        long classicTime = endTime - startTime;

        System.out.println("Classic Fibonacci(" + n + ") = " + classicResult + " Time: " + classicTime + " ns");

        // Оптимизированная реализация
        FibonacciOptimized optimizedFibonacci = new FibonacciOptimized();
        startTime = System.nanoTime();
        long optimizedResult = optimizedFibonacci.fibonacci(n);
        endTime = System.nanoTime();
        long optimizedTime = endTime - startTime;

        System.out.println("Optimized Fibonacci(" + n + ") = " + optimizedResult + " Time: " + optimizedTime + " ns");
    }
}

