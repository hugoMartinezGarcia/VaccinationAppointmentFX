package vaccinationAppointments.data;

/**
 * Class to store data about patients
 * @author Hugo Martínez
 * @version 1.0
 */
public class Patient extends vaccinationAppointments.data.Person {
    private int phoneNumber;
    private int nSIP;
    private int age;
    private String genre;
    private boolean diabetes;

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
