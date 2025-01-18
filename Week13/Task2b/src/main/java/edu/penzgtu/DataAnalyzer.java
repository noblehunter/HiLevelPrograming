package edu.penzgtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Pattern;

public class DataAnalyzer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the path to the CSV file:");
        String filePath = scanner.nextLine();

        System.out.println("Enter the column number for the price (starting from 1):");
        int priceColumn = scanner.nextInt();
        scanner.nextLine();


        System.out.println("Enter the column number for grouping (starting from 1):");
        int groupingColumn = scanner.nextInt();
        scanner.nextLine();

        List<String> lines = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\",\"(.+?)\"");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        Map<String, Double> groupPrices = new HashMap<>();
        Map<String, Integer> groupCounts = new HashMap<>();

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new PropertyProcessor(lines, 0, lines.size(), groupPrices, groupCounts, priceColumn, groupingColumn,pattern));

        for (Map.Entry<String, Double> entry : groupPrices.entrySet()) {
            String groupingValue = entry.getKey();
            double totalPrice = entry.getValue();
            int count = groupCounts.get(groupingValue);
            double averagePrice = totalPrice / count;
            System.out.printf("Grouping Value: %s, Average Price: %.2f%n", groupingValue, averagePrice);
        }
        pool.shutdown();
    }
}