package fluxioair.model;

/**
 * Interface that defines the contract for generating reports in FluxioAir.
 *
 * POLYMORPHISM via interface: any class implementing Reportable
 * must provide its own version of these methods.
 */
public interface Reportable {

    /**
     * Generates a short one-line summary for listings.
     */
    String generateSummary();

    /**
     * Generates the full detail of the object for reports.
     */
    String generateDetail();
}
