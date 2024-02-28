import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    void availableBook() {
        Library library = new Library("inventory.txt");
        int[] result = library.availableBook();
        assertEquals(1141, result[0]);
        assertEquals(1665, result[1]);
    }

    @Test
    void getFictionBooks() {
        Library library = new Library("inventory.txt");
        ArrayList<FictionBook> nfb = library.getFictionBooks();
        assertEquals("A Time for Mercy", nfb.get(0).getName());
    }

    @Test
    void getInventory() {
        Library library = new Library("inventory.txt");
        int quantity = library.getInventory().get("17359");
        assertEquals(16, quantity);
    }

    @Test
    void getNonfictionBooks() {
        Library library = new Library("inventory.txt");
        ArrayList<NonfictionBook> nfb = library.getNonfictionBooks();
        assertEquals("The Alchemist", nfb.get(0).getName());
    }

    @Test
    void inventory() {
        Library library = new Library("inventory.txt");
        int quantity = library.getInventory().get("17359");
        assertEquals(16, quantity);
    }

    @Test
    void lend() {
        Library library = new Library("inventory.txt");
        HashMap map = library.getInventory();
        assertEquals(16 ,map.get("17359"));
        library.lend("17359");
        assertEquals(15 ,map.get("17359"));
    }

    @Test
    void putBack() {
        Library library = new Library("inventory.txt");
        HashMap map = library.getInventory();
        assertEquals(16 ,map.get("17359"));
        library.putBack("17359");
        assertEquals(17, map.get("17359"));
    }

    @Test
    void registerStudent() {
        Library library = new Library("inventory.txt");
        Student st1 = new Student("Daesu", "1000");
        library.registerStudent(st1);
        String firstStudentName = library.getStudents().get(0).getName();
        assertEquals(firstStudentName, st1.getName());

    }

    @Test
    void search() {
        Library library = new Library("inventory.txt");
        Book book = library.search("94320");
        assertEquals(book.getIsbn(), "94320");
    }

    @Test
    void sort() {
        Library library = new Library("inventory.txt");
        // testing sort by ISBN
        ArrayList<Book> books = library.sort(1);
        Book firstBookInSortedOrder = books.get(0);
        String firstISBN = firstBookInSortedOrder.getIsbn();
        assertEquals(firstISBN,"01234");

        ArrayList<Book> books2 = library.sort(2);
        Book firstBooks2 = books2.get(0);
        int firstQuantity = library.getInventory().get(firstBooks2.getIsbn());
        assertEquals(firstQuantity,5);
    }
}