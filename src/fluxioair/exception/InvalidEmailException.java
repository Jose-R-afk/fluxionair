package fluxioair.exception;

/**
 * Exception thrown when the provided email does not contain "@"
 * or does not meet the minimum format required by FluxioAir.
 */
public class InvalidEmailException extends RuntimeException {

    private final String email;

    public InvalidEmailException(String email) {
        super("El email debe contener el símbolo @. Email recibido: '" + email + "'");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
