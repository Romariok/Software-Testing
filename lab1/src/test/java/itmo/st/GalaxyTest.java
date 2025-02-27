package itmo.st;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import itmo.st.galaxy.Book;
import itmo.st.galaxy.BookState;
import itmo.st.galaxy.Editor;

public class GalaxyTest {
    private static final String EDITOR_NAME = "Иван Петров";
    private static final String HITCHHIKERS_TITLE = "Путеводитель по Галактике для автостопщиков";
    private static final String HITCHHIKERS_AUTHOR = "Дуглас Адамс";
    private static final int HITCHHIKERS_YEAR = 1979;
    private static final String REGULAR_TITLE = "Обычная книга";
    private static final String REGULAR_AUTHOR = "Обычный автор";
    private static final int REGULAR_YEAR = 2000;

    private Editor editor;
    private Book hitchhikersGuide;
    private Book regularBook;

    @BeforeEach
    void setUp() {
        editor = new Editor(EDITOR_NAME, new Date());
        hitchhikersGuide = new Book(HITCHHIKERS_TITLE, HITCHHIKERS_AUTHOR, HITCHHIKERS_YEAR);
        regularBook = new Book(REGULAR_TITLE, REGULAR_AUTHOR, REGULAR_YEAR);
    }

    private void addBothBooks() {
        editor.addBook(hitchhikersGuide);
        editor.addBook(regularBook);
    }

    @Test
    void testHitchhikersGuideState() {
        editor.addBook(hitchhikersGuide);
        assertEquals(BookState.EXTRORDINARY, editor.getBooks().get(hitchhikersGuide));
    }

    @Test
    void testRegularBookStateNotNull() {
        editor.addBook(regularBook);
        assertNotNull(editor.getBooks().get(regularBook));
    }

    @Test
    void testMultipleBooks() {
        addBothBooks();

        assertEquals(2, editor.getBooks().size());
        assertEquals(BookState.EXTRORDINARY, editor.getBooks().get(hitchhikersGuide));
    }

    @Test
    void testBookGetters() {
        assertAll(
                () -> assertEquals(HITCHHIKERS_TITLE, hitchhikersGuide.getTitle()),
                () -> assertEquals(HITCHHIKERS_AUTHOR, hitchhikersGuide.getAuthor()),
                () -> assertEquals(HITCHHIKERS_YEAR, hitchhikersGuide.getYear()));
    }

}
