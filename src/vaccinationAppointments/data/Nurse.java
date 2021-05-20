package vaccinationAppointments.data;

/**
 * Class to store data about nurses.
 * @author Hugo Martínez
 * @version 1.0
 */
public class Nurse extends vaccinationAppointments.data.Person
                    implements vaccinationAppointments.data.Activable {
    private String id;
    private String type;
    private boolean activated;

    /**
     * Constructor to establish a new Nurse
     * @param type the kind of professional
     * @param name name of the Nurse
     * @param id identification
     * @param activated boolean to know if it is activated
     */
    public Nurse(String type, String name, String id, boolean activated) {
        super(name);
        this.id = id;
        this.type = type;
        this.activated = activated;
    }

    /**
     * Method to get the Nurse id
     * @return id of the nurse
     */
    public String getId() {
        return id;
    }

    /**
     * Method to set the Nurse id
     * @param id identification of the nurse
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method to get if the Nurse is activated
     * @return boolean true if the nurse is activated
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Method to set if nurse is activated
     * @param activated true if is activated or false if is deactivated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Method to get the kind of professional (nurse or doctor)
     * @return type of professional
     */
    public String getType() {
        return type;
    }

    /**
     * Method to set the kind of professional
     * @param type of professional
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method to develop the method of the interface Activable.This method sets the nurse as activated
     */
    @Override
    public void activable() {this.activated = true; }

    /**
     * Method to develop the method of the interface Activable. This method sets the nurse as deactivated
     */
    @Override
    public void deactivable() {this.activated = false; }

    /**
     * Method that return a String with the information of the nurse
     * @return String with personal information of the nurse
     */
    @Override
    public String toString() {
        return super.toString() + " - " + id + " - " + (activated ? "activated" : "deactivated");
    }

    /**
     * Method to prepare the object to be saved in a file.
     * @return Formatted string with information of the nurse
     */
    public String prepareNurseToFile() {
        return type + ";" + name + ";" + id + ";" + activated;
    }
}
