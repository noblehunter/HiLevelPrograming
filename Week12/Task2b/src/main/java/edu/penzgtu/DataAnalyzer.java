package edu.penzgtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

class DataAnalyzer {

    private List<Integer> data;

    public DataAnalyzer(List<Integer> data) {
        this.data = data;
    }

    public Result analyzeData() throws InterruptedException, ExecutionException {
        int numThreads = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> futures = new ArrayList<>();

        int chunkSize = (int) Math.ceil((double) data.size() / numThreads);

        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * chunkSize;
            int endIndex = Math.min(startIndex + chunkSize, data.size());

            AnalysisTask task = new AnalysisTask(data.subList(startIndex, endIndex));
            Future<Integer> future = executor.submit(task);
            futures.add(future);
        }

        int sum = 0;
        for (Future<Integer> future : futures) {
            try {
                sum += future.get();
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException) cause;
                } else {
                    throw new RuntimeException("Ошибка в расчете потока", e);
                }
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Прерывание при исключении
        }

        return new Result(sum);
    }

    static class AnalysisTask implements Callable<Integer> {
        private List<Integer> dataChunk;

        public AnalysisTask(List<Integer> dataChunk) {
            this.dataChunk = dataChunk;
        }

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int value : dataChunk) {
                sum += value;
            }
            return sum;
        }
    }

    static class Result {
        private int sum;

        public Result(int sum) {
            this.sum = sum;
        }

        public int getSum() {
            return sum;
        }
    }

    private static List<Integer> readPricesFromCSV(String filePath) {
        List<Integer> prices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Пропустить строку заголовка

            int lineNumber = 2; // Начальное количество строк - 2 (после заголовка)
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0) {
                    String priceStr = values[0].trim(); // При необходимости скорректируйте индекс
                    try {
                        //Проверка правильности целого числа перед синтаксическим анализом
                        if(priceStr.matches("\\d+")){
                            int price = Integer.parseInt(priceStr);
                            prices.add(price);
                        } else {
                            System.err.println("Пропуск недопустимой цены (не целой) в строке" + lineNumber + ": \"" + priceStr + "\"");
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("Пропуск недопустимой цены (исключение NumberFormatException) в строке" + lineNumber + ": \"" + priceStr + "\"");
                    }
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении CSV-файла: " + e.getMessage());
            return null;
        }
        return prices;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите путь к CSV-файлу: ");
            String filePath = scanner.nextLine();

            List<Integer> prices = readPricesFromCSV(filePath);

            if (prices == null || prices.isEmpty()) {
                System.err.println("Ошибка при чтении или обработке CSV-файла.");
                return;
            }

            DataAnalyzer analyzer = new DataAnalyzer(prices);
            Result result = analyzer.analyzeData();
            System.out.println("Сумма цен на жилье: " + result.getSum());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Во время расчета произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Произошла непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}