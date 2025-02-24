package itmo.st;

import itmo.st.galaxy.Book;
import itmo.st.galaxy.Editor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Date;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class GalaxyCreationTest {
    private static final String VALID_BOOK_TITLE = "Война и мир";
    private static final String VALID_AUTHOR = "Лев Толстой";
    private static final int VALID_YEAR = 1869;
    private static final String VALID_EDITOR_NAME = "Александр Пушкин";

    private Date validDate;
    private Date futureDate;
    private Date pastDate;

    @BeforeEach
    void setUp() {
        validDate = new Date();
        futureDate = new Date(System.currentTimeMillis() + 1000000);
        pastDate = new Date(-1);
    }

    @Test
    void testBookCreationWithValidData() {
        Book book = new Book(VALID_BOOK_TITLE, VALID_AUTHOR, VALID_YEAR);
        assertNotNull(book);
        assertEquals(VALID_BOOK_TITLE, book.getTitle());
        assertEquals(VALID_AUTHOR, book.getAuthor());
        assertEquals(VALID_YEAR, book.getYear());
    }

    private static Stream<Arguments> invalidBookDataProvider() {
        return Stream.of(
                Arguments.of("", "Аноним", 2000, "Название книги не может быть пустым"),
                Arguments.of("Книга", null, 2020, "Автор книги не может быть пустым"),
                Arguments.of(null, "Аноним", 2000, "Название книги не может быть пустым"),
                Arguments.of("Книга", "", 2020, "Автор книги не может быть пустым"),
                Arguments.of("Книга", "Автор", -1, "Год издания книги не может быть меньше или равен 0"));
    }

    @ParameterizedTest
    @MethodSource("invalidBookDataProvider")
    void testBookCreationWithInvalidData(String title, String author, int year, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book(title, author, year);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testEditorCreationWithValidData() {
        Editor editor = new Editor(VALID_EDITOR_NAME, validDate);
        assertNotNull(editor);
        assertEquals(VALID_EDITOR_NAME, editor.getName());
        assertEquals(validDate, editor.getBirthDate());
    }

    private static Stream<Arguments> invalidEditorDataProvider() {
        return Stream.of(
                Arguments.of("", "Имя редактора не может быть пустым"),
                Arguments.of(null, "Имя редактора не может быть пустым"));
    }

    @ParameterizedTest
    @MethodSource("invalidEditorDataProvider")
    void testEditorCreationWithInvalidName(String name, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Editor(name, validDate);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testEditorCreationWithNullDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Editor(VALID_EDITOR_NAME, null);
        });
        assertEquals("Дата рождения не может быть пустой", exception.getMessage());
    }

    @Test
    void testEditorCreationWithFutureDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Editor(VALID_EDITOR_NAME, futureDate);
        });
        assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }

    @Test
    void testEditorCreationWithPastDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Editor(VALID_EDITOR_NAME, pastDate);
        });
        assertEquals("Дата рождения не может быть меньше 0", exception.getMessage());
    }
}
