package fluxioair.model;

/**
 * Clase ABSTRACTA que representa un vuelo genérico de FluxioAir.
 *
 * Aplica ABSTRACCIÓN: define la estructura común de todos los vuelos
 *   sin instanciarse directamente.
 * Aplica HERENCIA: es la clase PADRE de VueloNacional y VueloInternacional.
 * Aplica ENCAPSULAMIENTO: atributos protected accesibles solo en subclases.
 * Aplica POLIMORFISMO: método abstracto calcularPrecioFinal() es sobreescrito
 *   en cada subclase con su propia lógica de cálculo.
 */
public abstract class Vuelo {

    // ======================== CONSTANTES DE ESTADO ========================
    public static final String ESTADO_PROGRAMADO  = "Programado";
    public static final String ESTADO_EN_VUELO    = "En Vuelo";
    public static final String ESTADO_ATERRIZADO  = "Aterrizado";
    public static final String ESTADO_CANCELADO   = "Cancelado";

    // ======================== ATRIBUTOS PROTECTED (Encapsulamiento + Herencia) ========================
    protected String codigoVuelo;
    protected String ciudadOrigen;
    protected String ciudadDestino;
    protected String fechaVuelo;       // Formato: DD/MM/YYYY
    protected String horaSalida;       // Formato: HH:MM
    protected String horaLlegada;      // Formato: HH:MM
    protected int    capacidadTotal;
    protected int    asientosDisponibles;
    protected double precioBase;
    protected String estadoVuelo;

    // ======================== CONSTRUCTOR ========================
    public Vuelo(String codigoVuelo, String ciudadOrigen, String ciudadDestino,
                 String fechaVuelo, String horaSalida, String horaLlegada,
                 int capacidadTotal, double precioBase) {
        this.codigoVuelo         = codigoVuelo;
        this.ciudadOrigen        = ciudadOrigen;
        this.ciudadDestino       = ciudadDestino;
        this.fechaVuelo          = fechaVuelo;
        this.horaSalida          = horaSalida;
        this.horaLlegada         = horaLlegada;
        this.capacidadTotal      = capacidadTotal;
        this.asientosDisponibles = capacidadTotal; // Inicialmente igual a la capacidad total
        this.precioBase          = precioBase;
        this.estadoVuelo         = ESTADO_PROGRAMADO; // Estado inicial
    }

    // ======================== MÉTODO ABSTRACTO (Polimorfismo) ========================
    /**
     * Calcula el precio final del vuelo.
     * Cada subclase implementa su propia lógica de cálculo.
     * POLIMORFISMO: mismo nombre, comportamiento diferente en cada subclase.
     */
    public abstract double calcularPrecioFinal();

    /**
     * Retorna el tipo de vuelo como texto ("Nacional" o "Internacional").
     * POLIMORFISMO: cada subclase retorna su propio tipo.
     */
    public abstract String getTipoVuelo();

    // ======================== MÉTODOS CONCRETOS ========================

    /**
     * Reserva una cantidad de asientos si hay disponibilidad.
     * @return true si la reserva fue exitosa, false si no hay asientos.
     */
    public boolean reservarAsientos(int cantidad) {
        if (cantidad < 1 || cantidad > 5) {
            return false;
        }
        if (asientosDisponibles >= cantidad) {
            asientosDisponibles -= cantidad;
            return true;
        }
        return false;
    }

    /**
     * Devuelve asientos al vuelo cuando se cancela una reserva.
     */
    public void devolverAsientos(int cantidad) {
        asientosDisponibles += cantidad;
        // No puede superar la capacidad total
        if (asientosDisponibles > capacidadTotal) {
            asientosDisponibles = capacidadTotal;
        }
    }

    /**
     * Verifica si el vuelo está en estado "Programado".
     */
    public boolean estaProgramado() {
        return ESTADO_PROGRAMADO.equals(estadoVuelo);
    }

    /**
     * Verifica si hay suficientes asientos disponibles.
     */
    public boolean hayAsientosDisponibles(int cantidad) {
        return asientosDisponibles >= cantidad;
    }

    // ======================== GETTERS ========================
    public String getCodigoVuelo()        { return codigoVuelo; }
    public String getCiudadOrigen()       { return ciudadOrigen; }
    public String getCiudadDestino()      { return ciudadDestino; }
    public String getFechaVuelo()         { return fechaVuelo; }
    public String getHoraSalida()         { return horaSalida; }
    public String getHoraLlegada()        { return horaLlegada; }
    public int    getCapacidadTotal()     { return capacidadTotal; }
    public int    getAsientosDisponibles(){ return asientosDisponibles; }
    public double getPrecioBase()         { return precioBase; }
    public String getEstadoVuelo()        { return estadoVuelo; }

    // ======================== SETTERS ========================
    public void setEstadoVuelo(String estadoVuelo)    { this.estadoVuelo = estadoVuelo; }
    public void setPrecioBase(double precioBase)       { this.precioBase = precioBase; }
    public void setAsientosDisponibles(int asientos)   { this.asientosDisponibles = asientos; }

    // ======================== toString BASE ========================
    /**
     * Información común de cualquier vuelo.
     * Las subclases la usan con super.toString() para extenderla.
     */
    @Override
    public String toString() {
        return "  Código        : " + codigoVuelo                   + "\n" +
               "  Tipo          : " + getTipoVuelo()                + "\n" +
               "  Ruta          : " + ciudadOrigen + " → " + ciudadDestino + "\n" +
               "  Fecha         : " + fechaVuelo                    + "\n" +
               "  Horario       : " + horaSalida + " - " + horaLlegada     + "\n" +
               "  Capacidad     : " + capacidadTotal + " asientos"  + "\n" +
               "  Disponibles   : " + asientosDisponibles + " asientos" + "\n" +
               "  Precio base   : $" + String.format("%,.0f", precioBase)  + "\n" +
               "  Precio final  : $" + String.format("%,.0f", calcularPrecioFinal()) + "\n" +
               "  Estado        : " + estadoVuelo;
    }
}
