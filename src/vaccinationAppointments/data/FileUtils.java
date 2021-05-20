// Class to prepare the data to be loaded or saved to files.
// by Hugo Martínez

package vaccinationAppointments.data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileUtils {
    public static ArrayList<vaccinationAppointments.data.Person> loadProfessionals() {
        ArrayList<vaccinationAppointments.data.Person> result = new ArrayList<>();

        if (new File("professionals.txt").exists()) {
            BufferedReader inputProfessionals = null;
            try {
                inputProfessionals = new BufferedReader(new FileReader("professionals.txt"));

                String line;

                do {
                    line = inputProfessionals.readLine();

                    if (line != null) {
                        String[] fragmentsLine = line.split(";");
                        String type = fragmentsLine[0];
                        String name = fragmentsLine[1];
                        String id = fragmentsLine[2];
                        boolean activated = Boolean.parseBoolean(fragmentsLine[3]);

                        if (type.equals("D")) {
                            Doctor d = new Doctor(type, name, id, activated);
                            result.add(d);
                        } else {
                            Nurse n = new Nurse(type, name, id, activated);
                            result.add(n);
                        }

                    }
                } while (line != null);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputProfessionals != null) {
                        inputProfessionals.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return result;
    }
    public static void saveProfessionals(ArrayList<vaccinationAppointments.data.Person> professionals) {
        PrintWriter outputProfessional = null;
        try {
            outputProfessional = new PrintWriter("professionals.txt");

            for (vaccinationAppointments.data.Person p: professionals) {
                if (p instanceof Doctor) {
                    outputProfessional.println(((Doctor) p).prepareDoctorToFile());
                } else if (p instanceof Nurse) {
                    outputProfessional.println(((Nurse) p).prepareNurseToFile());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputProfessional != null)
                outputProfessional.close();
        }
    }

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