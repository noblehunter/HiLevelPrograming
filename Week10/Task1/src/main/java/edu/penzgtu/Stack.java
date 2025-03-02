package edu.penzgtu;

public interface Stack<T> {
    void push(T item); // Добавить элемент на верх стека
    T pop();           // Удалить элемент с верхушки стека и вернуть его
    T peek();          // Вернуть элемент с верхушки стека, не удаляя его
    boolean isEmpty(); // Проверить, пуст ли стек
    int size();        // Получить количество элементов в стеке
}

