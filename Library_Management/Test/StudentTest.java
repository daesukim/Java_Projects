import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student st = new Student("Paul","299");

    @Test
    void getName() {
        assertEquals(st.getName(), "Paul");
    }

    @Test
    void getRegistrationNumber() {
        assertEquals(st.getRegistrationNumber(), "299");
    }

    @Test
    void setName() {
        st.setName("Garry");
        assertEquals(st.getName(), "Garry");
    }

    @Test
    void setRegistrationNumber() {
        st.setRegistrationNumber("297");
        assertEquals(st.getRegistrationNumber(), "297");
    }
}