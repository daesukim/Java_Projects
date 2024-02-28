import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonfictionBookTest {

    NonfictionBook nfb = new NonfictionBook("Arther", "1234", "Boring Days", "172");
    NonfictionBook nfb2 = new NonfictionBook("Bryan", "1223", "Computing Days", "122");

    @Test
    void testToString() {
        assertNotNull(nfb.toString());
    }

    @Test
    void compareTo() {
        assertEquals(nfb.compareTo(nfb2), 1);
    }
}