// This class contains the necessary methods to handle the different menu options
// by Hugo Martínez

package vaccinationAppointments.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

import static vaccinationAppointments.data.FileUtils.*;

public class Management {
    ArrayList<Person> professionals;
    ArrayList<Patient> patients;
    HashMap<String, Integer> vaccines;
    ArrayList<String> events;
    Scanner sc;

    public Management() {
        professionals = loadProfessionals();
        patients = loadPatients();
        vaccines = loadVaccines();
        events = loadEvents();
        sc = new Scanner(System.in);
    }


    public void run() {
        boolean exit = false;

        do {
            showMenu();
            String option = sc.nextLine();

            switch (option) {
                case "1": addVaccinationEvent(); break;
                case "2": showEvents(); break;
                case "3": showProfessionals(); break;
                case "4": showVaccinesStock(); break;
                case "5": addProfessional(); break;
                case "6": deleteProfessional(); break;
                case "7": addStock(); break;
                case "8": addPatient(); break;
                case "0": exit = true; break;
                default: System.out.println("Incorrect input"); break;

            }

        } while (!exit);
    }

    private void deleteProfessional() {
        showProfessionals();
        int indexToDelete = Integer.parseInt(askData("Select index to delete")) - 1;

        professionals.remove(indexToDelete);
        saveProfessionals(professionals);
    }

    private void showEvents() {
        for (String l: events) {
            System.out.println(l);
        }
    }

    private void showMenu() {
        System.out.println("1. Add vaccination event");
        System.out.println("2. Show events");
        System.out.println("3. Show professionals");
        System.out.println("4. Show vaccines stock");
        System.out.println("5. Add Professional");
        System.out.println("6. Delete Professional");
        System.out.println("7. Add stock");
        System.out.println("8. Add patient");
        System.out.println("0. Exit");
    }

    private void showProfessionals() {
        int type = Integer.parseInt(askData("Select 1. to show doctors o 2 to show nurses"));

        if (type == 1) {
            for (int i = 0; i < professionals.size(); i++) {
                if (professionals.get(i) instanceof Doctor) {
                    System.out.println((i+1) +  " " + professionals.get(i).toString());
                }
            }
        } else {
            for (int i = 0; i < professionals.size(); i++) {
                if (professionals.get(i) instanceof Nurse) {
                    System.out.println((i+1) + " " + professionals.get(i).toString());
                }
            }
        }
    }

    private void showVaccinesStock() {
        for (String key : vaccines.keySet()) {
            System.out.println(key + ": " + vaccines.get(key));
        }
    }

    private void addProfessional() {
        System.out.println("ADD NEW PROFESSIONAL:");
        int type = Integer.parseInt(askData("Insert '1' for new Doctor or '2' for new Nurse"));
        String name = askData("Insert the name");
        String id = askData("Insert the id");
        boolean activated = Boolean.parseBoolean(askData("Insert 'true' for activated or 'false' for inactive"));

        if (type == 1) {
            Doctor d = new Doctor("D", id, name, activated);
            professionals.add(d);
        } else {
            Nurse n = new Nurse("N", id, name, activated);
            professionals.add(n);
        }
        saveProfessionals(professionals);
    }

    private void addPatient() {
        System.out.println("ADD NEW PATIENT:");
        int age = Integer.parseInt(askData("Insert age"));
        boolean diabetes = Boolean.parseBoolean(askData("Insert 'true' if patient has diabetes or 'false' if not"));
        String genre = askData("Insert the genre ('W' for woman or 'M' for man)");
        String name = askData("Insert the name");
        int phone = Integer.parseInt(askData("Insert the phone number"));
        int nSIP = Integer.parseInt(askData("Insert the SIP number"));

        Patient p = new Patient(age, diabetes, genre, name, phone, nSIP);
        patients.add(p);

        savePatients(patients);
    }

    private void addStock() {
        System.out.println("VACCINES:");
        for (String key: vaccines.keySet()) {
            System.out.println(key);
        }
        String name = askData("Insert the name of vaccine to add stock");
        int newVaccines = Integer.parseInt(askData("Insert the number of new vaccines"));

        if (vaccines.containsKey(name)) {
            vaccines.put(name, vaccines.get(name) + newVaccines);
        } else {
            System.out.println("Incorrect name of vaccine!");
        }

        saveVaccines(vaccines);
    }

