package vaccinationAppointments.data;

import java.util.ArrayList;

/**
 * Class to store the data necessary to generate a vaccination event
 * @author Hugo Martínez
 * @version 1.0
 */
public class VaccinationEvent {
    private String date;
    private Doctor doctor;
    private ArrayList<vaccinationAppointments.data.Nurse> nurses;
    private ArrayList<String> patients;

    /**
     * Constructor to establish a new Vaccination Event
     * @param date date of the event
     * @param doctor Doctor object
     * @param nurses arrayList of Nurses
     * @param patients arrayList of Patients
     */
    public VaccinationEvent(String date, Doctor doctor, ArrayList<vaccinationAppointments.data.Nurse> nurses, ArrayList<String> patients) {
        this.date = date;
        this.doctor = doctor;
        this.nurses = nurses;
        this.patients = patients;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public ArrayList<vaccinationAppointments.data.Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(ArrayList<vaccinationAppointments.data.Nurse> nurses) {
        this.nurses = nurses;
    }

    public ArrayList<String> getPatients() {
        return patients;
    }

    @Override
    public String toString() {
        return "Date: " + date + "\nDoctor: " + doctor + "\nNurses: " + nurses + "\nPatients: " + patients + "\n";
    }
}
