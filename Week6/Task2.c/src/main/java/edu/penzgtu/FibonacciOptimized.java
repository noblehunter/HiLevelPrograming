package edu.penzgtu;

import java.util.HashMap;
import java.util.Map;

public class FibonacciOptimized {
    private static Map<Integer, Long> cache = new HashMap<>();

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
}

