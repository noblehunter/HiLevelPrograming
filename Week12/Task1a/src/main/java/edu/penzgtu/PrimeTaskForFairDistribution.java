package edu.penzgtu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

class PrimeTaskForFairDistribution implements Callable<Result> {
    private final int start;
    private final int end;
    private final PrimeChecker primeChecker;


    public PrimeTaskForFairDistribution(int start, int end, PrimeChecker primeChecker) {
        this.start = start;
        this.end = end;
        this.primeChecker = primeChecker;
    }

    @Override
    public Result call() {
        List<Integer> primes = new ArrayList<>();
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (primeChecker.isPrime(i)) {
                primes.add(i);
                count++;
            }
        }
        return new Result(primes, count);
    }
}