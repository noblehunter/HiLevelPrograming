package edu.penzgtu;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class CSVDataWorker implements Callable<CSVDataWorker.Result> {

    private final List<String[]> data;
    private final CSVDataProcessor processor;
    private final int filterColumnIndex;
    private final String filterValue;
    private final boolean caseSensitive;
    private final int sortColumnIndex;

    public CSVDataWorker(List<String[]> data, CSVDataProcessor processor, int filterColumnIndex,
                         String filterValue, boolean caseSensitive, int sortColumnIndex) {
        this.data = data;
        this.processor = processor;
        this.filterColumnIndex = filterColumnIndex;
        this.filterValue = filterValue;
        this.caseSensitive = caseSensitive;
        this.sortColumnIndex = sortColumnIndex;
    }

    @Override
    public Result call() {
        // Выполнение фильтрации, сортировки и вычислений
        List<String[]> processedData = this.data;

        if (filterColumnIndex >= 0 && !filterValue.isEmpty()) {
            processedData = processor.filterData(processedData, filterColumnIndex, filterValue, caseSensitive);
        }

        if (sortColumnIndex >= 0) {
            processedData = processor.sortData(processedData, sortColumnIndex);
        }

        Map<String, Integer> maxPricesByCity = processor.calculateMaxPricesByCity(processedData);

        return new Result(processedData, maxPricesByCity);
    }

    // Класс для хранения результата обработки данных
    public static class Result {
        private final List<String[]> processedData;
        private final Map<String, Integer> maxPricesByCity;

        public Result(List<String[]> processedData, Map<String, Integer> maxPricesByCity) {
            this.processedData = processedData;
            this.maxPricesByCity = maxPricesByCity;
        }

        public List<String[]> getProcessedData() {
            return processedData;
        }

        public Map<String, Integer> getMaxPricesByCity() {
            return maxPricesByCity;
        }
    }
}