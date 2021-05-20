package vaccinationAppointments.fx;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import vaccinationAppointments.data.*;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private Button nurseButton;
    @FXML
    private Button doctorButton;
    @FXML
    private Label listTitle;
    @FXML
    private TextField txtDate;
    @FXML
    private Button buttonNext;
    @FXML
    private ListView list;

    ArrayList<Doctor> doctors;
    ArrayList<Nurse> nurses;
    ArrayList<Patient> patients;
    HashMap<String, Integer> vaccines;
    ArrayList<String> events;
    ArrayList<Nurse> selectedNurses;
    ArrayList<Doctor> selectedDoctors;
    Scanner sc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        events = loadEvents();
        doctors = loadDoctors();
        nurses = loadNurses();
        vaccines = loadVaccines();
        doctorButton.setVisible(false);
        nurseButton.setVisible(false);
    }


    public void nextStep(ActionEvent actionEvent) {
        buttonNext.setVisible(false);
        if (txtDate.getText().isEmpty() || foundDate(txtDate.getText())) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText("Incorrect DATA");
            dialog.setContentText("Date is incorrect or it is not available");
            dialog.showAndWait();

        }
        else {
            listTitle.setText("DOCTORS");
            for (Doctor d : doctors) {
                list.getItems().add(d);
            }

            Doctor doctorSelected = (Doctor) list.getSelectionModel().getSelectedItem();
            if (doctorSelected != null)
                selectedDoctors.add(doctorSelected);
            doctorButton.setVisible(true);
        }
    }
    public void confirmDoctor(ActionEvent actionEvent) {

        doctorButton.setVisible(false);
        nurseButton.setVisible(true);
        list.getItems().clear();
        listTitle.setText("NURSES");
        for (Nurse n : nurses) {
            list.getItems().add(n);
        }
    }

    public void confirmNurse(ActionEvent actionEvent) {
        Nurse nurseSelected = (Nurse) list.getSelectionModel().getSelectedItem();

        nurseButton.setVisible(false);
        list.getItems().clear();



        for (String key: vaccines.keySet()) {
            list.getItems().add(key + " - " + vaccines.get(key));
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
}

