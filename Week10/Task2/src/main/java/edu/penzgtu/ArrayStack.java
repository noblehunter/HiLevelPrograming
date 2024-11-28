package edu.penzgtu;

import java.util.Arrays;

public class ArrayStack<T> implements Stack<T> {
    private T[] stack;
    private int top;
    private static final int INITIAL_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        stack = (T[]) new Object[INITIAL_CAPACITY];
        top = -1;
    }

    @Override
    public void push(T item) {
        if (top == stack.length - 1) {
            resize();
        }
        stack[++top] = item;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T item = stack[top];
        stack[top--] = null; // Удаляем ссылку для сборщика мусора
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack[top];
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public int size() {
        return top + 1;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newSize = stack.length * 2;
        stack = Arrays.copyOf(stack, newSize);
    }
}

