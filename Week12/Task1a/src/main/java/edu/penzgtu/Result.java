package edu.penzgtu;

import java.util.List;

class Result {
    private final List<Integer> primes;
    private final int count;

    public Result(List<Integer> primes, int count) {
        this.primes = primes;
        this.count = count;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public int getCount() {
        return count;
    }
}
