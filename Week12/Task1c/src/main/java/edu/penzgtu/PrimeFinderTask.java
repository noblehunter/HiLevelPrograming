package edu.penzgtu;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class PrimeFinderTask implements Runnable {
    private static final Logger logger = LogManager.getLogger(PrimeFinderTask.class);
    private final int number;
    private final List<Integer> primes;

    PrimeFinderTask(int number, List<Integer> primes) {
        this.number = number;
        this.primes = primes;
    }

    @Override
    public void run() {
        if (PrimeChecker.isPrime(number)) {
            synchronized (primes) {
                primes.add(number);
            }
        }
    }
}
