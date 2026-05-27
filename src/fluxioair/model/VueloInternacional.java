package fluxioair.model;

/**
 * Clase que representa un vuelo INTERNACIONAL de FluxioAir.
 *
 * Aplica HERENCIA: extiende la clase padre Vuelo.
 * Aplica POLIMORFISMO: sobreescribe calcularPrecioFinal() sumando cargo internacional.
 * Aplica ENCAPSULAMIENTO: atributos privados propios de vuelos internacionales.
 */
public class VueloInternacional extends Vuelo {

    // ======================== ATRIBUTOS PROPIOS (Encapsulamiento) ========================
    private String  paisDestino;
    private boolean requiereVisa;
    private double  cargoInternacional; // Impuesto adicional por vuelo internacional

    // ======================== CONSTRUCTOR ========================
    public VueloInternacional(String codigoVuelo, String ciudadOrigen, String ciudadDestino,
                               String fechaVuelo, String horaSalida, String horaLlegada,
                               int capacidadTotal, double precioBase,
                               String paisDestino, boolean requiereVisa, double cargoInternacional) {
        // Llamada al constructor del PADRE (Herencia)
        super(codigoVuelo, ciudadOrigen, ciudadDestino,
              fechaVuelo, horaSalida, horaLlegada,
              capacidadTotal, precioBase);
        this.paisDestino          = paisDestino;
        this.requiereVisa         = requiereVisa;
        this.cargoInternacional   = cargoInternacional;
    }

    // ======================== POLIMORFISMO: sobreescritura de métodos abstractos ========================

    /**
     * Para vuelos internacionales: precio final = precio base + cargo internacional.
     * POLIMORFISMO: implementación diferente al de VueloNacional.
     */
    @Override
    public double calcularPrecioFinal() {
        return precioBase + cargoInternacional;
    }

    /**
     * POLIMORFISMO: sobreescribe el método abstracto del padre.
     */
    @Override
    public String getTipoVuelo() {
        return "Internacional";
    }

    // ======================== GETTERS ========================
    public String  getPaisDestino()          { return paisDestino; }
    public boolean isRequiereVisa()          { return requiereVisa; }
    public double  getCargoInternacional()   { return cargoInternacional; }

    // ======================== SETTERS ========================
    public void setPaisDestino(String paisDestino)                  { this.paisDestino = paisDestino; }
    public void setRequiereVisa(boolean requiereVisa)               { this.requiereVisa = requiereVisa; }
    public void setCargoInternacional(double cargoInternacional)    { this.cargoInternacional = cargoInternacional; }

    // ======================== toString (extiende el del padre) ========================
    @Override
    public String toString() {
        return "╔══════════════════════════════════════╗\n" +
               "║      VUELO INTERNACIONAL             ║\n" +
               "╠══════════════════════════════════════╣\n" +
               super.toString() + "\n" +
               "  País destino  : " + paisDestino + "\n" +
               "  Requiere visa : " + (requiereVisa ? "Sí" : "No") + "\n" +
               "  Cargo intern. : $" + String.format("%,.0f", cargoInternacional) + "\n" +
               "╚══════════════════════════════════════╝";
    }
}
