package fluxioair.model;

/**
 * Domestic flight operating within Colombia.
 *
 * INHERITANCE  : extends Flight (parent class).
 * POLYMORPHISM : overrides calculateFinalPrice(), getFlightType(),
 *                getTypeIcon(), generateSummary() and generateDetail().
 */
public class DomesticFlight extends Flight {

    // ======================== PRIVATE ATTRIBUTES (Encapsulation) ========================
    private double  estimatedDurationHours;
    private boolean includesMeal;

    // ======================== CONSTRUCTOR ========================
    public DomesticFlight(String flightCode, String originCity, String destinationCity,
                           String flightDate, String departureTime, String arrivalTime,
                           int totalCapacity, double basePrice,
                           double estimatedDurationHours, boolean includesMeal) {
        // Call parent constructor (Inheritance)
        super(flightCode, originCity, destinationCity,
              flightDate, departureTime, arrivalTime,
              totalCapacity, basePrice);
        this.estimatedDurationHours = estimatedDurationHours;
        this.includesMeal           = includesMeal;
    }

    // ======================== POLYMORPHISM: method overrides ========================

    /**
     * Domestic flight: final price equals base price (no extra charges).
     * POLYMORPHISM: overrides abstract method from parent.
     */
    @Override
    public double calculateFinalPrice() {
        return basePrice;
    }

    @Override
    public String getFlightType() {
        return "Nacional";
    }

    @Override
    public String getTypeIcon() {
        return "[NAL]";
    }

    /** One-line summary specific to domestic flights. */
    @Override
    public String generateSummary() {
        return String.format("[NAL] %-6s | %s → %s | %s | $%,.0f | Asientos: %d | %s",
                flightCode, originCity, destinationCity,
                flightDate,
                calculateFinalPrice(),
                availableSeats,
                flightStatus);
    }

    /**
     * Extended detail: calls parent generateDetail() and appends domestic fields.
     * POLYMORPHISM: overrides Reportable.generateDetail().
     */
    @Override
    public String generateDetail() {
        return "╔══════════════════════════════════════╗\n" +
               "║        VUELO NACIONAL                ║\n" +
               "╠══════════════════════════════════════╣\n" +
               super.generateDetail()                       + "\n" +
               "  Duración      : " + estimatedDurationHours + " horas" + "\n" +
               "  Alimentación  : " + (includesMeal ? "Sí incluye" : "No incluye") + "\n" +
               "╚══════════════════════════════════════╝";
    }

    // ======================== GETTERS ========================
    public double  getEstimatedDurationHours() { return estimatedDurationHours; }
    public boolean isIncludesMeal()            { return includesMeal; }

    // ======================== SETTERS ========================
    public void setEstimatedDurationHours(double hours) { this.estimatedDurationHours = hours; }
    public void setIncludesMeal(boolean includesMeal)   { this.includesMeal = includesMeal; }
}
