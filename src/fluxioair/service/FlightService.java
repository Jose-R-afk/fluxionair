package fluxioair.service;

import fluxioair.exception.FlightNotAvailableException;
import fluxioair.model.DomesticFlight;
import fluxioair.model.Flight;
import fluxioair.model.InternationalFlight;
import java.util.ArrayList;

/**
 * Service class managing flight business logic.
 *
 * POLYMORPHISM: the list is ArrayList<Flight> (parent type) but stores
 * DomesticFlight and InternationalFlight objects. Calling any method
 * like generateSummary() or calculateFinalPrice() automatically executes
 * the correct subclass version at runtime.
 */
public class FlightService {

    // POLYMORPHISM: parent-typed list that stores subclass instances
    private ArrayList<Flight> flightList;

    public FlightService() {
        this.flightList = new ArrayList<>();
    }

    // ======================== BUSINESS METHODS ========================

    /**
     * Adds a domestic flight to the system.
     * @throws FlightNotAvailableException if code already exists, capacity <= 0 or price <= 0
     */
    public void addDomesticFlight(String flightCode, String originCity, String destinationCity,
                                   String flightDate, String departureTime, String arrivalTime,
                                   int totalCapacity, double basePrice,
                                   double durationHours, boolean includesMeal) {
        validateCommonData(flightCode, totalCapacity, basePrice);
        flightList.add(new DomesticFlight(flightCode, originCity, destinationCity,
                flightDate, departureTime, arrivalTime,
                totalCapacity, basePrice, durationHours, includesMeal));
    }

    /**
     * Adds an international flight to the system.
     * @throws FlightNotAvailableException if code already exists, capacity <= 0 or price <= 0
     */
    public void addInternationalFlight(String flightCode, String originCity, String destinationCity,
                                        String flightDate, String departureTime, String arrivalTime,
                                        int totalCapacity, double basePrice,
                                        String destinationCountry, boolean requiresVisa,
                                        double internationalFee) {
        validateCommonData(flightCode, totalCapacity, basePrice);
        flightList.add(new InternationalFlight(flightCode, originCity, destinationCity,
                flightDate, departureTime, arrivalTime,
                totalCapacity, basePrice, destinationCountry, requiresVisa, internationalFee));
    }

    /**
     * Validates business rules common to all flight types.
     * @throws FlightNotAvailableException on any rule violation
     */
    private void validateCommonData(String flightCode, int totalCapacity, double basePrice) {
        if (findByCode(flightCode) != null) {
            throw new FlightNotAvailableException(flightCode,
                    "ya existe un vuelo con ese código");
        }
        if (totalCapacity <= 0) {
            throw new FlightNotAvailableException(flightCode,
                    "la capacidad total debe ser mayor a 0");
        }
        if (basePrice <= 0) {
            throw new FlightNotAvailableException(flightCode,
                    "el precio del vuelo debe ser mayor a cero");
        }
    }

    /**
     * Finds a flight by its code.
     * POLYMORPHISM: returns Flight (parent type), actual object is Domestic or International.
     * @return the Flight found, or null if not found.
     */
    public Flight findByCode(String flightCode) {
        for (Flight f : flightList) {
            if (f.getFlightCode().equalsIgnoreCase(flightCode)) return f;
        }
        return null;
    }

    /**
     * Changes the status of an existing flight.
     * @throws FlightNotAvailableException if the flight does not exist
     */
    public String changeFlightStatus(String flightCode, String newStatus) {
        Flight flight = findByCode(flightCode);
        if (flight == null) {
            throw new FlightNotAvailableException(flightCode, "no existe en el sistema");
        }
        flight.setFlightStatus(newStatus);
        return "EXITO: Estado del vuelo " + flightCode + " cambiado a '" + newStatus + "'.";
    }

    /**
     * Lists scheduled flights using generateSummary() — POLYMORPHISM:
     * each Flight (Domestic or International) generates its own summary.
     */
    public String listScheduledFlights() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════╗\n");
        sb.append("║       VUELOS PROGRAMADOS             ║\n");
        sb.append("╚══════════════════════════════════════╝\n");
        boolean found = false;
        for (Flight f : flightList) {
            if (f.isScheduled()) {
                // POLYMORPHISM: generateSummary() behaves differently per subclass
                sb.append(f.generateSummary()).append("\n");
                found = true;
            }
        }
        if (!found) sb.append("No hay vuelos programados disponibles.\n");
        return sb.toString();
    }

    /**
     * Lists all flights with full detail.
     * POLYMORPHISM: generateDetail() returns the detail of each subclass.
     */
    public String listAllFlights() {
        if (flightList.isEmpty()) return "No hay vuelos registrados en el sistema.";
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════╗\n");
        sb.append("║         TODOS LOS VUELOS             ║\n");
        sb.append("╚══════════════════════════════════════╝\n");
        sb.append("Total: ").append(flightList.size()).append(" vuelo(s)\n\n");
        for (Flight f : flightList) {
            // POLYMORPHISM: generateDetail() of DomesticFlight or InternationalFlight
            sb.append(f.generateDetail()).append("\n\n");
        }
        return sb.toString();
    }

    /** Returns the full flight list. */
    public ArrayList<Flight> getFlightList() {
        return flightList;
    }
}
