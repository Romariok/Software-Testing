package itmo.st;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import itmo.st.galaxy.Book;
import itmo.st.galaxy.BookState;
import itmo.st.galaxy.Editor;

public class GalaxyTest {
    private Editor editor;
    private Book hitchhikersGuide;
    private Book regularBook;

    @BeforeEach
    void setUp() {
        editor = new Editor("Иван Петров", new Date());
        hitchhikersGuide = new Book("Путеводитель по Галактике для автостопщиков", "Дуглас Адамс", 1979);
        regularBook = new Book("Обычная книга", "Обычный автор", 2000);
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
        editor.addBook(hitchhikersGuide);
        editor.addBook(regularBook);
        
        assertEquals(2, editor.getBooks().size());
        assertEquals(BookState.EXTRORDINARY, editor.getBooks().get(hitchhikersGuide));
    }

    @Test
    void testBookGetters() {
        assertEquals("Путеводитель по Галактике для автостопщиков", hitchhikersGuide.getTitle());
        assertEquals("Дуглас Адамс", hitchhikersGuide.getAuthor());
        assertEquals(1979, hitchhikersGuide.getYear());
    }


    @Test
    void testEditorToString() {
        editor.addBook(hitchhikersGuide);
        String result = editor.toString();
        
        assertTrue(result.contains(editor.getName()));
        assertTrue(result.contains(hitchhikersGuide.getTitle()));
        assertTrue(result.contains(BookState.EXTRORDINARY.getString()));
    }
}

