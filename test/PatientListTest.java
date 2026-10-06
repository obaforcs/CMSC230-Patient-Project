import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PatientListTest {

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

    private Patient makePatient(
            String first,
            String last,
            String birthDate) {

        Name name = new Name(first, last);

        PatientIdentity identity =
                new PatientIdentity(
                        name,
                        makeDate(birthDate));

        return new Patient(identity);
    }

    @Test
    void testAddAndFind() {
        PatientList list = new PatientList();

        Patient jane =
                makePatient(
                        "Jane",
                        "Doe",
                        "01/01/1990");

        Patient john =
                makePatient(
                        "John",
                        "Smith",
                        "05/15/2000");

        assertTrue(list.add(jane));
        assertTrue(list.add(john));

        Patient result =
                list.find(john.getIdentity());

        assertSame(john, result);
    }

    @Test
    void testFindPatientNotPresent() {
        PatientList list = new PatientList();

        Patient jane =
                makePatient(
                        "Jane",
                        "Doe",
                        "01/01/1990");

        list.add(jane);

        PatientIdentity missingIdentity =
                new PatientIdentity(
                        new Name("Bob", "Jones"),
                        makeDate("03/03/1985"));

        assertNull(list.find(missingIdentity));
    }

    @Test
    void testPatientsStoredInSortedOrder() {
        PatientList list = new PatientList();

        // Deliberately add them out of order
        Patient smith =
                makePatient(
                        "John",
                        "Smith",
                        "05/15/2000");

        Patient adams =
                makePatient(
                        "Amy",
                        "Adams",
                        "02/10/1995");

        Patient doe =
                makePatient(
                        "Jane",
                        "Doe",
                        "01/01/1990");

        list.add(smith);
        list.add(adams);
        list.add(doe);

        PatientList.Iterator iter =
                list.new Iterator();

        Patient first = iter.next();
        Patient second = iter.next();
        Patient third = iter.next();

        assertEquals(
                "Adams",
                first.getIdentity()
                        .getName()
                        .getLastName());

        assertEquals(
                "Doe",
                second.getIdentity()
                        .getName()
                        .getLastName());

        assertEquals(
                "Smith",
                third.getIdentity()
                        .getName()
                        .getLastName());

        assertNull(iter.next());
    }

    @Test
    void testIterator() {
        PatientList list = new PatientList();

        Patient p1 =
                makePatient(
                        "Adam",
                        "Brown",
                        "01/01/1990");

        Patient p2 =
                makePatient(
                        "Jane",
                        "Doe",
                        "01/01/1991");

        list.add(p1);
        list.add(p2);

        PatientList.Iterator iter =
                list.new Iterator();

        assertSame(p1, iter.next());
        assertSame(p2, iter.next());

        assertNull(iter.next());
        assertNull(iter.next());
    }

    @Test
    void testSameLastNameSortsByFirstName() {
        PatientList list = new PatientList();

        Patient john =
                makePatient(
                        "John",
                        "Smith",
                        "01/01/2000");

        Patient adam =
                makePatient(
                        "Adam",
                        "Smith",
                        "01/01/2000");

        list.add(john);
        list.add(adam);

        PatientList.Iterator iter =
                list.new Iterator();

        assertEquals(
                "Adam",
                iter.next()
                        .getIdentity()
                        .getName()
                        .getFirstName());

        assertEquals(
                "John",
                iter.next()
                        .getIdentity()
                        .getName()
                        .getFirstName());
    }

    @Test
    void testSameNameSortsByBirthday() {
        PatientList list = new PatientList();

        Patient younger =
                makePatient(
                        "John",
                        "Smith",
                        "01/01/2000");

        Patient older =
                makePatient(
                        "John",
                        "Smith",
                        "01/01/1990");

        list.add(younger);
        list.add(older);

        PatientList.Iterator iter =
                list.new Iterator();

        assertSame(older, iter.next());
        assertSame(younger, iter.next());
    }
}