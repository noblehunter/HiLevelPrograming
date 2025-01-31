package edu.penzgtu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Scanner;

public class CSVReader {

    private static final Logger logger = LogManager.getLogger(CSVReader.class);
    public static void main(String[] args) {
        logger.info("Запуск CSVReader");
        Scanner scanner = new Scanner(System.in);
        String filePath;
        String delimiter;
        int filterColumnIndex = -1;
        String filterValue = "";
        int sortColumnIndex = -1;
        String exportPath = "";

        System.out.print("Введите путь к CSV файлу: ");
        filePath = scanner.nextLine();

        System.out.print("Введите разделитель (или нажмите Enter для ','): ");
        String inputDelimiter = scanner.nextLine();
        delimiter = inputDelimiter.isEmpty() ? "," : inputDelimiter;

        if (delimiter.isEmpty()) {
            System.err.println("Разделитель не может быть пустым, использован разделитель по умолчанию (',')");
            delimiter = ",";
        }
        logger.info("Путь к файлу: {}, разделитель: {}", filePath, delimiter);
        CSVDataProcessor processor = new CSVDataProcessor();
        List<String[]> csvData = processor.readCSV(filePath, delimiter);

        if (csvData != null) {
            CSVPrinter printer = new CSVPrinter();
            List<Integer> columnWidths = processor.calculateColumnWidths(csvData);
            logger.info("Вывод всех данных:");
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
                        logger.info("Фильтрация по столбцу {}, значению {}, с учетом регистра: {}", filterColumnIndex, filterValue, caseSensitive);

                        csvData = processor.filterData(csvData, filterColumnIndex, filterValue, caseSensitive);
                        System.out.println("Отфильтрованные данные:");
                        columnWidths = processor.calculateColumnWidths(csvData);
                        printer.printRecords(csvData, columnWidths);
                    } else {
                        System.out.println("Некорректный индекс столбца для фильтрации, фильтрация не будет произведена.");
                        logger.warn("Некорректный индекс столбца для фильтрации: {}", filterColumnIndex);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Некорректный формат индекса столбца для фильтрации, фильтрация не будет произведена.");
                    logger.error("Ошибка при парсинге индекса для фильтрации", ex);
                }
            }

            System.out.print("Введите индекс столбца для сортировки (начиная с 0, или Enter, если сортировка не нужна): ");
            String sortInput = scanner.nextLine();

            if (!sortInput.isEmpty()) {
                try {
                    sortColumnIndex = Integer.parseInt(sortInput);
                    if (sortColumnIndex >= 0 && sortColumnIndex < csvData.get(0).length){
                        logger.info("Сортировка по столбцу: {}", sortColumnIndex);
                        csvData = processor.sortData(csvData, sortColumnIndex);
                        System.out.println("Отсортированные данные:");
                        columnWidths = processor.calculateColumnWidths(csvData);
                        printer.printRecords(csvData, columnWidths);
                    } else {
                        System.out.println("Некорректный индекс столбца для сортировки, сортировка не будет произведена.");
                        logger.warn("Некорректный индекс столбца для сортировки: {}", sortColumnIndex);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Некорректный формат индекса столбца для сортировки, сортировка не будет произведена.");
                    logger.error("Ошибка при парсинге индекса для сортировки", ex);
                }
            }
            System.out.print("Введите путь для сохранения отфильтрованных данных (или Enter, чтобы пропустить): ");
            exportPath = scanner.nextLine();

            if(!exportPath.isEmpty()) {
                logger.info("Экспорт данных в файл: {}", exportPath);
                CSVExporter exporter = new CSVExporter();
                exporter.exportToCSV(csvData, exportPath, delimiter);
            }

        } else {
            System.out.println("Не удалось прочитать данные из файла " + filePath);
            logger.error("Не удалось прочитать данные из файла: {}", filePath);
        }
        logger.info("Завершение работы CSVReader");
        scanner.close();
    }
}
