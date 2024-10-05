package edu.penzgtu;
// Обьявляю класс Author
class Author {
    private String name;
    private int birthYear;

    public Author(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }
    // Метод для получения имени автора
    public String getName() {
        return name;
    }
    // Метод для установки нового имени автора
    public void setName(String name) {
        this.name = name;
    }
    // Метод для получения года рождения автора
    public int getBirthYear() {
        return birthYear;
    }
    // Метод для установки нового года рождения автора
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
// Обьявляю класс Book
class Book {
    private Author author;

    public Book(Author author) {
        this.author = author;
    }
    // Метод для получения автора книги
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
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