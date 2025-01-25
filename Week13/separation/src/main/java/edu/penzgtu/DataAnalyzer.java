package edu.penzgtu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAnalyzer {

    public Map<String, Map<Integer, Double>> analyze(List<DataRecord> records) {
        Map<String, Map<Integer, Double>> maxPriceByCityAndYear = new HashMap<>();

        for (DataRecord record : records) {
            String city = record.getCity();
            int year = record.getYear();
            double price = record.getPrice();
            maxPriceByCityAndYear.computeIfAbsent(city, k -> new HashMap<>())
                    .compute(year, (k, currentMax) -> currentMax == null || price > currentMax ? price : currentMax);
        }
        return maxPriceByCityAndYear;
    }

}
