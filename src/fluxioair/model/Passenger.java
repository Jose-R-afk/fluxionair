package fluxioair.model;

/**
 * Class representing a FluxioAir passenger.
 *
 * ENCAPSULATION : private attributes with getters/setters.
 * POLYMORPHISM  : implements Reportable with its own version of
 *                 generateSummary() and generateDetail().
 */
public class Passenger implements Reportable {

    // ======================== PRIVATE ATTRIBUTES (Encapsulation) ========================
    private String idNumber;
    private String firstName;
    private String lastName;
    private int    age;
    private String email;
    private String phoneNumber;
    private String passportNumber;
    private String nationality;

    // ======================== CONSTRUCTOR ========================
    public Passenger(String idNumber, String firstName, String lastName, int age,
                     String email, String phoneNumber, String passportNumber, String nationality) {
        this.idNumber       = idNumber;
        this.firstName      = firstName;
        this.lastName       = lastName;
        this.age            = age;
        this.email          = email;
        this.phoneNumber    = phoneNumber;
        this.passportNumber = passportNumber;
        this.nationality    = nationality;
    }

    // ======================== GETTERS ========================
    public String getIdNumber()       { return idNumber; }
    public String getFirstName()      { return firstName; }
    public String getLastName()       { return lastName; }
    public int    getAge()            { return age; }
    public String getEmail()          { return email; }
    public String getPhoneNumber()    { return phoneNumber; }
    public String getPassportNumber() { return passportNumber; }
    public String getNationality()    { return nationality; }

    // ======================== SETTERS ========================
    public void setIdNumber(String idNumber)           { this.idNumber = idNumber; }
    public void setFirstName(String firstName)         { this.firstName = firstName; }
    public void setLastName(String lastName)           { this.lastName = lastName; }
    public void setAge(int age)                        { this.age = age; }
    public void setEmail(String email)                 { this.email = email; }
    public void setPhoneNumber(String phoneNumber)     { this.phoneNumber = phoneNumber; }
    public void setPassportNumber(String passportNumber){ this.passportNumber = passportNumber; }
    public void setNationality(String nationality)     { this.nationality = nationality; }

    // ======================== METHODS ========================
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // ======================== POLYMORPHISM: Reportable ========================

    /**
     * Short one-line summary for listings.
     * POLYMORPHISM: Passenger's own implementation.
     */
    @Override
    public String generateSummary() {
        return String.format("Pasajero | Cédula: %-12s | %-25s | Pasaporte: %s",
                idNumber, getFullName(), passportNumber);
    }

    /**
     * Full passenger detail.
     * POLYMORPHISM: Passenger's own implementation.
     */
    @Override
    public String generateDetail() {
        return "╔══════════════════════════════════════╗\n" +
               "║         DATOS DEL PASAJERO           ║\n" +
               "╠══════════════════════════════════════╣\n" +
               "║ Cédula       : " + idNumber       + "\n" +
               "║ Nombre       : " + getFullName()  + "\n" +
               "║ Edad         : " + age + " años"  + "\n" +
               "║ Email        : " + email          + "\n" +
               "║ Teléfono     : " + phoneNumber    + "\n" +
               "║ Pasaporte    : " + passportNumber + "\n" +
               "║ Nacionalidad : " + nationality    + "\n" +
               "╚══════════════════════════════════════╝";
    }

    @Override
    public String toString() {
        return generateDetail();
    }
}
