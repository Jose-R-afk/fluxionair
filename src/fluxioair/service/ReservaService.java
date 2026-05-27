package fluxioair.service;

import fluxioair.model.Pasajero;
import fluxioair.model.Reserva;
import fluxioair.model.Vuelo;
import java.util.ArrayList;

/**
 * Servicio que gestiona la lógica de negocio relacionada con las Reservas.
 *
 * Aplica ABSTRACCIÓN: encapsula el proceso completo de reserva/cancelación.
 * Aplica POLIMORFISMO: trabaja con objetos Vuelo que pueden ser Nacional o Internacional,
 *   llamando a calcularPrecioFinal() que se ejecuta polimórficamente.
 * Usa ArrayList para almacenar la colección de reservas en memoria.
 */
public class ReservaService {

    // ArrayList para almacenar todas las reservas del sistema
    private ArrayList<Reserva> listaReservas;

    // Servicios necesarios para buscar pasajeros y vuelos
    private PasajeroService pasajeroService;
    private VueloService    vueloService;

    // Contador automático para generar códigos de reserva
    private int contadorReservas;

    // ======================== CONSTRUCTOR ========================
    public ReservaService(PasajeroService pasajeroService, VueloService vueloService) {
        this.listaReservas    = new ArrayList<>();
        this.pasajeroService  = pasajeroService;
        this.vueloService     = vueloService;
        this.contadorReservas = 1;
    }

    // ======================== MÉTODOS DE NEGOCIO ========================

    /**
     * Crea una nueva reserva para un pasajero en un vuelo específico.
     * Sigue el proceso definido en el parcial paso a paso.
     *
     * @param cedulaPasajero  Cédula del pasajero que realiza la reserva
     * @param codigoVuelo     Código del vuelo a reservar
     * @param cantidadAsientos Cantidad de asientos a reservar (1-5)
     * @param fechaReserva    Fecha en que se realiza la reserva
     * @return mensaje de resultado (éxito o descripción del error)
     */
    public String crearReserva(String cedulaPasajero, String codigoVuelo,
                                int cantidadAsientos, String fechaReserva) {

        // Paso 1: Verificar que el pasajero existe
        Pasajero pasajero = pasajeroService.buscarPorCedula(cedulaPasajero);
        if (pasajero == null) {
            return "No se encontró el pasajero con esa cédula";
        }

        // Paso 2: Verificar que el vuelo existe y está en estado "Programado"
        Vuelo vuelo = vueloService.buscarPorCodigo(codigoVuelo);
        if (vuelo == null) {
            return "No se encontró el vuelo con código " + codigoVuelo;
        }
        if (!vuelo.estaProgramado()) {
            return "No se puede reservar en un vuelo que no esté en estado 'Programado'. Estado actual: " + vuelo.getEstadoVuelo();
        }

        // Paso 3: Validar cantidad de asientos (1 a 5)
        if (cantidadAsientos < 1 || cantidadAsientos > 5) {
            return "No se pueden reservar más de 5 asientos por reserva";
        }

        // Paso 4: Verificar que hay suficientes asientos disponibles
        if (!vuelo.hayAsientosDisponibles(cantidadAsientos)) {
            return "No hay asientos disponibles para este vuelo";
        }

        // Paso 5: Generar código de reserva único
        String codigoReserva = generarCodigoReserva();

        // Paso 6: Crear la reserva
        Reserva nuevaReserva = new Reserva(codigoReserva, pasajero, vuelo,
                                            cantidadAsientos, fechaReserva);

        // Paso 7: Reducir asientos disponibles del vuelo
        vuelo.reservarAsientos(cantidadAsientos);

        // Paso 8: Guardar la reserva en la lista
        listaReservas.add(nuevaReserva);

        return "EXITO: Reserva creada exitosamente.\n" + nuevaReserva.toString();
    }

