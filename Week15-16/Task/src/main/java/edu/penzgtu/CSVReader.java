package edu.penzgtu;

import java.util.*;
import java.util.concurrent.*;

public class CSVReader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath;
        String delimiter;
        int filterColumnIndex = -1;
        String filterValue = "";
        int sortColumnIndex = -1;
        String exportPath = "";
        int numThreads = 4; // Значение по умолчанию

        System.out.print("Введите путь к CSV файлу: ");
        filePath = scanner.nextLine();

        System.out.print("Введите разделитель (или нажмите Enter для ','): ");
        String inputDelimiter = scanner.nextLine();
        delimiter = inputDelimiter.isEmpty() ? "," : inputDelimiter;

        if (delimiter.isEmpty()) {
            System.err.println("Разделитель не может быть пустым, использован разделитель по умолчанию (',')");
            delimiter = ",";
        }
        System.out.print("Введите количество потоков (или Enter для 4): ");
        String threadsInput = scanner.nextLine();
        if(!threadsInput.isEmpty()){
            try {
                numThreads = Integer.parseInt(threadsInput);
            } catch (NumberFormatException ex) {
                System.err.println("Некоректный формат числа потоков, будет использовано значение по умолчанию (4)");
            }
        }

        CSVDataProcessor processor = new CSVDataProcessor();
        List<String[]> csvData = processor.readCSV(filePath, delimiter);

        if (csvData != null) {
            CSVPrinter printer = new CSVPrinter();
            List<Integer> columnWidths = processor.calculateColumnWidths(csvData);
            System.out.println("\nВсе данные:");
            printer.printRecords(csvData, columnWidths);

            processor.printUniqueCitiesAndMaxPrices(csvData);
            processor.printPriceHistogram(csvData);

            System.out.print("Введите индекс столбца для фильтрации (начиная с 0, или Enter, если фильтрация не нужна): ");
            String filterInput = scanner.nextLine();

            if (!filterInput.isEmpty()) {
                try {
                    filterColumnIndex = Integer.parseInt(filterInput);
                    if (filterColumnIndex >= 0 && filterColumnIndex < csvData.get(0).length) {
                        System.out.print("Введите значение для фильтрации: ");
                        filterValue = scanner.nextLine();

                        System.out.print("Учитывать регистр при фильтрации? (y/n) по умолчанию: n): ");
                        String caseSensitiveInput = scanner.nextLine().trim().toLowerCase();
                        boolean caseSensitive = caseSensitiveInput.equals("y");

                        // Многопоточная обработка с фильтрацией
                        List<String[]> filteredData = processDataWithWorkers(csvData, processor, numThreads, filterColumnIndex, filterValue, caseSensitive, -1);
                        if (filteredData != null){
                            System.out.println("Отфильтрованные данные:");
                            columnWidths = processor.calculateColumnWidths(filteredData);
                            printer.printRecords(filteredData, columnWidths);
                            csvData = filteredData; // Обновление csvData
                        }

                    } else {
                        System.out.println("Некорректный индекс столбца для фильтрации, фильтрация не будет произведена.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Некорректный формат индекса столбца для фильтрации, фильтрация не будет произведена.");
                }
            }

            System.out.print("Введите индекс столбца для сортировки (начиная с 0, или Enter, если сортировка не нужна): ");
            String sortInput = scanner.nextLine();

            if (!sortInput.isEmpty()) {
                try {
                    sortColumnIndex = Integer.parseInt(sortInput);
                    if (sortColumnIndex >= 0 && sortColumnIndex < csvData.get(0).length){
                        List<String[]> sortedData = processDataWithWorkers(csvData, processor, numThreads, -1, "", false, sortColumnIndex);
                        if(sortedData != null) {
                            System.out.println("Отсортированные данные:");
                            columnWidths = processor.calculateColumnWidths(sortedData);
                            printer.printRecords(sortedData, columnWidths);
                            csvData = sortedData; // Обновление csvData
                        }

                    } else {
                        System.out.println("Некорректный индекс столбца для сортировки, сортировка не будет произведена.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Некорректный формат индекса столбца для сортировки, сортировка не будет произведена.");
                }
            }
            System.out.print("Введите путь для сохранения отфильтрованных данных (или Enter, чтобы пропустить): ");
            exportPath = scanner.nextLine();

            if(!exportPath.isEmpty()) {
                CSVExporter exporter = new CSVExporter();
                exporter.exportToCSV(csvData, exportPath, delimiter);
            }

        } else {
            System.out.println("Не удалось прочитать данные из файла " + filePath);
        }
        scanner.close();
    }


    private static List<String[]> processDataWithWorkers(List<String[]> csvData, CSVDataProcessor processor, int numThreads, int filterColumnIndex, String filterValue, boolean caseSensitive, int sortColumnIndex) {
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<CSVDataWorker.Result>> futures = new ArrayList<>();
        int chunkSize = (int) Math.ceil((double) csvData.size() / numThreads);

        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * chunkSize;
            int endIndex = Math.min(startIndex + chunkSize, csvData.size());
            List<String[]> chunk = csvData.subList(startIndex, endIndex);
            CSVDataWorker worker = new CSVDataWorker(chunk, processor, filterColumnIndex, filterValue, caseSensitive, sortColumnIndex);
            futures.add(executorService.submit(worker));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String[]> processedData = new ArrayList<>();
        for (Future<CSVDataWorker.Result> future : futures) {
            try {
                CSVDataWorker.Result result = future.get();
                processedData.addAll(result.processedData());
                // Объединение результатов maxPricesByCity и т.д.
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }
        return processedData;
    }
}