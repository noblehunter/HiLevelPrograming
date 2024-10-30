import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Permutations {

    public static void main(String[] args) {
        // Создаем объект Scanner для считывания ввода пользователя
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем у пользователя ввод чисел через пробел
        System.out.println("Введите числа через пробел:");
        String input = scanner.nextLine();

        // Разбиваем введенную строку на отдельные элементы
        String[] inputNumbers = input.split(" ");
        List<Integer> numbers = new ArrayList<>();

        // Преобразуем каждый элемент в целое число и добавляем в список
        for (String num : inputNumbers) {
            numbers.add(Integer.parseInt(num));
        }

        // Генерируем все возможные перестановки
        List<List<Integer>> result = perms(numbers);

        // Выводим все найденные перестановки
        System.out.println("Все возможные перестановки:");
        for (List<Integer> permutation : result) {
            System.out.println(permutation);
        }

        // Закрываем сканер
        scanner.close();
    }

    // Рекурсивная функция для генерации всех перестановок
    public static List<List<Integer>> perms(List<Integer> nums) {
        // Список для хранения всех перестановок
        List<List<Integer>> result = new ArrayList<>();

        // Базовый случай: если список пуст, добавляем пустую перестановку
        if (nums.isEmpty()) {
            result.add(new ArrayList<>()); // Добавляем пустую перестановку
            return result;
        }

        // Проходим по каждому элементу в списке
        for (int i = 0; i < nums.size(); i++) {
            // Сохраняем текущий элемент
            Integer currentNum = nums.get(i);
            // Создаем новый список без текущего элемента
            List<Integer> remaining = new ArrayList<>(nums);
            remaining.remove(i); // Убираем текущий элемент

            // Рекурсивно вызываем perms для оставшихся чисел
            for (List<Integer> permutation : perms(remaining)) {
                // Добавляем текущий элемент в начало каждой перестановки
                permutation.add(0, currentNum); // Добавляем текущий элемент в начало
                // Добавляем полученную перестановку в результат
                result.add(permutation);
            }
        }

        // Возвращаем все найденные перестановки
        return result;
    }
}


