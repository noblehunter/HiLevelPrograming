package edu.penzgtu;

public class Main {
    public static void main(String[] args) {
        Stack<Integer> stack = new ArrayStack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println("Верхний элемент: " + stack.peek()); // Должно вывести 3
        System.out.println("Размер стека: " + stack.size()); // Должно вывести 3

        System.out.println("Извлекаемый элемент: " + stack.pop()); // Должно вывести 3
        System.out.println("Размер стека после: " + stack.size()); // Должно вывести 2

        while (!stack.isEmpty()) {
            System.out.println("Извлекаемый элемент: " + stack.pop());
        }

        // Проверка на пустоту
        System.out.println("Стек пуст? " + stack.isEmpty()); // Должно вывести true
    }
}

