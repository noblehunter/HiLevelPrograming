package edu.penzgtu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import edu.penzgtu.DataProcessor.HouseData;  // <--- добавили импорт

public class Worker {
    public static Map<String, List<Integer>> process(List<HouseData> data) {
        Map<String, List<Integer>> cityYearPrices = new HashMap<>();
        for (HouseData house : data) {
            String city = house.getCity();
            int year = 0;
            try {
                year = Integer.parseInt(house.getDateOfTransfer().substring(0, 4));
            } catch (NumberFormatException e) {
                System.err.println("Не удалось преобразовать год: " + house.getDateOfTransfer());
                continue;
            }

            int price = 0;
            try {
                price = Integer.parseInt(house.getPricePaid());
            } catch (NumberFormatException e) {
                System.err.println("Не удалось преобразовать цену: " + house.getPricePaid());
                continue;
            }

            cityYearPrices.computeIfAbsent(city + "_" + year, k -> new ArrayList<>()).add(price);
        }
        return cityYearPrices;
    }
}
