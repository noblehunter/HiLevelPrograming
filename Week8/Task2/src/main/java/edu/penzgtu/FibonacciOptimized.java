package edu.penzgtu;

import java.util.HashMap;

public class FibonacciOptimized {
    private HashMap<Integer, Long> memo = new HashMap<>();

    public long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        long result = fibonacci(n - 1) + fibonacci(n - 2);
        memo.put(n, result);
        return result;
    }
}
