package edu.penzgtu;

public class Book {
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
