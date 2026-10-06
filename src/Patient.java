public class Patient {
    private PatientIdentity identity;

    public Patient(PatientIdentity id) {
        identity = id;
    }

    public PatientIdentity getIdentity() {
        return identity;
    }

    @Override
    public String toString() {
        return "identity: " + identity.toString();
    }
}