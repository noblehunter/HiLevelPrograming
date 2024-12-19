package edu.penzgtu;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


class PrimeFinderWorkStealing extends RecursiveTask<List<Integer>> {
    private static final Logger logger = LogManager.getLogger(PrimeFinderWorkStealing.class);
    private final int start;
    private final int end;

    PrimeFinderWorkStealing(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected List<Integer> compute() {
        if (end - start <= 1000) {
            return IntStream.rangeClosed(start, end).filter(PrimeChecker::isPrime).boxed().collect(Collectors.toList());
        } else {
            int mid = (start + end) / 2;
            PrimeFinderWorkStealing left = new PrimeFinderWorkStealing(start, mid);
            PrimeFinderWorkStealing right = new PrimeFinderWorkStealing(mid + 1, end);
            left.fork();
            List<Integer> rightPrimes = right.compute();
            List<Integer> leftPrimes = left.join();
            leftPrimes.addAll(rightPrimes);
            return leftPrimes;
        }
    }
}

