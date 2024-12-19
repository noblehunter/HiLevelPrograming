package edu.penzgtu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

class PrimeTask extends RecursiveTask<Result> {
    private final int start;
    private final int end;
    private final int threshold;
    private final PrimeChecker primeChecker;

    public PrimeTask(int start, int end, int threshold, PrimeChecker primeChecker) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
        this.primeChecker = primeChecker;
    }

    @Override
    protected Result compute() {
        if (end - start <= threshold) {
            List<Integer> primes = new ArrayList<>();
            int count = 0;
            for (int i = start; i <= end; i++) {
                if (primeChecker.isPrime(i)) {
                    primes.add(i);
                    count++;
                }
            }
            return new Result(primes, count);
        } else {
            int middle = start + (end - start) / 2;
            PrimeTask leftTask = new PrimeTask(start, middle, threshold, primeChecker);
            PrimeTask rightTask = new PrimeTask(middle + 1, end, threshold, primeChecker);

            leftTask.fork();
            Result rightResult = rightTask.compute();
            Result leftResult = leftTask.join();

            List<Integer> resultPrimes = new ArrayList<>();
            resultPrimes.addAll(leftResult.getPrimes());
            resultPrimes.addAll(rightResult.getPrimes());

            int totalCount = leftResult.getCount() + rightResult.getCount();
            return new Result(resultPrimes, totalCount);
        }
    }
}