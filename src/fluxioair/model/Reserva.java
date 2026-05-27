package fluxioair.model;

/**
 * Clase que representa una reserva de boleto en FluxioAir.
 *
 * Aplica ABSTRACCIÓN: modela el concepto de reserva con sus datos esenciales.
 * Aplica ENCAPSULAMIENTO: todos los atributos son privados con getters/setters.
 * Aplica RELACIÓN entre clases: contiene referencias a Pasajero y Vuelo.
 */
public class Reserva {

    // ======================== CONSTANTES DE ESTADO ========================
    public static final String ESTADO_CONFIRMADA  = "Confirmada";
    public static final String ESTADO_CANCELADA   = "Cancelada";
    public static final String ESTADO_COMPLETADA  = "Completada";

    // ======================== ATRIBUTOS PRIVADOS (Encapsulamiento) ========================
    private String   codigoReserva;
    private Pasajero pasajero;          // Relación con clase Pasajero
    private Vuelo    vuelo;             // Relación con clase Vuelo (polimorfismo: puede ser Nacional o Internacional)
    private int      cantidadAsientos;
    private String   fechaReserva;
    private double   precioTotal;
    private String   estadoReserva;

    // ======================== CONSTRUCTOR ========================
    public Reserva(String codigoReserva, Pasajero pasajero, Vuelo vuelo,
                   int cantidadAsientos, String fechaReserva) {
        this.codigoReserva    = codigoReserva;
        this.pasajero         = pasajero;
        this.vuelo            = vuelo;
        this.cantidadAsientos = cantidadAsientos;
        this.fechaReserva     = fechaReserva;
        // Precio total = precio final del vuelo × cantidad de asientos
        // POLIMORFISMO: calcularPrecioFinal() ejecuta la versión correcta (Nacional o Internacional)
        this.precioTotal      = vuelo.calcularPrecioFinal() * cantidadAsientos;
        this.estadoReserva    = ESTADO_CONFIRMADA; // Estado inicial
    }

    // ======================== MÉTODOS ========================

    /**
     * Cancela la reserva si está en estado Confirmada.
     * @return true si se canceló exitosamente, false si no se puede cancelar.
     */
    public boolean cancelar() {
        if (ESTADO_CONFIRMADA.equals(estadoReserva)) {
            estadoReserva = ESTADO_CANCELADA;
            // Devolver asientos al vuelo
            vuelo.devolverAsientos(cantidadAsientos);
            return true;
        }
        return false;
    }

    /**
     * Verifica si la reserva está confirmada.
     */
    public boolean estaConfirmada() {
        return ESTADO_CONFIRMADA.equals(estadoReserva);
    }

    // ======================== GETTERS ========================
    public String   getCodigoReserva()    { return codigoReserva; }
    public Pasajero getPasajero()         { return pasajero; }
    public Vuelo    getVuelo()            { return vuelo; }
    public int      getCantidadAsientos() { return cantidadAsientos; }
    public String   getFechaReserva()     { return fechaReserva; }
    public double   getPrecioTotal()      { return precioTotal; }
    public String   getEstadoReserva()    { return estadoReserva; }

    // ======================== SETTERS ========================
    public void setEstadoReserva(String estadoReserva) { this.estadoReserva = estadoReserva; }

    // ======================== toString ========================
    @Override
    public String toString() {
        return "╔══════════════════════════════════════════════╗\n" +
               "║             DETALLE DE RESERVA              ║\n" +
               "╠══════════════════════════════════════════════╣\n" +
               "║ Código reserva  : " + codigoReserva                    + "\n" +
               "║ Estado          : " + estadoReserva                    + "\n" +
               "║ Fecha reserva   : " + fechaReserva                     + "\n" +
               "║ Pasajero        : " + pasajero.getNombreCompleto()     + "\n" +
               "║ Cédula          : " + pasajero.getCedula()             + "\n" +
               "║ Vuelo           : " + vuelo.getCodigoVuelo()           + "\n" +
               "║ Ruta            : " + vuelo.getCiudadOrigen() + " → " + vuelo.getCiudadDestino() + "\n" +
               "║ Tipo vuelo      : " + vuelo.getTipoVuelo()             + "\n" +
               "║ Fecha vuelo     : " + vuelo.getFechaVuelo()            + "\n" +
               "║ Asientos        : " + cantidadAsientos                 + "\n" +
               "║ Precio x asiento: $" + String.format("%,.0f", vuelo.calcularPrecioFinal()) + "\n" +
               "║ PRECIO TOTAL    : $" + String.format("%,.0f", precioTotal) + "\n" +
               "╚══════════════════════════════════════════════╝";
    }
}
