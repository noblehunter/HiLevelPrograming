package edu.penzgtu;

public class Main {
    public static void main(String[] args) {
        Stack<Integer> intStack = new ArrayStack<>();
        intStack.push(1);
        intStack.push(2);
        intStack.push(3);
        System.out.println("Верхний Integer элемент: " + intStack.peek()); // Должно вывести 3

        Stack<String> stringStack = new ArrayStack<>();
        stringStack.push("Привет");
        stringStack.push("Мир");
        System.out.println("Верхний String элемент: " + stringStack.peek()); // Должно вывести "Мир"

        while (!intStack.isEmpty()) {
            System.out.println("Извлекаемый Integer элемент: " + intStack.pop());
        }

        System.out.println("Является ли стек целых пустым? " + intStack.isEmpty()); // Должно вывести true
    }
}

