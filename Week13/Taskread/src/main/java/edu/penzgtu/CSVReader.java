import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {

    public static List<String[]> readCSV(String filePath, String delimiter) {
        List<String[]> records = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                records.add(values);
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace(); // Выводим stack trace для отладки
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии BufferedReader: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return records;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath;
        String delimiter = ","; // Разделитель по умолчанию

        System.out.print("Введите путь к CSV файлу: ");
        filePath = scanner.nextLine();

        List<String[]> csvData = readCSV(filePath, delimiter);

        if(csvData != null) {
            for (String[] record : csvData) {
                for (String value : record) {
                    System.out.print(value + " | ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Не удалось прочитать данные из файла " + filePath);
        }
        scanner.close();
    }
}


