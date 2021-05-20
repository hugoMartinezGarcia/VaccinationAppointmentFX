package vaccinationAppointments.data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to prepare the data to be loaded or saved to files.
 * @author Hugo Martínez
 * @version 1.0
 */
public class FileUtils {
    /**
     * Static method to load a ArrayList of Doctors from a file called doctors.txt
     * @return a ArrayList of Doctors
     */
    public static ArrayList<Doctor> loadDoctors() {
        ArrayList<Doctor> result = new ArrayList<>();

        if (new File("doctors.txt").exists()) {
            BufferedReader inputDoctors = null;
            try {
                inputDoctors = new BufferedReader(new FileReader("doctors.txt"));

                String line;

                do {
                    line = inputDoctors.readLine();

                    if (line != null) {
                        String[] fragmentsLine = line.split(";");
                        String type = fragmentsLine[0];
                        String name = fragmentsLine[1];
                        String id = fragmentsLine[2];
                        boolean activated = Boolean.parseBoolean(fragmentsLine[3]);

                        Doctor d = new Doctor(type, name, id, activated);
                        result.add(d);
                    }
                } while (line != null);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputDoctors != null) {
                        inputDoctors.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * Static method to load a ArrayList of Nurses from a file called nurses.txt
     * @return a ArrayList of Nurses
     */
    public static ArrayList<Nurse> loadNurses() {
        ArrayList<Nurse> result = new ArrayList<>();

        if (new File("nurses.txt").exists()) {
            BufferedReader inputNurses = null;
            try {
                inputNurses = new BufferedReader(new FileReader("nurses.txt"));

                String line;

                do {
                    line = inputNurses.readLine();

                    if (line != null) {
                        String[] fragmentsLine = line.split(";");
                        String type = fragmentsLine[0];
                        String name = fragmentsLine[1];
                        String id = fragmentsLine[2];
                        boolean activated = Boolean.parseBoolean(fragmentsLine[3]);

                        Nurse n = new Nurse(type, name, id, activated);
                        result.add(n);


                    }
                } while (line != null);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputNurses != null) {
                        inputNurses.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * Static method to load a ArrayList of Patients from a file called patients.txt
     * @return a ArrayList of Patients
     */
    public static ArrayList<Patient> loadPatients() {
        ArrayList<Patient> result = new ArrayList<>();

        if (new File("patients.txt").exists()) {
            BufferedReader inputPatients = null;
            try {
                inputPatients = new BufferedReader(new FileReader("patients.txt"));

                String line;

                do {
                    line = inputPatients.readLine();

                    if (line != null) {
                        String[] fragmentsLine = line.split(";");
                        int age = Integer.parseInt(fragmentsLine[0]);
                        boolean diabetes = Boolean.parseBoolean(fragmentsLine[1]);
                        String genre = fragmentsLine[2];
                        String name = fragmentsLine[3];
                        int phone = Integer.parseInt(fragmentsLine[4]);
                        int nSIP = Integer.parseInt(fragmentsLine[5]);

                        Patient p = new Patient(age, diabetes, genre, name, phone, nSIP);
                        result.add(p);
                    }
                } while (line != null);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputPatients != null) {
                        inputPatients.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * Static method to save in a file a ArrayList of Patients patients waiting to be vaccinated
     * @param patients List of the patients
     */
    public static void savePatients(ArrayList<Patient> patients) {
        PrintWriter outputPatient = null;
        try {
            outputPatient = new PrintWriter("patients.txt");

            for (Patient p: patients) {
                outputPatient.println(p.preparePatientToFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputPatient != null)
                outputPatient.close();
        }
    }

    /**
     * Static method to load a HashMap with name of the vaccines and its stock
     * @return a HashMap with the name of the vaccines and its stock
     */
    public static HashMap<String, Integer> loadVaccines() {
        HashMap<String, Integer> result = new HashMap<>();

        if (new File("stock.txt").exists()) {
            BufferedReader inputStock = null;
            try {
                inputStock = new BufferedReader(new FileReader("stock.txt"));

                String line;

                do {
                    line = inputStock.readLine();

                    if (line != null) {
                        String[] fragmentsLine = line.split(";");
                        String vaccineName = fragmentsLine[0];
                        int stock = Integer.parseInt(fragmentsLine[1]);

                        result.put(vaccineName, stock);
                    }
                } while (line != null);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStock != null) {
                        inputStock.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * Static method to save in a file called stock.txt a HashMap with the name of the vaccines and its stock
     * @param vaccines HashMap with the name of the vaccines and its stock
     */
    public static void saveVaccines(HashMap<String, Integer> vaccines) {
        PrintWriter outputVaccine = null;
        try {
            outputVaccine = new PrintWriter("stock.txt");

            for (String key : vaccines.keySet()) {
                outputVaccine.println(key + ";" + vaccines.get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputVaccine != null)
                outputVaccine.close();
        }
    }

    /**
     * Static method to load a ArrayList of Vaccination Events from the file events.txt
     * @return ArrayList of Vaccination Events
     */
    public static ArrayList<String> loadEvents() {
        ArrayList<String> result = new ArrayList<>();

        if (new File("events.txt").exists()) {
            BufferedReader inputEvents = null;
            try {
                inputEvents = new BufferedReader(new FileReader("events.txt"));

                String line;

                do {
                    line = inputEvents.readLine();

                    if (line != null) {
                        result.add(line);
                        }
                } while (line != null);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputEvents != null) {
                        inputEvents.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * Static method to save a ArrayList of Vaccination Events
     * @param events ArrayList of Vaccination Events
     */
    public static void saveEvents(ArrayList<String> events) {
        PrintWriter outputEvent = null;
        try {
            outputEvent = new PrintWriter("events.txt");

            for (String e: events) {
                outputEvent.println(e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputEvent != null)
                outputEvent.close();
        }
    }
}