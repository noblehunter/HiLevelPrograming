import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CSVReader {

    public static List<String[]> readCSV(String filePath, String delimiter) {
        List<String[]> records = new ArrayList<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.err.println("Ошибка: Файл не существует: " + filePath);
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Игнорируем пустые строки
                }
                String[] values = line.split(delimiter, -1);
                records.add(values);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return records;
    }

    public static void printRecords(List<String[]> records, List<Integer> columnWidths) {
        if (records == null || records.isEmpty()) {
            System.out.println("Нет данных для вывода.");
            return;
        }
        for (String[] record : records) {
            for (int i = 0; i < record.length; i++) {
                String value = record[i];
                int width = (i < columnWidths.size()) ? columnWidths.get(i) : 15; // 15 - значение по умолчанию
                System.out.printf("%-" + width + "s| ", value.length() > width ? value.substring(0, width - 3) + "..." : value); // усекаем длинные строки
            }
            System.out.println();
        }
    }

    public static List<Integer> calculateColumnWidths(List<String[]> records) {
        if (records == null || records.isEmpty()) return new ArrayList<>();
        int columnCount = records.get(0).length;
        List<Integer> columnWidths = new ArrayList<>(columnCount);

        for (int i = 0; i < columnCount; i++) {
            int maxWidth = 0;
            for (String[] record : records) {
                if (record.length > i){
                    maxWidth = Math.max(maxWidth, record[i].length());
                }
            }
            columnWidths.add(maxWidth + 2);
        }
        return columnWidths;
    }

    public static List<String[]> filterData(List<String[]> csvData, int filterColumnIndex, String filterValue, boolean caseSensitive) {
        if (csvData == null || csvData.isEmpty() || filterColumnIndex < 0) return new ArrayList<>();
        return csvData.stream()
                .filter(record -> record.length > filterColumnIndex &&
                        (caseSensitive ? record[filterColumnIndex].contains(filterValue) : record[filterColumnIndex].toLowerCase().contains(filterValue.toLowerCase())))
                .collect(Collectors.toList());
    }

    public static List<String[]> sortData(List<String[]> data, int sortColumnIndex) {
        if (data == null || data.isEmpty() || sortColumnIndex < 0) return new ArrayList<>();

        return data.stream()
                .sorted(Comparator.comparing(record -> {
                    if (record == null || record.length <= sortColumnIndex) return 0;
                    try{
                        return Integer.parseInt(record[sortColumnIndex].replaceAll("\"", ""));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }))
                .collect(Collectors.toList());
    }


    public static void exportToCSV(List<String[]> data, String filePath, String delimiter) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] record : data) {
                bw.write(String.join(delimiter, record));
                bw.newLine();
            }
            System.out.println("Данные успешно экспортированы в: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath;
        String delimiter;
        int filterColumnIndex = -1;
        String filterValue = "";
        int sortColumnIndex = -1;
        final int cityIndex = 9; // Индекс столбца, где находится город.
        final int priceIndex = 1; // Индекс столбца, где находится цена.

        System.out.print("Введите путь к CSV файлу: ");
        filePath = scanner.nextLine();

        System.out.print("Введите разделитель (или нажмите Enter для ','): ");
        String inputDelimiter = scanner.nextLine();
        delimiter = inputDelimiter.isEmpty() ? "," : inputDelimiter;

        if (delimiter.isEmpty()) {
            System.err.println("Разделитель не может быть пустым, использован разделитель по умолчанию (',')");
            delimiter = ",";
        }

        List<String[]> csvData = readCSV(filePath, delimiter);

        if (csvData != null) {
            List<Integer> columnWidths = calculateColumnWidths(csvData);
            System.out.println("\nВсе данные:");
            printRecords(csvData, columnWidths);


            //Вывод всех уникальных городов и максимальных цен:
            Map<String, Integer> maxPricesByCity = new HashMap<>();
            for (String[] record : csvData) {
                if (record.length > cityIndex && record.length > priceIndex) {
                    String city = record[cityIndex];
                    try {
                        int price = Integer.parseInt(record[priceIndex].replaceAll("\"", ""));
                        maxPricesByCity.compute(city, (key, oldValue) ->
                                oldValue == null || price > oldValue ? price : oldValue);
                    } catch (NumberFormatException e) {
                        System.err.println("Некорректный формат цены, строка не обработана: " + record[priceIndex]);
                    }
                }
            }

            System.out.println("\nУникальные города и их максимальные цены:");
            maxPricesByCity.forEach((city, maxPrice) -> System.out.println(city + ": " + maxPrice));


            System.out.print("Введите индекс столбца для фильтрации (начиная с 0, или Enter, если фильтрация не нужна): ");
            String filterInput = scanner.nextLine();

            if (!filterInput.isEmpty()){
                try{
                    filterColumnIndex = Integer.parseInt(filterInput);
                    if (filterColumnIndex >= 0 && filterColumnIndex < csvData.get(0).length){
                        System.out.print("Введите значение для фильтрации: ");
                        filterValue = scanner.nextLine();

                        System.out.print("Учитывать регистр при фильтрации? (y/n) по умолчанию: n): ");
                        String caseSensitiveInput = scanner.nextLine().trim().toLowerCase();
                        boolean caseSensitive = caseSensitiveInput.equals("y");
                        csvData = filterData(csvData, filterColumnIndex, filterValue, caseSensitive);
                        System.out.println("Отфильтрованные данные:");
                        columnWidths = calculateColumnWidths(csvData);
                        printRecords(csvData, columnWidths);
                    }
                    else{
                        System.out.println("Некорректный индекс столбца для фильтрации, фильтрация не будет произведена.");
                    }

                }
                catch (NumberFormatException ex){
                    System.out.println("Некорректный формат индекса столбца для фильтрации, фильтрация не будет произведена.");
                }
            }
            System.out.print("Введите индекс столбца для сортировки (начиная с 0, или Enter, если сортировка не нужна): ");
            String sortInput = scanner.nextLine();

            if (!sortInput.isEmpty()){
                try{
                    sortColumnIndex = Integer.parseInt(sortInput);
                    if(sortColumnIndex >= 0 && sortColumnIndex < csvData.get(0).length){
                        csvData = sortData(csvData, sortColumnIndex);
                        System.out.println("Отсортированные данные:");
                        columnWidths = calculateColumnWidths(csvData);
                        printRecords(csvData, columnWidths);
                    } else{
                        System.out.println("Некорректный индекс столбца для сортировки, сортировка не будет произведена.");
                    }
                }
                catch (NumberFormatException ex){
                    System.out.println("Некорректный формат индекса столбца для сортировки, сортировка не будет произведена.");
                }
            }

            System.out.print("Введите путь для сохранения отфильтрованных данных (или Enter, чтобы пропустить): ");
            String exportPath = scanner.nextLine();

            if(!exportPath.isEmpty()) {
                exportToCSV(csvData, exportPath, delimiter);
            }
        } else {
            System.out.println("Не удалось прочитать данные из файла " + filePath);
        }
        scanner.close();
    }
}