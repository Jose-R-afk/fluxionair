package fluxioair.model;

/**
 * Abstract class representing a generic FluxioAir flight.
 *
 * ABSTRACTION  : cannot be instantiated directly.
 * INHERITANCE  : parent class of DomesticFlight and InternationalFlight.
 * ENCAPSULATION: protected attributes accessible only in subclasses.
 * POLYMORPHISM :
 *   - calculateFinalPrice() → abstract, each subclass implements differently.
 *   - getFlightType()       → abstract, each subclass returns its own type.
 *   - getTypeIcon()         → abstract, each subclass returns its own icon.
 *   - generateSummary()     → abstract via Reportable, different per subclass.
 *   - generateDetail()      → concrete in Flight, extended by subclasses.
 */
public abstract class Flight implements Reportable {

    // ======================== STATUS CONSTANTS ========================
    public static final String STATUS_SCHEDULED = "Programado";
    public static final String STATUS_IN_FLIGHT = "En Vuelo";
    public static final String STATUS_LANDED    = "Aterrizado";
    public static final String STATUS_CANCELLED = "Cancelado";

    // ======================== PROTECTED ATTRIBUTES (Encapsulation + Inheritance) ========================
    protected String flightCode;
    protected String originCity;
    protected String destinationCity;
    protected String flightDate;
    protected String departureTime;
    protected String arrivalTime;
    protected int    totalCapacity;
    protected int    availableSeats;
    protected double basePrice;
    protected String flightStatus;

    // ======================== CONSTRUCTOR ========================
    public Flight(String flightCode, String originCity, String destinationCity,
                  String flightDate, String departureTime, String arrivalTime,
                  int totalCapacity, double basePrice) {
        this.flightCode       = flightCode;
        this.originCity       = originCity;
        this.destinationCity  = destinationCity;
        this.flightDate       = flightDate;
        this.departureTime    = departureTime;
        this.arrivalTime      = arrivalTime;
        this.totalCapacity    = totalCapacity;
        this.availableSeats   = totalCapacity;  // Initially equal to total capacity
        this.basePrice        = basePrice;
        this.flightStatus     = STATUS_SCHEDULED;
    }

    // ======================== ABSTRACT METHODS (Polymorphism) ========================
    public abstract double calculateFinalPrice();
    public abstract String getFlightType();
    public abstract String getTypeIcon();

    // ======================== CONCRETE METHODS ========================

    /**
     * Reserves a number of seats if availability allows.
     * @return true if reservation succeeded, false otherwise.
     */
    public boolean reserveSeats(int amount) {
        if (availableSeats >= amount) {
            availableSeats -= amount;
            return true;
        }
        return false;
    }

    /**
     * Returns seats to the flight when a booking is cancelled.
     */
    public void returnSeats(int amount) {
        availableSeats += amount;
        if (availableSeats > totalCapacity) {
            availableSeats = totalCapacity;
        }
    }

    /** Returns true if the flight status is "Programado". */
    public boolean isScheduled() {
        return STATUS_SCHEDULED.equals(flightStatus);
    }

    /** Returns true if enough seats are available. */
    public boolean hasAvailableSeats(int amount) {
        return availableSeats >= amount;
    }

    // ======================== POLYMORPHISM: Reportable ========================

    /**
     * One-line summary — abstract so each subclass personalises it.
     */
    @Override
    public abstract String generateSummary();

    /**
     * Common detail shared by all flight types.
     * Subclasses extend it via super.generateDetail().
     */
    @Override
    public String generateDetail() {
        return "  Código        : " + flightCode                                     + "\n" +
               "  Tipo          : " + getTypeIcon() + " " + getFlightType()          + "\n" +
               "  Ruta          : " + originCity + " → " + destinationCity           + "\n" +
               "  Fecha         : " + flightDate                                     + "\n" +
               "  Horario       : " + departureTime + " - " + arrivalTime            + "\n" +
               "  Capacidad     : " + totalCapacity + " asientos"                    + "\n" +
               "  Disponibles   : " + availableSeats + " asientos"                   + "\n" +
               "  Precio base   : $" + String.format("%,.0f", basePrice)             + "\n" +
               "  Precio final  : $" + String.format("%,.0f", calculateFinalPrice()) + "\n" +
               "  Estado        : " + flightStatus;
    }

    @Override
    public String toString() {
        return generateDetail();
    }

    // ======================== GETTERS ========================
    public String getFlightCode()      { return flightCode; }
    public String getOriginCity()      { return originCity; }
    public String getDestinationCity() { return destinationCity; }
    public String getFlightDate()      { return flightDate; }
    public String getDepartureTime()   { return departureTime; }
    public String getArrivalTime()     { return arrivalTime; }
    public int    getTotalCapacity()   { return totalCapacity; }
    public int    getAvailableSeats()  { return availableSeats; }
    public double getBasePrice()       { return basePrice; }
    public String getFlightStatus()    { return flightStatus; }

    // ======================== SETTERS ========================
    public void setFlightStatus(String flightStatus)    { this.flightStatus = flightStatus; }
    public void setBasePrice(double basePrice)           { this.basePrice = basePrice; }
    public void setAvailableSeats(int availableSeats)    { this.availableSeats = availableSeats; }
}
