public class Name {
    private String firstName;
    private String lastName;

    public Name(String first, String last) {
        firstName = first;
        lastName = last;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String fullname() {
        return lastName + ", " + firstName;
    }

    public boolean match(Name other) {
        if (other == null) {
            return false;
        }

        return firstName.toLowerCase().equals(
                other.getFirstName().toLowerCase())
                &&
                lastName.toLowerCase().equals(
                        other.getLastName().toLowerCase());
    }

    public boolean isLessThan(Name other) {
        String thisLast = lastName.toLowerCase();
        String otherLast = other.getLastName().toLowerCase();

        int lastComparison = thisLast.compareTo(otherLast);

        if (lastComparison < 0) {
            return true;
        }

        if (lastComparison > 0) {
            return false;
        }

        String thisFirst = firstName.toLowerCase();
        String otherFirst = other.getFirstName().toLowerCase();

        return thisFirst.compareTo(otherFirst) < 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Name)) {
            return false;
        }

        return match((Name) obj);
    }

    @Override
    public String toString() {
        return fullname();
    }
}