    /**
     * Cancela una reserva existente por su código.
     * Devuelve los asientos al vuelo automáticamente.
     *
     * @param codigoReserva Código único de la reserva a cancelar
     * @return mensaje de resultado
     */
    public String cancelarReserva(String codigoReserva) {
        // Paso 1: Buscar la reserva
        Reserva reserva = buscarPorCodigo(codigoReserva);
        if (reserva == null) {
            return "No se encontró la reserva con ese código";
        }

        // Paso 2: Verificar que la reserva está confirmada
        if (!reserva.estaConfirmada()) {
            return "La reserva " + codigoReserva + " no puede cancelarse. Estado actual: " + reserva.getEstadoReserva();
        }

        // Paso 3: Cancelar la reserva (internamente cambia estado y devuelve asientos)
        reserva.cancelar();

        return "EXITO: Reserva " + codigoReserva + " cancelada correctamente.\n" +
               "Asientos devueltos al vuelo " + reserva.getVuelo().getCodigoVuelo() + ": " +
               reserva.getCantidadAsientos() + " asiento(s).\n" +
               "Asientos disponibles ahora: " + reserva.getVuelo().getAsientosDisponibles();
    }

    /**
     * Busca una reserva por su código único.
     * @return la Reserva encontrada, o null si no existe.
     */
    public Reserva buscarPorCodigo(String codigoReserva) {
        for (Reserva r : listaReservas) {
            if (r.getCodigoReserva().equalsIgnoreCase(codigoReserva)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Consulta y muestra toda la información de una reserva por su código.
     * Estadística 1: Reservas por código.
     */
    public String consultarReservaPorCodigo(String codigoReserva) {
        Reserva reserva = buscarPorCodigo(codigoReserva);
        if (reserva == null) {
            return "No se encontró la reserva con ese código";
        }
        return reserva.toString();
    }

    /**
     * Consulta todas las reservas de un pasajero específico por su cédula.
     * Estadística 3: Reservas por pasajero.
     */
    public String consultarReservasPorPasajero(String cedulaPasajero) {
        Pasajero pasajero = pasajeroService.buscarPorCedula(cedulaPasajero);
        if (pasajero == null) {
            return "No se encontró el pasajero con esa cédula";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════════════╗\n");
        sb.append("║       RESERVAS DEL PASAJERO                 ║\n");
        sb.append("╠══════════════════════════════════════════════╣\n");
        sb.append("║ Pasajero: ").append(pasajero.getNombreCompleto()).append("\n");
        sb.append("║ Cédula  : ").append(pasajero.getCedula()).append("\n");
        sb.append("╚══════════════════════════════════════════════╝\n");

        int total = 0;
        for (Reserva r : listaReservas) {
            if (r.getPasajero().getCedula().equals(cedulaPasajero)) {
                sb.append(r.toString()).append("\n");
                total++;
            }
        }
        if (total == 0) {
            sb.append("Este pasajero no tiene reservas registradas.\n");
        } else {
            sb.append("Total de reservas: ").append(total).append("\n");
        }
        return sb.toString();
    }

    /**
     * Genera un código de reserva único automáticamente.
     * Formato: R001, R002, R003...
     */
    private String generarCodigoReserva() {
        String codigo;
        do {
            codigo = String.format("R%03d", contadorReservas);
            contadorReservas++;
        } while (buscarPorCodigo(codigo) != null);
        return codigo;
    }

    /**
     * Retorna el número total de pasajeros registrados (Estadística 2).
     */
    public int getTotalPasajerosRegistrados() {
        return pasajeroService.getTotalPasajeros();
    }

    /**
     * Retorna la lista completa de reservas.
     */
    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    /**
     * Lista todas las reservas del sistema.
     */
    public String listarTodasLasReservas() {
        if (listaReservas.isEmpty()) {
            return "No hay reservas registradas en el sistema.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════════════╗\n");
        sb.append("║         TODAS LAS RESERVAS                  ║\n");
        sb.append("╚══════════════════════════════════════════════╝\n");
        sb.append("Total: ").append(listaReservas.size()).append(" reserva(s)\n\n");
        for (Reserva r : listaReservas) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }
}
