package edu.penzgtu;

import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyProcessor extends RecursiveAction {
    private final List<String> lines;
    private final int start;
    private final int end;
    private final Map<String, Double> groupPrices;
    private final Map<String, Integer> groupCounts;
    private final int priceColumn;
    private final int groupingColumn;
    private final Pattern pattern;


    public PropertyProcessor(List<String> lines, int start, int end, Map<String, Double> groupPrices, Map<String, Integer> groupCounts, int priceColumn, int groupingColumn,Pattern pattern) {
        this.lines = lines;
        this.start = start;
        this.end = end;
        this.groupPrices = groupPrices;
        this.groupCounts = groupCounts;
        this.priceColumn = priceColumn;
        this.groupingColumn = groupingColumn;
        this.pattern = pattern;
    }

    @Override
    protected void compute() {
        if (end - start <= 10) { //Sequential threshold - adjust as needed
            processLinesSequentially();
        } else {
            int mid = (start + end) / 2;
            invokeAll(new PropertyProcessor(lines, start, mid, groupPrices, groupCounts, priceColumn, groupingColumn, pattern),
                    new PropertyProcessor(lines, mid, end, groupPrices, groupCounts, priceColumn, groupingColumn, pattern));
        }
    }

    private void processLinesSequentially() {

        for (int i = start; i < end; i++) {
            String line = lines.get(i);
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                try{
                    double price = Double.parseDouble(matcher.group(priceColumn));
                    String groupingValue = matcher.group(groupingColumn);

                    groupPrices.compute(groupingValue, (k, v) -> (v == null ? 0 : v) + price);
                    groupCounts.compute(groupingValue, (k, v) -> (v == null ? 0 : v) + 1);
                } catch (NumberFormatException e){
                    System.err.println("Error parsing line: " + line);
                }

            }
        }
    }
}