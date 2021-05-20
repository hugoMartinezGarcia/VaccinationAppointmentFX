// Abstract class to store data about different vaccines
// by Hugo Martínez

package vaccinationAppointments.data;

public abstract class Vaccine {
    String name;

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
