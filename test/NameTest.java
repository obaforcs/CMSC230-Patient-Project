import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NameTest {

    @Test
    void testFullname() {
        Name name = new Name("John", "Smith");

        assertEquals("Smith, John", name.fullname());
    }

    @Test
    void testMatch() {
        Name name1 = new Name("John", "Smith");
        Name name2 = new Name("john", "smith");

        assertTrue(name1.match(name2));
    }

    @Test
    void testDoesNotMatch() {
        Name name1 = new Name("John", "Smith");
        Name name2 = new Name("Jane", "Smith");

        assertFalse(name1.match(name2));
    }

    @Test
    void testIsLessThanDifferentLastNames() {
        Name name1 = new Name("John", "Adams");
        Name name2 = new Name("John", "Smith");

        assertTrue(name1.isLessThan(name2));
        assertFalse(name2.isLessThan(name1));
    }

    @Test
    void testIsLessThanSameLastName() {
        Name name1 = new Name("Adam", "Smith");
        Name name2 = new Name("John", "Smith");

        assertTrue(name1.isLessThan(name2));
        assertFalse(name2.isLessThan(name1));
    }

    @Test
    void testCapitalizationDoesNotMatter() {
        Name name1 = new Name("JOHN", "SMITH");
        Name name2 = new Name("john", "smith");

        assertTrue(name1.match(name2));
    }
}