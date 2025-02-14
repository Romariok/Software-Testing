package itmo.st;

import itmo.st.galaxy.Book;
import itmo.st.galaxy.Editor;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class GalaxyCreationTest {

    @Test
    void testBookCreationWithValidData() {
        Book book = new Book("Война и мир", "Лев Толстой", 1869);
        assertNotNull(book);
        assertEquals("Война и мир", book.getTitle());
        assertEquals("Лев Толстой", book.getAuthor());
        assertEquals(1869, book.getYear());
    }

    @Test
    void testBookCreationWithEmptyTitle() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "Аноним", 2000);
        });
        assertEquals("Название книги не может быть пустым", exception.getMessage());
    }

    @Test
    void testBookCreationWithNullAuthor() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("Книга", null, 2020);
        });
        assertEquals("Автор книги не может быть пустым", exception.getMessage());
    }

    @Test
    void testBookCreationWithNullTitle() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book(null, "Аноним", 2000);
        });
        assertEquals("Название книги не может быть пустым", exception.getMessage());
    }

    @Test
    void testBookCreationWithEmptyAuthor() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("Книга", "", 2020);
        });
        assertEquals("Автор книги не может быть пустым", exception.getMessage());
    }

    @Test
    void testBookCreationWithNegativeYear() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("Книга", "Автор", -1);
        });
        assertEquals("Год издания книги не может быть меньше или равен 0", exception.getMessage());
    }

    @Test
    void testEditorCreationWithValidData() {
        Date date = new Date();
        Editor editor = new Editor("Александр Пушкин", date);
        assertNotNull(editor);
        assertEquals("Александр Пушкин", editor.getName());
        assertEquals(date, editor.getBirthDate());
    }

    @Test
    void testEditorCreationWithEmptyName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Editor("", new Date());
        });
        assertEquals("Имя редактора не может быть пустым", exception.getMessage());
    }

    @Test
    void testEditorCreationWithNullName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Editor(null, new Date());
        });
        assertEquals("Имя редактора не может быть пустым", exception.getMessage());
    }

    @Test
    void testEditorCreationWithNullDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Editor("Иван Иванов", null);
        });
        assertEquals("Дата рождения не может быть пустой", exception.getMessage());
    }

    @Test
    void testEditorCreationWithFutureDate() {
        Date futureDate = new Date(System.currentTimeMillis() + 1000000);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Editor("Федор Достоевский", futureDate);
        });
        assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }

    @Test
    void testEditorCreationWithPastDate() {
        Date pastDate = new Date(-1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Editor("Федор Достоевский", pastDate);
        });
        assertEquals("Дата рождения не может быть меньше 0", exception.getMessage());
    }
}
