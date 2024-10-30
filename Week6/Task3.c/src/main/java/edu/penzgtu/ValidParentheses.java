package edu.penzgtu;

import java.util.Scanner;
import java.util.Stack;

public class ValidParentheses {

    public static boolean isValid(String s) {
        // Создаем стек для хранения открывающих скобок
        Stack<Character> stack = new Stack<>();

        // Проходим по всем символам входной строки
        for (char c : s.toCharArray()) {
            // Если символ - это открывающая скобка, добавляем его в стек
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                // Если стек пуст, а символ - закрывающая скобка, возвращаем false
                if (stack.isEmpty()) {
                    return false;
                }
                // Проверяем соответствие закрывающей скобки с последней открывающей
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                        (c == '}' && top != '{') ||
                        (c == ']' && top != '[')) {
                    return false;
                }
            }
        }

        // Если стек пуст, все скобки были закрыты правильно
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрос ввода строки от пользователя
        System.out.print("Введите строку, содержащую только скобки '(', ')', '{', '}', '[' и ']': ");
        String input = scanner.nextLine();

        // Проверяем валидность строки и выводим результат
        if (isValid(input)) {
            System.out.println("Строка валидна.");
        } else {
            System.out.println("Строка не валидна.");
        }

        scanner.close();
    }
}

