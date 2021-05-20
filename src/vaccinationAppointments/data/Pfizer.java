package vaccinationAppointments.data;

public class Pfizer extends Vaccine
                    implements Freezable {

    boolean frozen;

    public Pfizer(String name, boolean freezable) {
        super(name);
        this.frozen = freezable;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    @Override
    public void freezable() {this.frozen = true; }

    @Override
    public void defrostable() {this.frozen = false; }
}
