package fluxioair.exception;

/**
 * Exception thrown when searching for or trying to cancel
 * a booking with a code that does not exist in the system.
 */
public class BookingNotFoundException extends RuntimeException {

    private final String bookingCode;

    public BookingNotFoundException(String bookingCode) {
        super("No se encontró la reserva con ese código: " + bookingCode);
        this.bookingCode = bookingCode;
    }

    public String getBookingCode() {
        return bookingCode;
    }
}
