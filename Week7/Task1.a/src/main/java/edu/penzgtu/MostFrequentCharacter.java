import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MostFrequentCharacter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку: ");
        String input = scanner.nextLine();

        Optional<Map.Entry<Character, Long>> result = findMostFrequentCharacter(input);

        if (result.isPresent()) {
            Map.Entry<Character, Long> entry = result.get();
            System.out.println("Самый часто встречающийся символ: '" + entry.getKey() + "' с количеством повторов: " + entry.getValue());
        } else {
            System.out.println("Строка пустая.");
        }

        scanner.close();
    }

    public static Optional<Map.Entry<Character, Long>> findMostFrequentCharacter(String s) {
        if (s == null || s.isEmpty()) {
            return Optional.empty(); // Возвращаем пустой Optional для пустой строки
        }

        // Подсчет частоты символов с использованием Stream API
        return s.chars()
                .mapToObj(c -> (char) c) // Преобразование int в char
                .collect(Collectors.groupingBy(c -> c, Collectors.counting())) // Группировка и подсчет
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue)); // Нахождение максимальной частоты
    }
}

