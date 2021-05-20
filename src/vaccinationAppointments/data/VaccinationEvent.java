// Class to store the data necessary to generate a vaccination event
// by Hugo Martínez

package vaccinationAppointments.data;

import java.util.ArrayList;

public class VaccinationEvent {
    String date;
    Doctor doctor;
    ArrayList<vaccinationAppointments.data.Nurse> nurses;
    ArrayList<String> patients;

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
