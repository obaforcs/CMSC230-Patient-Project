import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PatientList {
    private final Patient[] patientArray;
    private final int MAX_PATIENTS = 1000;
    private int nextAvailableIndex = 0;

    public PatientList() {
        patientArray =
                new Patient[MAX_PATIENTS];
    }

    public boolean add(Patient pat) {
        return addOrdered(pat);
    }

    public Patient find(PatientIdentity id) {
        return binarySearch(id);
    }

    private boolean addOrdered(Patient pat) {
        if (nextAvailableIndex >=
                patientArray.length) {
            return false;
        }

        int currentIndex =
                nextAvailableIndex - 1;

        while (currentIndex >= 0 &&
                pat.getIdentity().isLessThan(
                        patientArray[currentIndex]
                                .getIdentity())) {

            patientArray[currentIndex + 1] =
                    patientArray[currentIndex];

            currentIndex--;
        }

        patientArray[currentIndex + 1] = pat;
        nextAvailableIndex++;

        return true;
    }

    private Patient binarySearch(
            PatientIdentity id) {

        int lower = 0;
        int upper =
                nextAvailableIndex - 1;

        while (upper >= lower) {
            int mid =
                    (upper + lower) / 2;

            PatientIdentity midIdentity =
                    patientArray[mid]
                            .getIdentity();

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

    /*
     * PROJECT 3
     */

    public boolean saveToFile(
            String filename) {

        File file = new File(filename);

        try {
            FileWriter writer =
                    new FileWriter(file);

            Iterator iter =
                    this.new Iterator();

            Patient patient;

            while ((patient =
                    iter.next()) != null) {

                writer.write(
                        patient.toCSV()
                                + "\n");
            }

            writer.close();

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public boolean importFromFile(
            String filename) {

        File file = new File(filename);

        try {
            Scanner scanner =
                    new Scanner(file);

            while (scanner.hasNextLine()) {

                String line =
                        scanner.nextLine();

                Patient patient =
                        Patient.makePatient(line);

                // Invalid CSV line:
                // skip it and continue.
                if (patient == null) {
                    continue;
                }

                // Add UNSORTED at the end.
                if (!addUnsorted(patient)) {
                    scanner.close();
                    return false;
                }
            }

            scanner.close();

            // Sort only after all patients
            // have been loaded.
            mergeSort(
                    0,
                    nextAvailableIndex - 1);

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    private boolean addUnsorted(
            Patient patient) {

        if (nextAvailableIndex >=
                patientArray.length) {
            return false;
        }

        patientArray[
                nextAvailableIndex] =
                patient;

        nextAvailableIndex++;

        return true;
    }

    private void mergeSort(
            int left,
            int right) {

        if (left >= right) {
            return;
        }

        int middle =
                (left + right) / 2;

        mergeSort(left, middle);

        mergeSort(
                middle + 1,
                right);

        merge(
                left,
                middle,
                right);
    }

    private void merge(
            int left,
            int middle,
            int right) {

        Patient[] temp =
                new Patient[
                        right - left + 1];

        int leftIndex = left;
        int rightIndex =
                middle + 1;
        int tempIndex = 0;

        while (leftIndex <= middle &&
                rightIndex <= right) {

            Patient leftPatient =
                    patientArray[leftIndex];

            Patient rightPatient =
                    patientArray[rightIndex];

            if (rightPatient
                    .getIdentity()
                    .isLessThan(
                            leftPatient
                                    .getIdentity())) {

                temp[tempIndex] =
                        rightPatient;

                rightIndex++;

            } else {

                temp[tempIndex] =
                        leftPatient;

                leftIndex++;
            }

            tempIndex++;
        }

        while (leftIndex <= middle) {

            temp[tempIndex] =
                    patientArray[leftIndex];

            leftIndex++;
            tempIndex++;
        }

        while (rightIndex <= right) {

            temp[tempIndex] =
                    patientArray[rightIndex];

            rightIndex++;
            tempIndex++;
        }

        for (int i = 0;
             i < temp.length;
             i++) {

            patientArray[left + i] =
                    temp[i];
        }
    }

    public class Iterator {
        private int currentIndex;

        public Iterator() {
            currentIndex = 0;
        }

        public Patient next() {
            if (currentIndex >=
                    nextAvailableIndex) {
                return null;
            }

            Patient patient =
                    patientArray[
                            currentIndex];

            currentIndex++;

            return patient;
        }
    }
}