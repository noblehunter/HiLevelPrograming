package edu.penzgtu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



class PrimeFinderShuffle {
    private static final Logger logger = LogManager.getLogger(PrimeFinderShuffle.class);
    static List<Integer> findPrimes(int limit, int numThreads) throws InterruptedException {
        List<Integer> numbers = IntStream.rangeClosed(2, limit).boxed().collect(Collectors.toList());
        Collections.shuffle(numbers);
        List<Integer> primes = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int number : numbers) {
            executor.submit(new PrimeFinderTask(number, primes));
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        return primes;
    }
}
