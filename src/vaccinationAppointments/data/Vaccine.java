package vaccinationAppointments.data;

/**
 * Abstract class to store data about different vaccines
 * @author Hugo Martínez
 * @version 1.0
 */
public abstract class Vaccine {
    private String name;

    public Vaccine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
