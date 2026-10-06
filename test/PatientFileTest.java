import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PatientFileTest {

    @Test
    void testMakePatient() {

        Patient patient =
                Patient.makePatient(
                        "Doe,Jane,01-01-1990");

        assertNotNull(patient);

        assertEquals(
                "Doe",
                patient.getIdentity()
                        .getName()
                        .getLastName());

        assertEquals(
                "Jane",
                patient.getIdentity()
                        .getName()
                        .getFirstName());
    }

    @Test
    void testInvalidPatientLine() {

        Patient patient =
                Patient.makePatient(
                        "This is not valid CSV");

        assertNull(patient);
    }

    @Test
    void testToCSV() {

        Patient patient =
                Patient.makePatient(
                        "Doe,Jane,01-01-1990");

        assertEquals(
                "Doe,Jane,01-01-1990",
                patient.toCSV());
    }

    @Test
    void testImportAndSort()
            throws IOException {

        String filename =
                "project3_import_test.csv";

        FileWriter writer =
                new FileWriter(filename);

        // Deliberately unsorted
        writer.write(
                "Smith,John,05-15-2000\n");

        writer.write(
                "Adams,Amy,02-10-1995\n");

        writer.write(
                "Doe,Jane,01-01-1990\n");

        writer.close();

        PatientList list =
                new PatientList();

        assertTrue(
                list.importFromFile(
                        filename));

        PatientList.Iterator iter =
                list.new Iterator();

        assertEquals(
                "Adams",
                iter.next()
                        .getIdentity()
                        .getName()
                        .getLastName());

        assertEquals(
                "Doe",
                iter.next()
                        .getIdentity()
                        .getName()
                        .getLastName());

        assertEquals(
                "Smith",
                iter.next()
                        .getIdentity()
                        .getName()
                        .getLastName());

        assertNull(iter.next());

        new File(filename).delete();
    }

    @Test
    void testInvalidLineIsSkipped()
            throws IOException {

        String filename =
                "project3_invalid_test.csv";

        FileWriter writer =
                new FileWriter(filename);

        writer.write(
                "Smith,John,05-15-2000\n");

        writer.write(
                "this line is garbage\n");

        writer.write(
                "Doe,Jane,01-01-1990\n");

        writer.close();

        PatientList list =
                new PatientList();

        assertTrue(
                list.importFromFile(
                        filename));

        PatientList.Iterator iter =
                list.new Iterator();

        assertNotNull(iter.next());
        assertNotNull(iter.next());

        // Only two valid records.
        assertNull(iter.next());

        new File(filename).delete();
    }

    @Test
    void testSaveToFile()
            throws IOException {

        String filename =
                "project3_save_test.csv";

        PatientList list =
                new PatientList();

        Patient jane =
                Patient.makePatient(
                        "Doe,Jane,01-01-1990");

        Patient john =
                Patient.makePatient(
                        "Smith,John,05-15-2000");

        list.add(john);
        list.add(jane);

        assertTrue(
                list.saveToFile(
                        filename));

        File file =
                new File(filename);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        file.delete();
    }

    @Test
    void testImportIntoExistingList()
            throws IOException {

        PatientList list =
                new PatientList();

        Patient existing =
                Patient.makePatient(
                        "Brown,Adam,04-01-1980");

        list.add(existing);

        String filename =
                "project3_existing_test.csv";

        FileWriter writer =
                new FileWriter(filename);

        writer.write(
                "Smith,John,05-15-2000\n");

        writer.write(
                "Adams,Amy,02-10-1995\n");

        writer.close();

        assertTrue(
                list.importFromFile(
                        filename));

        PatientList.Iterator iter =
                list.new Iterator();

        assertEquals(
                "Adams",
                iter.next()
                        .getIdentity()
                        .getName()
                        .getLastName());

        assertEquals(
                "Brown",
                iter.next()
                        .getIdentity()
                        .getName()
                        .getLastName());

        assertEquals(
                "Smith",
                iter.next()
                        .getIdentity()
                        .getName()
                        .getLastName());

        new File(filename).delete();
    }
}