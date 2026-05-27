package fluxioair.model;

/**
 * International flight travelling to another country.
 *
 * INHERITANCE  : extends Flight (parent class).
 * POLYMORPHISM : overrides calculateFinalPrice() adding the international fee,
 *                getFlightType(), getTypeIcon(), generateSummary() and generateDetail().
 */
public class InternationalFlight extends Flight {

    // ======================== PRIVATE ATTRIBUTES (Encapsulation) ========================
    private String  destinationCountry;
    private boolean requiresVisa;
    private double  internationalFee;   // Additional tax for international travel

    // ======================== CONSTRUCTOR ========================
    public InternationalFlight(String flightCode, String originCity, String destinationCity,
                                String flightDate, String departureTime, String arrivalTime,
                                int totalCapacity, double basePrice,
                                String destinationCountry, boolean requiresVisa,
                                double internationalFee) {
        // Call parent constructor (Inheritance)
        super(flightCode, originCity, destinationCity,
              flightDate, departureTime, arrivalTime,
              totalCapacity, basePrice);
        this.destinationCountry = destinationCountry;
        this.requiresVisa       = requiresVisa;
        this.internationalFee   = internationalFee;
    }

    // ======================== POLYMORPHISM: method overrides ========================

    /**
     * International flight: final price = base price + international fee.
     * POLYMORPHISM: different implementation from DomesticFlight.
     */
    @Override
    public double calculateFinalPrice() {
        return basePrice + internationalFee;
    }

    @Override
    public String getFlightType() {
        return "Internacional";
    }

    @Override
    public String getTypeIcon() {
        return "[INT]";
    }

    /** One-line summary specific to international flights. */
    @Override
    public String generateSummary() {
        return String.format("[INT] %-6s | %s → %s (%s) | %s | $%,.0f | Asientos: %d | Visa: %s | %s",
                flightCode, originCity, destinationCity,
                destinationCountry,
                flightDate,
                calculateFinalPrice(),
                availableSeats,
                (requiresVisa ? "SI" : "NO"),
                flightStatus);
    }

    /**
     * Extended detail: calls parent generateDetail() and appends international fields.
     * POLYMORPHISM: overrides Reportable.generateDetail().
     */
    @Override
    public String generateDetail() {
        return "╔══════════════════════════════════════╗\n" +
               "║      VUELO INTERNACIONAL             ║\n" +
               "╠══════════════════════════════════════╣\n" +
               super.generateDetail()                       + "\n" +
               "  País destino  : " + destinationCountry    + "\n" +
               "  Requiere visa : " + (requiresVisa ? "Sí" : "No") + "\n" +
               "  Cargo intern. : $" + String.format("%,.0f", internationalFee) + "\n" +
               "╚══════════════════════════════════════╝";
    }

    // ======================== GETTERS ========================
    public String  getDestinationCountry() { return destinationCountry; }
    public boolean isRequiresVisa()        { return requiresVisa; }
    public double  getInternationalFee()   { return internationalFee; }

    // ======================== SETTERS ========================
    public void setDestinationCountry(String country)      { this.destinationCountry = country; }
    public void setRequiresVisa(boolean requiresVisa)      { this.requiresVisa = requiresVisa; }
    public void setInternationalFee(double fee)            { this.internationalFee = fee; }
}
