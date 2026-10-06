import java.util.Date;

public class PatientIdentity {
    private Name name;
    private Date dateOfBirth;

    public PatientIdentity(Name nm, Date dob) {
        name = nm;
        dateOfBirth = dob;
    }

    public Name getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean match(PatientIdentity other) {
        if (other == null) {
            return false;
        }

        return name.match(other.getName())
                && dateOfBirth.equals(other.getDateOfBirth());
    }

    public boolean isLessThan(PatientIdentity other) {
        if (name.isLessThan(other.getName())) {
            return true;
        }

        if (name.match(other.getName())) {
            return dateOfBirth.compareTo(
                    other.getDateOfBirth()) < 0;
        }

        return false;
    }

    @Override
    public String toString() {
        return "name: " + name.toString()
                + " dob: " + dateOfBirth.toString();
    }
}