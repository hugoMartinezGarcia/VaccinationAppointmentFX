// Class to store data about patients
// by Hugo Martínez

package vaccinationAppointments.data;

public class Patient extends vaccinationAppointments.data.Person {
    int phoneNumber;
    int nSIP;
    int age;
    String genre;
    boolean diabetes;

    public Patient(int age, boolean diabetes, String genre, String name, int phoneNumber, int nSIP) {
        super(name);
        this.phoneNumber = phoneNumber;
        this.nSIP = nSIP;
        this.age = age;
        this.genre = genre;
        this.diabetes = diabetes;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getNSIP() {
        return nSIP;
    }

    public int getAge() {
        return age;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public String preparePatientToFile() {
        return age + ";" + diabetes + ";" + genre + ";" + name + ";" + phoneNumber + ";" + nSIP;
    }
}
