package fluxioair.service;

import fluxioair.exception.BookingNotFoundException;
import fluxioair.exception.FlightNotAvailableException;
import fluxioair.exception.InsufficientSeatsException;
import fluxioair.model.Booking;
import fluxioair.model.Flight;
import fluxioair.model.Passenger;
import java.util.ArrayList;

/**
 * Service class managing booking business logic.
 *
 * Throws custom exceptions for each business rule violation.
 * POLYMORPHISM: works with Flight references (parent type) whose real
 * behaviour (Domestic or International) is resolved at runtime.
 */
public class BookingService {

    private ArrayList<Booking> bookingList;
    private PassengerService   passengerService;
    private FlightService      flightService;
    private int                bookingCounter;

    public BookingService(PassengerService passengerService, FlightService flightService) {
        this.bookingList      = new ArrayList<>();
        this.passengerService = passengerService;
        this.flightService    = flightService;
        this.bookingCounter   = 1;
    }

    // ======================== BUSINESS METHODS ========================

    /**
     * Creates a new booking for a passenger on a specific flight.
     *
     * @throws IllegalArgumentException    if the passenger does not exist
     * @throws FlightNotAvailableException if the flight does not exist or is not scheduled
     * @throws InsufficientSeatsException  if seats requested > 5 or not enough availability
     */
    public Booking createBooking(String passengerId, String flightCode,
                                  int seatsRequested, String bookingDate) {

        // Step 1: Verify passenger exists
        Passenger passenger = passengerService.findByIdNumber(passengerId);
        if (passenger == null) {
            throw new IllegalArgumentException(
                    "No se encontró el pasajero con esa cédula: " + passengerId);
        }

        // Step 2: Verify flight exists and is scheduled
        Flight flight = flightService.findByCode(flightCode);
        if (flight == null) {
            throw new FlightNotAvailableException(flightCode, "no existe en el sistema");
        }
        if (!flight.isScheduled()) {
            throw new FlightNotAvailableException(flightCode,
                    "estado actual es '" + flight.getFlightStatus() + "', debe ser Programado");
        }

        // Step 3: Validate seat count (1–5)
        if (seatsRequested < 1 || seatsRequested > 5) {
            throw new InsufficientSeatsException(
                    "No se pueden reservar más de 5 asientos por reserva");
        }

        // Step 4: Check availability
        if (!flight.hasAvailableSeats(seatsRequested)) {
            throw new InsufficientSeatsException(seatsRequested, flight.getAvailableSeats());
        }

        // Step 5: Create and store the booking
        String  code    = generateBookingCode();
        Booking booking = new Booking(code, passenger, flight, seatsRequested, bookingDate);
        flight.reserveSeats(seatsRequested);
        bookingList.add(booking);
        return booking;
    }

    /**
     * Cancels an existing booking by its code.
     *
     * @throws BookingNotFoundException if the code does not exist
     * @throws IllegalStateException    if the booking is already cancelled or completed
     */
    public Booking cancelBooking(String bookingCode) {
        Booking booking = findByCode(bookingCode);
        if (booking == null) {
            throw new BookingNotFoundException(bookingCode);
        }
        if (!booking.isConfirmed()) {
            throw new IllegalStateException(
                    "La reserva " + bookingCode + " no puede cancelarse. " +
                    "Estado actual: " + booking.getBookingStatus());
        }
        booking.cancel();
        return booking;
    }

    /**
     * Finds and returns a booking by its code, or throws if not found.
     * @throws BookingNotFoundException if the code does not exist
     */
    public Booking getBookingByCode(String bookingCode) {
        Booking booking = findByCode(bookingCode);
        if (booking == null) {
            throw new BookingNotFoundException(bookingCode);
        }
        return booking;
    }

    /**
     * Internal search — returns null if not found (no exception).
     */
    public Booking findByCode(String bookingCode) {
        for (Booking b : bookingList) {
            if (b.getBookingCode().equalsIgnoreCase(bookingCode)) return b;
        }
        return null;
    }

    /**
     * Lists all bookings for a specific passenger.
     * POLYMORPHISM: uses generateSummary() from Booking which internally
     * calls polymorphic methods of Flight.
     */
    public String getBookingsByPassenger(String passengerId) {
        Passenger passenger = passengerService.findByIdNumber(passengerId);
        if (passenger == null) {
            throw new IllegalArgumentException(
                    "No se encontró el pasajero con esa cédula: " + passengerId);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════════════╗\n");
        sb.append("║       RESERVAS DEL PASAJERO                 ║\n");
        sb.append("╠══════════════════════════════════════════════╣\n");
        sb.append("║ Pasajero: ").append(passenger.getFullName()).append("\n");
        sb.append("╚══════════════════════════════════════════════╝\n");
        int count = 0;
        for (Booking b : bookingList) {
            if (b.getPassenger().getIdNumber().equals(passengerId)) {
                // POLYMORPHISM: generateSummary() from Booking
                sb.append(b.generateSummary()).append("\n");
                count++;
            }
        }
        if (count == 0) sb.append("Sin reservas registradas.\n");
        else            sb.append("Total: ").append(count).append(" reserva(s)\n");
        return sb.toString();
    }

    /** Returns total registered passengers (Report #2). */
    public int getTotalPassengers() {
        return passengerService.getTotalPassengers();
    }

    /** Lists all bookings with full detail. */
    public String listAllBookings() {
        if (bookingList.isEmpty()) return "No hay reservas registradas en el sistema.";
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════════════╗\n");
        sb.append("║         TODAS LAS RESERVAS                  ║\n");
        sb.append("╚══════════════════════════════════════════════╝\n");
        sb.append("Total: ").append(bookingList.size()).append(" reserva(s)\n\n");
        for (Booking b : bookingList) {
            sb.append(b.generateDetail()).append("\n");
        }
        return sb.toString();
    }

    /** Returns the full booking list. */
    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }

    /** Auto-generates a unique booking code: R001, R002, … */
    private String generateBookingCode() {
        String code;
        do {
            code = String.format("R%03d", bookingCounter++);
        } while (findByCode(code) != null);
        return code;
    }
}
