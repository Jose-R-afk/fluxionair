package fluxioair.service;

import fluxioair.exception.DuplicateIdException;
import fluxioair.exception.DuplicatePassportException;
import fluxioair.exception.InvalidEmailException;
import fluxioair.model.Passenger;
import java.util.ArrayList;

/**
 * Service class managing passenger business logic.
 *
 * ABSTRACTION: encapsulates business logic away from the model and UI layers.
 * Uses ArrayList to store the passenger collection in memory.
 */
public class PassengerService {

    private ArrayList<Passenger> passengerList;

    public PassengerService() {
        this.passengerList = new ArrayList<>();
    }

    // ======================== BUSINESS METHODS ========================

    /**
     * Registers a new passenger after validating all business rules.
     *
     * @throws InvalidEmailException      if the email does not contain "@"
     * @throws DuplicateIdException       if a passenger with that ID already exists
     * @throws DuplicatePassportException if a passenger with that passport already exists
     * @throws IllegalArgumentException   if any required field is empty or age is negative
     */
    public void registerPassenger(String idNumber, String firstName, String lastName,
                                   int age, String email, String phoneNumber,
                                   String passportNumber, String nationality) {

        // Validate required fields
        if (idNumber.trim().isEmpty() || firstName.trim().isEmpty() ||
            lastName.trim().isEmpty() || phoneNumber.trim().isEmpty() || nationality.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        // Validate age
        if (age < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }

        // Validate email — uses contains() as required by the exam
        if (!email.contains("@")) {
            throw new InvalidEmailException(email);
        }

        // Validate unique ID
        if (findByIdNumber(idNumber) != null) {
            throw new DuplicateIdException(idNumber);
        }

        // Validate unique passport
        if (findByPassportNumber(passportNumber) != null) {
            throw new DuplicatePassportException(passportNumber);
        }

        passengerList.add(new Passenger(idNumber, firstName, lastName, age,
                                         email, phoneNumber, passportNumber, nationality));
    }

    /**
     * Finds a passenger by ID number.
     * @return the Passenger found, or null if not found.
     */
    public Passenger findByIdNumber(String idNumber) {
        for (Passenger p : passengerList) {
            if (p.getIdNumber().equals(idNumber)) return p;
        }
        return null;
    }

    /**
     * Finds a passenger by passport number.
     * @return the Passenger found, or null if not found.
     */
    public Passenger findByPassportNumber(String passportNumber) {
        for (Passenger p : passengerList) {
            if (p.getPassportNumber().equals(passportNumber)) return p;
        }
        return null;
    }

    /** Returns the total number of registered passengers. */
    public int getTotalPassengers() {
        return passengerList.size();
    }

    /** Returns the full passenger list. */
    public ArrayList<Passenger> getPassengerList() {
        return passengerList;
    }

    /**
     * Lists all passengers using generateSummary() — POLYMORPHISM:
     * each Reportable object generates its own summary line.
     */
    public String listPassengers() {
        if (passengerList.isEmpty()) {
            return "No hay pasajeros registrados en el sistema.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════╗\n");
        sb.append("║   LISTA DE PASAJEROS REGISTRADOS     ║\n");
        sb.append("╚══════════════════════════════════════╝\n");
        sb.append("Total: ").append(passengerList.size()).append(" pasajero(s)\n\n");
        for (int i = 0; i < passengerList.size(); i++) {
            // POLYMORPHISM: calls generateSummary() from Passenger
            sb.append("[").append(i + 1).append("] ")
              .append(passengerList.get(i).generateSummary())
              .append("\n");
        }
        return sb.toString();
    }
}
