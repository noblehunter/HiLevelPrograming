package edu.penzgtu;

import java.util.List;
import java.util.concurrent.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        int limit = 100000;
        int numThreads = Runtime.getRuntime().availableProcessors();

        if (args.length > 0) {
            try {
                limit = Integer.parseInt(args[0]);
                if (limit <= 1) {
                    logger.error("Ошибка: Верхняя граница должна быть больше 1.");
                    return;
                }
            } catch (NumberFormatException e) {
                logger.error("Ошибка: Неверный формат верхней границы. Используйте целое число.", e);
                return;
            }
            if (args.length > 1) {
                try {
                    numThreads = Integer.parseInt(args[1]);
                    if (numThreads <= 0) {
                        logger.error("Ошибка: Количество потоков должно быть больше 0.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    logger.error("Ошибка: Неверный формат количества потоков. Используйте целое число.", e);
                    return;
                }
            }
        }

        logger.info("Начинаем поиск простых чисел до {} с {} потоками.", limit, numThreads);

        long startTime = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        List<Integer> primesWorkStealing = pool.invoke(new PrimeFinderWorkStealing(2, limit));
        long endTime = System.nanoTime();
        logger.info("Паралельные вычисления завершены. Время выполнения: {} ms, найдено простых чисел: {}", (endTime - startTime) / 1_000_000, primesWorkStealing.size());

        startTime = System.nanoTime();
        List<Integer> primesShuffle = PrimeFinderShuffle.findPrimes(limit, numThreads);
        endTime = System.nanoTime();
        logger.info("Перемешивание и распределение завершено. Время выполнения: {} ms, найдено простых чисел: {}", (endTime - startTime) / 1_000_000, primesShuffle.size());
    }
}