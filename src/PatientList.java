public class PatientList {
    private final Patient[] patientArray;
    private final int MAX_PATIENTS = 1000;
    private int nextAvailableIndex = 0;

    public PatientList() {
        patientArray = new Patient[MAX_PATIENTS];
    }

    // Adds a patient to the database.
    // Returns true if successful, false if the array is full.
    public boolean add(Patient pat) {
        return addOrdered(pat);
    }

    // Returns the Patient matching the given identity.
    // Returns null if not found.
    public Patient find(PatientIdentity id) {
        return binarySearch(id);
    }

    private boolean addOrdered(Patient pat) {
        if (nextAvailableIndex >= patientArray.length) {
            return false;
        }

        int currentIndex = nextAvailableIndex - 1;

        while (currentIndex >= 0 &&
                pat.getIdentity().isLessThan(
                        patientArray[currentIndex].getIdentity())) {

            patientArray[currentIndex + 1] =
                    patientArray[currentIndex];

            currentIndex--;
        }

        patientArray[currentIndex + 1] = pat;
        nextAvailableIndex++;

        return true;
    }

    private Patient binarySearch(PatientIdentity id) {
        int lower = 0;
        int upper = nextAvailableIndex - 1;

        while (upper >= lower) {
            int mid = (upper + lower) / 2;

            PatientIdentity midIdentity =
                    patientArray[mid].getIdentity();

            if (midIdentity.match(id)) {
                return patientArray[mid];
            }

            if (midIdentity.isLessThan(id)) {
                lower = mid + 1;
            } else {
                upper = mid - 1;
            }
        }

        return null;
    }

    public class Iterator {
        private int currentIndex;

        public Iterator() {
            currentIndex = 0;
        }

        public Patient next() {
            if (currentIndex >= nextAvailableIndex) {
                return null;
            }

            Patient patient = patientArray[currentIndex];
            currentIndex++;

            return patient;
        }
    }
}