package itmo.st.galaxy;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }
        this.author = author;
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Автор книги не может быть пустым");
        }
        this.year = year;
        if (year <= 0) {
            throw new IllegalArgumentException("Год издания книги не может быть меньше или равен 0");
        }
    }
}
