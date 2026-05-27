package fluxioair.model;

/**
 * Class representing a flight booking in FluxioAir.
 *
 * ENCAPSULATION: all attributes are private with getters/setters.
 * POLYMORPHISM via interface: implements Reportable.
 * POLYMORPHISM in use: calls flight.calculateFinalPrice() which executes
 *   the correct version (Domestic or International) at runtime.
 */
public class Booking implements Reportable {

    // ======================== STATUS CONSTANTS ========================
    public static final String STATUS_CONFIRMED = "Confirmada";
    public static final String STATUS_CANCELLED = "Cancelada";
    public static final String STATUS_COMPLETED = "Completada";

    // ======================== PRIVATE ATTRIBUTES (Encapsulation) ========================
    private String    bookingCode;
    private Passenger passenger;       // Association with Passenger
    private Flight    flight;          // Polymorphism: can be Domestic or International
    private int       seatsBooked;
    private String    bookingDate;
    private double    totalPrice;
    private String    bookingStatus;

    // ======================== CONSTRUCTOR ========================
    public Booking(String bookingCode, Passenger passenger, Flight flight,
                   int seatsBooked, String bookingDate) {
        this.bookingCode   = bookingCode;
        this.passenger     = passenger;
        this.flight        = flight;
        this.seatsBooked   = seatsBooked;
        this.bookingDate   = bookingDate;
        // POLYMORPHISM: calculateFinalPrice() runs the correct subclass version
        this.totalPrice    = flight.calculateFinalPrice() * seatsBooked;
        this.bookingStatus = STATUS_CONFIRMED;
    }

    // ======================== METHODS ========================

    /**
     * Cancels the booking if it is currently confirmed.
     * @return true if cancelled successfully, false otherwise.
     */
    public boolean cancel() {
        if (STATUS_CONFIRMED.equals(bookingStatus)) {
            bookingStatus = STATUS_CANCELLED;
            flight.returnSeats(seatsBooked);
            return true;
        }
        return false;
    }

    /** Returns true if the booking is confirmed. */
    public boolean isConfirmed() {
        return STATUS_CONFIRMED.equals(bookingStatus);
    }

    // ======================== POLYMORPHISM: Reportable ========================

    /**
     * One-line summary specific to bookings.
     * POLYMORPHISM: Booking's own implementation, different from Passenger and Flight.
     */
    @Override
    public String generateSummary() {
        return String.format("Reserva %-6s | %s | Vuelo: %-6s | %s → %s | Asientos: %d | $%,.0f | %s",
                bookingCode,
                bookingDate,
                flight.getFlightCode(),
                flight.getOriginCity(),
                flight.getDestinationCity(),
                seatsBooked,
                totalPrice,
                bookingStatus);
    }

    /**
     * Full booking detail.
     * POLYMORPHISM: calls flight.getFlightType() and flight.getTypeIcon()
     *   which return different values depending on Domestic or International.
     */
    @Override
    public String generateDetail() {
        return "╔══════════════════════════════════════════════╗\n" +
               "║             DETALLE DE RESERVA              ║\n" +
               "╠══════════════════════════════════════════════╣\n" +
               "║ Código reserva  : " + bookingCode                             + "\n" +
               "║ Estado          : " + bookingStatus                           + "\n" +
               "║ Fecha reserva   : " + bookingDate                             + "\n" +
               "║ Pasajero        : " + passenger.getFullName()                 + "\n" +
               "║ Cédula          : " + passenger.getIdNumber()                 + "\n" +
               "║ Vuelo           : " + flight.getFlightCode()                  + "\n" +
               "║ Tipo            : " + flight.getTypeIcon() + " " + flight.getFlightType() + "\n" +
               "║ Ruta            : " + flight.getOriginCity() + " → " + flight.getDestinationCity() + "\n" +
               "║ Fecha vuelo     : " + flight.getFlightDate()                  + "\n" +
               "║ Asientos        : " + seatsBooked                             + "\n" +
               "║ Precio x asiento: $" + String.format("%,.0f", flight.calculateFinalPrice()) + "\n" +
               "║ PRECIO TOTAL    : $" + String.format("%,.0f", totalPrice)     + "\n" +
               "╚══════════════════════════════════════════════╝";
    }

    @Override
    public String toString() {
        return generateDetail();
    }

    // ======================== GETTERS ========================
    public String    getBookingCode()   { return bookingCode; }
    public Passenger getPassenger()     { return passenger; }
    public Flight    getFlight()        { return flight; }
    public int       getSeatsBooked()   { return seatsBooked; }
    public String    getBookingDate()   { return bookingDate; }
    public double    getTotalPrice()    { return totalPrice; }
    public String    getBookingStatus() { return bookingStatus; }

    // ======================== SETTERS ========================
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
}
