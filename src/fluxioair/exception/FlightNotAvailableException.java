package fluxioair.exception;

/**
 * Exception thrown when trying to book a flight that does not exist,
 * is not scheduled, or has an invalid price or capacity.
 */
public class FlightNotAvailableException extends RuntimeException {

    private final String flightCode;
    private final String reason;

    public FlightNotAvailableException(String flightCode, String reason) {
        super("Vuelo " + flightCode + " no disponible: " + reason);
        this.flightCode = flightCode;
        this.reason     = reason;
    }

    public String getFlightCode() { return flightCode; }
    public String getReason()     { return reason; }
}