    private void addVaccinationEvent() {
        // Date
        boolean found;
        String date;
        do {
            found = false;
            date = askData("Insert the day of the event (YYYY-MM-DD)");
            for(String line: events) {
                if (line.contains(date)) {
                    System.out.println("Date not available");
                    found = true;
                }
            }
        } while (found);

        // Doctor
        Doctor doctorSelected = selectDoctor();

        // Nurses
        ArrayList<Nurse> nursesSelected = selectNurses();

        // Vaccines
        HashMap<String, Integer> selectedVaccines = selectVaccines();

        // Patients
        // Sort the patients by age
        patients.sort(new Comparator<>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return Integer.compare(p2.getAge(), p1.getAge());
            }
        });

        ArrayList<String> patientsSelected = selectPatients(selectedVaccines);

        //Finally the event is saved.
        VaccinationEvent vE = new VaccinationEvent(date, doctorSelected, nursesSelected, patientsSelected);
        events.add(vE.toString());
        saveEvents(events);
    }

    private Doctor selectDoctor() {
        int doctorIndex;

        for (int i = 0; i < professionals.size(); i++) {
            if (professionals.get(i) instanceof Doctor) {
                System.out.println((i+1) + ". " + professionals.get(i).getName());
            }
        }

        do {
            doctorIndex = Integer.parseInt(askData("Select a doctor from the list")) - 1;

            if (!((Doctor) professionals.get(doctorIndex)).isActivated())
                System.out.println("Doctor is not activated!");
        } while (!((Doctor) professionals.get(doctorIndex)).isActivated());

        return (Doctor) professionals.get(doctorIndex);
    }

    private ArrayList<Nurse> selectNurses() {
        ArrayList<Nurse> selectedNurses = new ArrayList<>();
        String nurseIndexText;

        for (int i = 0; i < professionals.size(); i++) {
            if (professionals.get(i) instanceof Nurse) {
                System.out.println((i+1) + ". " + professionals.get(i).getName());
            }
        }
        System.out.println("Select a nurse from the list (Intro for exit):");

        do {
            nurseIndexText = sc.nextLine();
            if (!nurseIndexText.equals("")) {
                int nurseIndex = Integer.parseInt(nurseIndexText) - 1;
                if (((Nurse) professionals.get(nurseIndex)).isActivated()) {
                    selectedNurses.add((Nurse) professionals.get(nurseIndex));
                } else
                    System.out.println("Nurse is not activated!");
            }
        } while (!nurseIndexText.equals(""));

        return selectedNurses;
    }

    private HashMap<String, Integer> selectVaccines() {
        int auxNumber;
        HashMap<String, Integer> selectedVaccines = new HashMap<>();
        System.out.println("Insert the number of vaccines of each type to be administered:");
        for (String key: vaccines.keySet()) {
            do {
                System.out.print(key + ": Available: " + vaccines.get(key) + ". Required: ");
                auxNumber = sc.nextInt();
                sc.nextLine();

                if (vaccines.get(key) - auxNumber < 0) {
                    System.out.println("The stock of " + key + " is not enough!");
                }
            } while (vaccines.get(key) - auxNumber < 0);

            vaccines.put(key, vaccines.get(key) - auxNumber);

            selectedVaccines.put(key, auxNumber);
        }

        saveVaccines(vaccines);
        return selectedVaccines;
    }

    private ArrayList<String> selectPatients(HashMap<String, Integer> selectedVaccines) {
        ArrayList<String> selectedPatients = new ArrayList<>();
        int index;

        for (String key: selectedVaccines.keySet()) {
            if (key.equals("Pfizer") || key.equals("Moderna")) {
                index = 0;
                while (selectedVaccines.get(key) > 0 && index < patients.size()) {
                    if (patients.get(index).getAge() >= 60 || patients.get(index).isDiabetes()) {
                        selectedPatients.add(patients.get(index) + " - ARN Vaccine");
                        patients.remove(index);
                        selectedVaccines.put(key, selectedVaccines.get(key) - 1);
                        index--;
                    }
                    index++;
                }

                index = 0;
                while (selectedVaccines.get(key) > 0 && index < patients.size()) {
                    selectedPatients.add(patients.get(index) + " - ARN Vaccine");
                    patients.remove(index);
                    selectedVaccines.put(key, selectedVaccines.get(key) - 1);
                }
            } else {
                index = 0;
                while (selectedVaccines.get(key) > 0 && index < patients.size()) {
                    selectedPatients.add(patients.get(index) + " - NO ARN");
                    patients.remove(index);
                    selectedVaccines.put(key, selectedVaccines.get(key) - 1);
                }
            }
        }

        savePatients(patients);
        return selectedPatients;
    }

    private String askData (String text) {
        System.out.println(text + ": ");
        return sc.nextLine();
    }
}
