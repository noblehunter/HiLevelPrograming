package edu.penzgtu;

public class Main {
    public static void main(String[] args) {
        // Использование стека для Integer
        Stack<Integer> intStack = new ArrayStack<>();
        intStack.push(1);
        intStack.push(2);
        intStack.push(3);
        System.out.println("Верхний Integer элемент: " + intStack.peek()); // Должно вывести 3

        // Использование стека для Double
        Stack<Double> doubleStack = new ArrayStack<>();
        doubleStack.push(1.1);
        doubleStack.push(2.2);
        doubleStack.push(3.3);
        System.out.println("Верхний Double элемент: " + doubleStack.peek()); // Должно вывести 3.3

        // Использование стека для String
        Stack<String> stringStack = new ArrayStack<>();
        stringStack.push("Привет");
        stringStack.push("Мир");
        System.out.println("Верхний String элемент: " + stringStack.peek()); // Должно вывести "World"

        // Проверка на пустоту
        System.out.println("Является ли стек пустым? " + intStack.isEmpty()); // Должно вывести false
    }
}

