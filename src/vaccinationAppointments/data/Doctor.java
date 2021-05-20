// Class to store data about doctors
// by Hugo Martínez

package vaccinationAppointments.data;

public class Doctor extends vaccinationAppointments.data.Person
                    implements vaccinationAppointments.data.Activable {
    String id;
    String type;
    boolean activated;

    public Doctor(String type, String name, String id, boolean activated) {
        super(name);
        this.id = id;
        this.type = type;
        this.activated = activated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void activable() {this.activated = true; }

    @Override
    public void deactivable() {this.activated = false; }

    @Override
    public String toString() {
        return super.toString() + " - " + id + " - " + (activated ? "activated" : "deactivated");
    }

    public String prepareDoctorToFile() {
        return type + ";" + name + ";" + id + ";" + activated;
    }
}
