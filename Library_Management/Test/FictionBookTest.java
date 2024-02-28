import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class FictionBookTest {
    ArrayList<Book> books = new ArrayList<>();
    Book b1 = new FictionBook("Author", "1232", "Name of the book", "152");
    Book b2 = new NonfictionBook("Bay", "2555", "What is Java", "520");
    Book b3 = new FictionBook("Paul", "1", "Please work", "200");

    @Test
    void compareTo() {
        assertEquals(b1.compareTo(b2), -1);
        assertEquals(b1.getAuthor(), "Author");
        assertEquals(b1.getIsbn(), "1232");
        assertEquals(b1.getName(), "Name of the book");
        assertEquals(b1.getPages(), "152");
    }

    @Test
    void testToString() {
        assertNotNull(b1.toString());
    }
}