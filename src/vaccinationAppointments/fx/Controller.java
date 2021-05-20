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

/**
 * Class with the code to control the different graphic elements of the program. It contains the different methods as well.
 * @author Hugo Martínez
 * @version 1.0
 */
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
    private Nurse nurseSelected;

    /**
     * This method initialize the different elements of the program
     * @param url
     * @param resourceBundle
     */
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

    /**
     * This method is to check if the date is selected and if it available
     * @param
     */
    public void confirmDate(ActionEvent actionEvent) {
        if (date.getValue() == null || foundDate(date.getValue().toString())){
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText("Incorrect DATE");
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

    /**
     * Method to handle Select doctor button
     * @param actionEvent
     */
    public void selectDoctor(ActionEvent actionEvent) {
        doctorSelected = (Doctor) list.getSelectionModel().getSelectedItem();
        if (!doctorSelected.isActivated()) {
            alertMessage();
        } else {
            doctorButton.setVisible(false);
            nurseButton.setVisible(true);
            confirmNursesButton.setVisible(true);
            list.getItems().clear();
            lblTitle.setText("NURSES");
            for (Nurse n : nurses) {
                list.getItems().add(n);
            }
        }
    }

    /**
     * Method to handle Select nurse button
     * @param actionEvent
     */
    public void selectNurse(ActionEvent actionEvent) {
        nurseSelected = (Nurse) list.getSelectionModel().getSelectedItem();
        if (!nurseSelected.isActivated()) {
            alertMessage();
        } else {
            selectedNurses.add(nurseSelected);
        }
    }

    private void alertMessage() {
        Alert dialog = new Alert(Alert.AlertType.WARNING);
        dialog.setTitle("WARNING");
        dialog.setHeaderText("PROFESSIONAL DEACTIVATED");
        dialog.setContentText("Selected professional is deactivated");
        dialog.showAndWait();
    }

    /**
     * Method to handle Confirm nurses button
     * @param actionEvent
     */
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

    /**
     * Method to handle Confirm button
     * @param actionEvent
     */
    public void confirmVaccines(ActionEvent actionEvent) {
        if (comboPfizer.getValue() == null || comboJanssen.getValue() == null || comboModerna.getValue() == null
                || comboAstra.getValue() == null)
        {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText("Empty fields");
            dialog.setContentText("You must select a value for all vaccines");
            dialog.showAndWait();
        } else {
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

