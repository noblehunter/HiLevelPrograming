package edu.penzgtu;

import java.util.Arrays;

public class ArrayStack<T> implements Stack<T> {
    private T[] stack; // Массив для хранения элементов стека
    private int top;    // Индекс верхнего элемента стека
    private static final int INITIAL_CAPACITY = 10; // Начальная емкость стека

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        stack = (T[]) new Object[INITIAL_CAPACITY]; // Инициализация массива
        top = -1; // Стек изначально пуст
    }

    @Override
    public void push(T item) {
        if (top == stack.length - 1) { // Проверка переполнения
            resize(); // Увеличение размера массива
        }
        stack[++top] = item; // Добавление элемента на верх стека
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст"); // Исключение при попытке извлечь из пустого стека
        }
        T item = stack[top]; // Получение верхнего элемента
        stack[top--] = null; // Удаление ссылки для сборщика мусора и уменьшение индекса
        return item; // Возврат извлеченного элемента
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст"); // Исключение при попытке посмотреть верхний элемент пустого стека
        }
        return stack[top]; // Возврат верхнего элемента без его удаления
    }

    @Override
    public boolean isEmpty() {
        return top == -1; // Проверка на пустоту стека
    }

    @Override
    public int size() {
        return top + 1; // Возврат количества элементов в стеке
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newSize = stack.length * 2; // Увеличение размера массива в 2 раза
        stack = Arrays.copyOf(stack, newSize); // Копирование элементов в новый массив
    }
}

