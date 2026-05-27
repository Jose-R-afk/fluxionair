package fluxioair.exception;

/**
 * Exception thrown when trying to register a passenger
 * with an ID number that already exists in the system.
 */
public class DuplicateIdException extends RuntimeException {

    private final String idNumber;

    public DuplicateIdException(String idNumber) {
        super("Ya existe un pasajero con esa cédula: " + idNumber);
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }
}
