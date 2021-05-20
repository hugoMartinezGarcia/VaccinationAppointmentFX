package vaccinationAppointments.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import vaccinationAppointments.data.*;

import java.io.*;
import java.net.URL;
import java.util.*;

import static vaccinationAppointments.data.FileUtils.*;

public class Controller implements Initializable {
    @FXML
    private Button confirmNursesButton;
    @FXML
    private Label lblPfizer;
    @FXML
    private Label lblJanssen;
    @FXML
    private Label lblModerna;
    @FXML
    private Label lblAstra;
    @FXML
    private ComboBox<Integer> comboJanssen;
    @FXML
    private ComboBox<Integer> comboModerna;
    @FXML
    private ComboBox<Integer> comboAstra;
    @FXML
    private Button vaccinesButton;
    @FXML
    private ComboBox<Integer> comboPfizer;
    @FXML
    private DatePicker date;
    @FXML
    private Button nurseButton;
    @FXML
    private Button doctorButton;
    @FXML
    private Label lblTitle;
    @FXML
    private Button buttonNext;
    @FXML
    private ListView list;

    private ArrayList<Doctor> doctors;
    private ArrayList<Nurse> nurses;
    private ArrayList<Patient> patients;
    private HashMap<String, Integer> vaccines;
    private HashMap<String, Integer> selectedVaccines;
    private ArrayList<String> events;
    private ArrayList<Nurse> selectedNurses;
    private Doctor doctorSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        events = loadEvents();
        doctors = loadDoctors();
        nurses = loadNurses();
        vaccines = loadVaccines();
        patients = loadPatients();
        selectedNurses = new ArrayList<>();
        selectedVaccines = new HashMap<>();
        lblTitle.setVisible(false);
        doctorButton.setVisible(false);
        nurseButton.setVisible(false);
        confirmNursesButton.setVisible(false);
        comboPfizer.setVisible(false);
        comboJanssen.setVisible(false);
        comboModerna.setVisible(false);
        comboAstra.setVisible(false);
        vaccinesButton.setVisible(false);
        lblPfizer.setVisible(false);
        lblJanssen.setVisible(false);
        lblModerna.setVisible(false);
        lblAstra.setVisible(false);
    }


    public void confirmDate(ActionEvent actionEvent) {
        if (date.getValue() == null || foundDate(date.toString())){
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText("Incorrect DATA");
            dialog.setContentText("Date is incorrect or it is not available");
            dialog.showAndWait();

        }
        else {
            lblTitle.setText("DOCTORS");
            for (Doctor d : doctors) {
                list.getItems().add(d);
            }

            lblTitle.setVisible(true);
            buttonNext.setVisible(false);
            doctorButton.setVisible(true);
        }
    }
    public void selectDoctor(ActionEvent actionEvent) {
        doctorSelected = (Doctor) list.getSelectionModel().getSelectedItem();
        doctorButton.setVisible(false);
        nurseButton.setVisible(true);
        confirmNursesButton.setVisible(true);
        list.getItems().clear();
        lblTitle.setText("NURSES");
        for (Nurse n : nurses) {
            list.getItems().add(n);
        }
    }

    public void selectNurse(ActionEvent actionEvent) {
        Nurse nurseSelected = (Nurse) list.getSelectionModel().getSelectedItem();
        selectedNurses.add(nurseSelected);
    }

    public void confirmNurses(ActionEvent actionEvent) {

        nurseButton.setVisible(false);
        confirmNursesButton.setVisible(false);
        list.getItems().clear();
        lblTitle.setVisible(false);

        addToCombo(comboPfizer, vaccines.get("Pfizer"));
        addToCombo(comboJanssen, vaccines.get("Janssen"));
        addToCombo(comboModerna, vaccines.get("Moderna"));
        addToCombo(comboAstra, vaccines.get("AstraZeneca"));

        comboPfizer.setVisible(true);
        comboJanssen.setVisible(true);
        comboModerna.setVisible(true);
        comboAstra.setVisible(true);
        vaccinesButton.setVisible(true);
        lblPfizer.setVisible(true);
        lblJanssen.setVisible(true);
        lblModerna.setVisible(true);
        lblAstra.setVisible(true);
    }

    public void confirmVaccines(ActionEvent actionEvent) {
        selectedVaccines.put("Pfizer", comboPfizer.getValue());
        selectedVaccines.put("Janssen", comboJanssen.getValue());
        selectedVaccines.put("Moderna", comboModerna.getValue());
        selectedVaccines.put("AstraZeneca", comboAstra.getValue());

        for (String key: vaccines.keySet()) {
            vaccines.put(key, vaccines.get(key) - selectedVaccines.get(key));
        }

        addVaccinationEvent();
        saveVaccines(vaccines);

        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("INFORMATION");
        dialog.setHeaderText("VACCINATION EVENT CREATED");
        dialog.setContentText("The vaccination event has been created successfully and has been added to the events.txt file");
        dialog.showAndWait();
    }

    private void addToCombo(ComboBox<Integer> combo, int totalNumber) {
        for (int i = 0; i <= totalNumber; i++) {
            combo.getItems().add(i);
        }
    }
    private boolean foundDate(String date) {
        boolean result = false;
        for (String e: events) {
            if (e.contains(date)) {
                result = true;
            }
        }
        return result;
    }

    private ArrayList<String> loadEvents() {
        ArrayList<String> result = new ArrayList<>();

        if (new File("events.txt").exists()) {
            try (BufferedReader inputEvents = new BufferedReader(new FileReader("events.txt"))) {

                String line;

                do {
                    line = inputEvents.readLine();

                    if (line != null) {
                        result.add(line);
                    }
                } while (line != null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private void saveEvents(ArrayList<String> events) {
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

    private ArrayList<Doctor> loadDoctors() {
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
    private ArrayList<Nurse> loadNurses() {
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

    private HashMap<String, Integer> loadVaccines() {
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

    private void saveVaccines(HashMap<String, Integer> vaccines) {
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

    private ArrayList<Patient> loadPatients() {
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

    private void savePatients(ArrayList<Patient> patients) {
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

    private void addVaccinationEvent() {
        patients.sort(new Comparator<>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return Integer.compare(p2.getAge(), p1.getAge());
            }
        });

        ArrayList<String> patientsSelected = selectPatients(selectedVaccines);

        //Finally the event is saved.
        VaccinationEvent vE = new VaccinationEvent(date.getValue().toString(), doctorSelected, selectedNurses, patientsSelected);
        events.add(vE.toString());
        saveEvents(events);
    }
}

