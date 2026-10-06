import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Patient {
    private final PatientIdentity identity;

    public Patient(PatientIdentity id) {
        if (id == null ||
                id.getName() == null ||
                id.getDateOfBirth() == null) {
            throw new IllegalArgumentException(
                    "Patient must have a valid identity");
        }

        identity = id;
    }

    public PatientIdentity getIdentity() {
        return identity;
    }

    public String toCSV() {
        Name name = identity.getName();

        SimpleDateFormat formatter =
                new SimpleDateFormat("MM-dd-yyyy");

        return name.getLastName() + ","
                + name.getFirstName() + ","
                + formatter.format(
                identity.getDateOfBirth());
    }

    public static Patient makePatient(String line) {
        if (line == null) {
            return null;
        }

        try {
            Scanner scanner = new Scanner(line);
            scanner.useDelimiter(",");

            if (!scanner.hasNext()) {
                scanner.close();
                return null;
            }

            String lastName = scanner.next().trim();

            if (!scanner.hasNext()) {
                scanner.close();
                return null;
            }

            String firstName = scanner.next().trim();

            if (!scanner.hasNext()) {
                scanner.close();
                return null;
            }

            String dateString = scanner.next().trim();

            scanner.close();

            if (firstName.isEmpty() ||
                    lastName.isEmpty() ||
                    dateString.isEmpty()) {
                return null;
            }

            SimpleDateFormat formatter =
                    new SimpleDateFormat("MM-dd-yyyy");

            formatter.setLenient(false);

            Date birthDate =
                    formatter.parse(dateString);

            Name name =
                    new Name(firstName, lastName);

            PatientIdentity identity =
                    new PatientIdentity(name, birthDate);

            return new Patient(identity);

        } catch (ParseException |
                 IllegalArgumentException e) {

            return null;
        }
    }

    @Override
    public String toString() {
        return "identity: " + identity.toString();
    }
}