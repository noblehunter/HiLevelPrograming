package edu.penzgtu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int limit = 10000000;
        int threshold = 1000;
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        PrimeChecker primeChecker = new PrimeChecker();

        // Work Stealing
        long startTimeWorkStealing = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        PrimeTask task = new PrimeTask(2, limit, threshold, primeChecker);
        Result resultWorkStealing = pool.invoke(task);
        List<Integer> primesWorkStealing = resultWorkStealing.getPrimes();
        int countWorkStealing = resultWorkStealing.getCount();
        long endTimeWorkStealing = System.currentTimeMillis();
        long durationWorkStealing = endTimeWorkStealing - startTimeWorkStealing;
        pool.shutdown();

        System.out.println("Паралельные вычисления: " + durationWorkStealing + " ms, простых чисел: " + countWorkStealing);


        // Равномерное распределение с перемешиванием
        long startTimeFairDistribution = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Result>> futures = new ArrayList<>();
        List<Integer> range = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            range.add(i);
        }
        Collections.shuffle(range);
        int rangeSize = range.size();
        int chunkSize = rangeSize / numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numberOfThreads - 1) ? rangeSize - 1 : (i + 1) * chunkSize - 1;
            int startValue = range.get(start);
            int endValue = range.get(end);
            PrimeTaskForFairDistribution taskFair = new PrimeTaskForFairDistribution(startValue, endValue, primeChecker);
            futures.add(executor.submit(taskFair));
        }

        List<Integer> primesFair = new ArrayList<>();
        int countFair = 0;
        for (Future<Result> future : futures) {
            Result result = future.get();
            primesFair.addAll(result.getPrimes());
            countFair += result.getCount();
        }

        long endTimeFairDistribution = System.currentTimeMillis();
        long durationFairDistribution = endTimeFairDistribution - startTimeFairDistribution;
        executor.shutdown();

        System.out.println("Паралельные вычисления с перемешиванием: " + durationFairDistribution + " ms, простых чисел: " + countFair);

    }
}