package edu.penzgtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.penzgtu.DataRecord; // Добавили импорт DataRecord



public class CSVParser {

    private static final Pattern DATE_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})");

    private final String delimiter;
    private final String[] headers;
    private final int priceIndex;
    private final int dateIndex;
    private final int cityIndex;

    public CSVParser(String delimiter, String[] headers, int priceIndex, int dateIndex, int cityIndex) {
        this.delimiter = delimiter;
        this.headers = headers;
        this.priceIndex = priceIndex;
        this.dateIndex = dateIndex;
        this.cityIndex = cityIndex;
    }


    public List<DataRecord> parseFile(String filePath) {
        List<DataRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            System.out.println(formatHeaders());
            String line;
            while ((line = br.readLine()) != null) {
                DataRecord record = parseLine(line);
                if(record != null)
                    records.add(record);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return records;
    }


    private DataRecord parseLine(String line) {
        String[] values = line.split(delimiter);

        if (values.length <= Math.max(cityIndex, Math.max(priceIndex, dateIndex))) {
            System.out.println("Пропускаем строку: " + line + ". Не достаточно данных");
            return null; // пропускаем строку, если данные не полные.
        }
        String city = values[cityIndex].trim();
        String dateStr = values[dateIndex].trim();
        String priceStr = values[priceIndex].trim();

        try {
            Integer year = getYearFromDate(dateStr);
            double price = parsePrice(priceStr);
            if(year == null || price <= 0)
                return null;
            System.out.println("Город: " + city + ", год: " + year + ", цена: " + price);
            return new DataRecord(city, year, price);
        } catch (Exception e) {
            System.out.println("Проблема при обработке строки: " + line + ". Ошибка: " + e.getMessage());
            return null;
        }
    }


    private  double parsePrice(String priceStr) {
        try {
            String normalizedPriceStr = priceStr.replaceAll("[^\\d.]", "");
            return Double.parseDouble(normalizedPriceStr);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при парсинге цены: " + priceStr);
            return 0;
        }
    }

    private Integer getYearFromDate(String dateStr) {
        System.out.println("Попытка извлечь год из: " + dateStr);
        try {

            LocalDate date = parseDateWithFormat(dateStr, "yyyy-MM-dd HH:mm");
            if(date != null)
                return date.getYear();

            Matcher matcher = DATE_PATTERN.matcher(dateStr);
            if(matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
            System.out.println("Не удалось распарсить дату: " + dateStr);
            return null;
        }
        catch (Exception e) {
            System.out.println("Ошибка при парсинге даты: " + e.getMessage());
            return null;
        }
    }


    private LocalDate parseDateWithFormat(String dateStr, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }


    private String formatHeaders() {
        StringBuilder sb = new StringBuilder();
        for (String header : headers) {
            sb.append(header).append(" | ");
        }
        return sb.toString();
    }
}