import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import edu.penzgtu.DataProcessor;
import edu.penzgtu.Worker;

public class Master {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь к CSV файлу: ");
        String filePath = scanner.nextLine();

        List<DataProcessor.HouseData> houseData = DataProcessor.loadHouseData(filePath);

        int numWorkers = 4;
        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);
        int chunkSize = houseData.size() / numWorkers;
        List<Future<Map<String, List<Integer>>>> futures = new ArrayList<>();

        int startIndex = 0;
        for (int i = 0; i < numWorkers; i++) {
            int endIndex = (i == numWorkers - 1) ? houseData.size() : startIndex + chunkSize;
            List<DataProcessor.HouseData> subData = houseData.subList(startIndex, endIndex);
            futures.add(executor.submit(() -> Worker.process(subData)));
            startIndex = endIndex;
        }

        Map<String, List<Integer>> aggregatedResults = new HashMap<>();
        for (Future<Map<String, List<Integer>>> future : futures) {
            Map<String, List<Integer>> workerResults = future.get();
            workerResults.forEach((key, value) -> aggregatedResults.computeIfAbsent(key, k -> new ArrayList<>()).addAll(value));
        }
        System.out.println("Агрегированные результаты: " + aggregatedResults);

        aggregatedResults.forEach((key, values) -> {
            String[] parts = key.split("_");
            String city = parts[0];
            String year = parts[1];
            int maxPrice = values.stream().mapToInt(Integer::intValue).max().orElse(0);
            System.out.println("Город: " + city + ", Год: " + year + ", Максимальная цена: " + maxPrice);
        });

        executor.shutdown();
        scanner.close(); // Закрываем сканер
    }
}