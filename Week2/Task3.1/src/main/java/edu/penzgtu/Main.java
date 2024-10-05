package edu.penzgtu;
// Обьявляю класс Author

// Основной класс Main
public class Main {
    public static void main(String[] args) {
        Author author = new Author("Джоан Кей Роулинг", 1965);
        Book book = new Book(author);
// Вывод всех данных в консоль
        System.out.println("Автор: " + book.getAuthor().getName());
        System.out.println("Год рождения: " + book.getAuthor().getBirthYear());

        // Изменение информации об авторе
        book.getAuthor().setName("Джордж Оруэлл");
        book.getAuthor().setBirthYear(1903);

        System.out.println("Автор: " + book.getAuthor().getName());
        System.out.println("Год рождения: " + book.getAuthor().getBirthYear());
    }
}