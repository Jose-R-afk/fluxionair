package fluxioair.model;

/**
 * Clase que representa un vuelo NACIONAL de FluxioAir (dentro de Colombia).
 *
 * Aplica HERENCIA: extiende la clase padre Vuelo.
 * Aplica POLIMORFISMO: sobreescribe calcularPrecioFinal() y getTipoVuelo().
 * Aplica ENCAPSULAMIENTO: atributos privados propios de este tipo de vuelo.
 */
public class VueloNacional extends Vuelo {

    // ======================== ATRIBUTOS PROPIOS (Encapsulamiento) ========================
    private double duracionHoras;    // Duración estimada en horas (ej: 1.5, 2.0)
    private boolean incluyeAlimentacion;

    // ======================== CONSTRUCTOR ========================
    public VueloNacional(String codigoVuelo, String ciudadOrigen, String ciudadDestino,
                         String fechaVuelo, String horaSalida, String horaLlegada,
                         int capacidadTotal, double precioBase,
                         double duracionHoras, boolean incluyeAlimentacion) {
        // Llamada al constructor del PADRE (Herencia)
        super(codigoVuelo, ciudadOrigen, ciudadDestino,
              fechaVuelo, horaSalida, horaLlegada,
              capacidadTotal, precioBase);
        this.duracionHoras        = duracionHoras;
        this.incluyeAlimentacion  = incluyeAlimentacion;
    }

    // ======================== POLIMORFISMO: sobreescritura de métodos abstractos ========================

    /**
     * Para vuelos nacionales, el precio final ES IGUAL al precio base.
     * Sin cargos adicionales.
     * POLIMORFISMO: sobreescribe el método abstracto del padre.
     */
    @Override
    public double calcularPrecioFinal() {
        return precioBase; // Sin cargos adicionales
    }

    /**
     * POLIMORFISMO: sobreescribe el método abstracto del padre.
     */
    @Override
    public String getTipoVuelo() {
        return "Nacional";
    }

    // ======================== GETTERS ========================
    public double  getDuracionHoras()       { return duracionHoras; }
    public boolean isIncluyeAlimentacion()  { return incluyeAlimentacion; }

    // ======================== SETTERS ========================
    public void setDuracionHoras(double duracionHoras)            { this.duracionHoras = duracionHoras; }
    public void setIncluyeAlimentacion(boolean incluyeAlimentacion) { this.incluyeAlimentacion = incluyeAlimentacion; }

    // ======================== toString (extiende el del padre) ========================
    @Override
    public String toString() {
        return "╔══════════════════════════════════════╗\n" +
               "║        VUELO NACIONAL                ║\n" +
               "╠══════════════════════════════════════╣\n" +
               super.toString() + "\n" +
               "  Duración      : " + duracionHoras + " horas" + "\n" +
               "  Alimentación  : " + (incluyeAlimentacion ? "Sí" : "No") + "\n" +
               "╚══════════════════════════════════════╝";
    }
}
