import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PatientIdentityTest {

    private Date makeDate(String date) {
        try {
            SimpleDateFormat formatter =
                    new SimpleDateFormat("MM/dd/yyyy");

            formatter.setLenient(false);

            return formatter.parse(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testMatch() {
        Name name1 = new Name("John", "Smith");
        Name name2 = new Name("john", "smith");

        PatientIdentity identity1 =
                new PatientIdentity(
                        name1,
                        makeDate("05/15/2000"));

        PatientIdentity identity2 =
                new PatientIdentity(
                        name2,
                        makeDate("05/15/2000"));

        assertTrue(identity1.match(identity2));
    }

    @Test
    void testDifferentBirthdaysDoNotMatch() {
        Name name1 = new Name("John", "Smith");
        Name name2 = new Name("John", "Smith");

        PatientIdentity identity1 =
                new PatientIdentity(
                        name1,
                        makeDate("05/15/2000"));

        PatientIdentity identity2 =
                new PatientIdentity(
                        name2,
                        makeDate("05/16/2000"));

        assertFalse(identity1.match(identity2));
    }

    @Test
    void testLessThanDifferentNames() {
        PatientIdentity identity1 =
                new PatientIdentity(
                        new Name("John", "Adams"),
                        makeDate("05/15/2000"));

        PatientIdentity identity2 =
                new PatientIdentity(
                        new Name("John", "Smith"),
                        makeDate("05/15/2000"));

        assertTrue(identity1.isLessThan(identity2));
    }

    @Test
    void testLessThanSameNameDifferentBirthdays() {
        PatientIdentity older =
                new PatientIdentity(
                        new Name("John", "Smith"),
                        makeDate("05/15/1999"));

        PatientIdentity younger =
                new PatientIdentity(
                        new Name("John", "Smith"),
                        makeDate("05/15/2000"));

        assertTrue(older.isLessThan(younger));
        assertFalse(younger.isLessThan(older));
    }

    @Test
    void testToString() {
        PatientIdentity identity =
                new PatientIdentity(
                        new Name("John", "Smith"),
                        makeDate("05/15/2000"));

        assertNotNull(identity.toString());
        assertTrue(identity.toString().contains("Smith, John"));
    }
}