package edu.penzgtu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class PrimeFinder extends RecursiveTask<List<Integer>> {
    private final int start;
    private final int end;

    PrimeFinder(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected List<Integer> compute() {
        if (end - start <= 1000) { // Порог для перехода к последовательному выполнению
            List<Integer> primes = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                if (isPrime(i)) {
                    primes.add(i);
                }
            }
            return primes;
        } else {
            int mid = (start + end) / 2;
            PrimeFinder left = new PrimeFinder(start, mid);
            PrimeFinder right = new PrimeFinder(mid + 1, end);
            left.fork(); // Запуск подзадач асинхронно
            List<Integer> rightPrimes = right.compute(); // Выполнение правой подзадачи
            List<Integer> leftPrimes = left.join(); // Ожидание завершения левой подзадачи
            leftPrimes.addAll(rightPrimes);
            return leftPrimes;
        }
    }

    // Метод проверки на простоту (оптимизированный)
    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        return true;
    }
}


public class Main {
    public static void main(String[] args) {
        int limit = 1000000; // Большая граница для демонстрации Work Stealing
        ForkJoinPool pool = new ForkJoinPool();
        List<Integer> primes = pool.invoke(new PrimeFinder(2, limit));
        System.out.println("Найдено " + primes.size() + " простых чисел.");
        System.out.println(primes); // Вывод списка (может быть очень длинным)

    }
}