package fluxioair.exception;

/**
 * Exception thrown when trying to register a passenger
 * with a passport number that already exists in the system.
 */
public class DuplicatePassportException extends RuntimeException {

    private final String passportNumber;

    public DuplicatePassportException(String passportNumber) {
        super("Ya existe un pasajero con ese número de pasaporte: " + passportNumber);
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }
}
