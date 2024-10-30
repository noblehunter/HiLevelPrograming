package edu.penzgtu;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class ContainerWithMostWater extends JPanel {
    private int[] heights;
    private int maxVolume;
    private int leftIndex;
    private int rightIndex;

    public ContainerWithMostWater(int[] heights, int maxVolume, int leftIndex, int rightIndex) {
        this.heights = heights;
        this.maxVolume = maxVolume;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth() / heights.length;
        int heightScale = 10; // Масштаб высоты для визуализации

        // Рисуем вертикальные линии
        for (int i = 0; i < heights.length; i++) {
            g.drawLine(i * width, getHeight(), i * width, getHeight() - heights[i] * heightScale);
        }

        // Рисуем контейнер
        g.setColor(Color.BLUE);
        g.fillRect(leftIndex * width, getHeight() - Math.min(heights[leftIndex], heights[rightIndex]) * heightScale,
                (rightIndex - leftIndex) * width, Math.min(heights[leftIndex], heights[rightIndex]) * heightScale);

        // Выводим текст с максимальным объемом
        g.setColor(Color.BLACK);
        g.drawString("Максимальный объем воды: " + maxVolume, 10, 20);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем у пользователя ввод вертикальных линий
        System.out.println("Введите неотрицательные целые числа через запятую:");
        String input = scanner.nextLine();

        // Разбиваем строку на массив строк и преобразуем в массив целых чисел
        String[] heightStrings = input.split(",");
        int[] heights = new int[heightStrings.length];

        for (int i = 0; i < heightStrings.length; i++) {
            heights[i] = Integer.parseInt(heightStrings[i].trim());
        }

        // Вызываем метод maxArea для получения максимального объема воды
        Result result = maxArea(heights);

        // Создаем окно для визуализации
        JFrame frame = new JFrame("Визуализация контейнера с максимальным объемом воды");
        ContainerWithMostWater panel = new ContainerWithMostWater(heights, result.maxVolume, result.leftIndex, result.rightIndex);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        scanner.close();
    }

    // Метод для вычисления максимального объема воды и возвращает результат
    public static Result maxArea(int[] heights) {
        int left = 0; // Указатель на левую линию
        int right = heights.length - 1; // Указатель на правую линию
        int maxVolume = 0; // Изначально максимальный объем равен 0
        int leftIndex = 0;
        int rightIndex = 0;

        // Используем два указателя для нахождения максимального объема
        while (left < right) {
            // Вычисляем текущий объем
            int currentVolume = Math.min(heights[left], heights[right]) * (right - left);
            // Обновляем максимальный объем, если текущий больше
            if (currentVolume > maxVolume) {
                maxVolume = currentVolume;
                leftIndex = left;  // Сохраняем индексы
                rightIndex = right;
            }

            // Перемещаем указатель с меньшей высотой
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return new Result(maxVolume, leftIndex, rightIndex); // Возвращаем максимальный объем и индексы
    }

    // Класс для хранения результата
    static class Result {
        int maxVolume;
        int leftIndex;
        int rightIndex;

        Result(int maxVolume, int leftIndex, int rightIndex) {
            this.maxVolume = maxVolume;
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
        }
    }
}
