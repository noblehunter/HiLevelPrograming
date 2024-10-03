package edu.penzgtu;

class Author {
    private String name;
    private int birthYear;

    public Author(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}

class Book {
    private Author author;

    public Book(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}

public class Main {
    public static void main(String[] args) {
        Author author = new Author("Джоан Кей Роулинг", 1965);
        Book book = new Book(author);

        System.out.println("Автор: " + book.getAuthor().getName());
        System.out.println("Год рождения: " + book.getAuthor().getBirthYear());

        // Изменение информации об авторе
        book.getAuthor().setName("Джордж Оруэлл");
        book.getAuthor().setBirthYear(1903);

        System.out.println("Автор: " + book.getAuthor().getName());
        System.out.println("Год рождения: " + book.getAuthor().getBirthYear());
    }
}

