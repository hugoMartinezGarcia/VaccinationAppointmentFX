package vaccinationAppointments.data;

/**
 * Abstract class to store data about different types of people
 * @author Hugo Martínez
 * @version 1.0
 */
public abstract class Person {
    protected String name;

    /**
     * Constructor to establish a new Person
     * @param name Person name
     */
    public Person (String name) {
        this.name = name;
    }

    /**
     * Method that return the name of the person
     * @return Person name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to give a name for the person
     * @param name Person name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method that return a string with the name of the person
     * @return name of the person
     */
    @Override
    public String toString() {
        return name;
    }
}
