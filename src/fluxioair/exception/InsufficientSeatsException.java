package fluxioair.exception;

/**
 * Exception thrown when there are not enough available seats
 * on the flight to complete the requested booking.
 */
public class InsufficientSeatsException extends RuntimeException {

    private final int requested;
    private final int available;

    public InsufficientSeatsException(int requested, int available) {
        super("No hay asientos disponibles para este vuelo. " +
              "Solicitados: " + requested + " | Disponibles: " + available);
        this.requested = requested;
        this.available = available;
    }

    // Constructor for when more than 5 seats are requested
    public InsufficientSeatsException(String message) {
        super(message);
        this.requested = 0;
        this.available = 0;
    }

    public int getRequested() { return requested; }
    public int getAvailable() { return available; }
}
