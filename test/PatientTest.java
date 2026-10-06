import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

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
    void testCreatePatient() {
        Name name = new Name("Jane", "Doe");

        Date birthday = makeDate("08/25/2003");

        PatientIdentity identity =
                new PatientIdentity(name, birthday);

        Patient patient =
                new Patient(identity);

        assertSame(identity, patient.getIdentity());
    }

    @Test
    void testPatientIdentityInformation() {
        Name name = new Name("Jane", "Doe");

        Date birthday = makeDate("08/25/2003");

        PatientIdentity identity =
                new PatientIdentity(name, birthday);

        Patient patient =
                new Patient(identity);

        assertTrue(
                patient.getIdentity()
                        .getName()
                        .match(new Name("Jane", "Doe"))
        );

        assertEquals(
                birthday,
                patient.getIdentity().getDateOfBirth()
        );
    }

    @Test
    void testToString() {
        Patient patient =
                new Patient(
                        new PatientIdentity(
                                new Name("Jane", "Doe"),
                                makeDate("08/25/2003")
                        )
                );

        assertNotNull(patient.toString());
        assertTrue(patient.toString().contains("Doe, Jane"));
    }
}