package fluxioair.service;

import fluxioair.model.Vuelo;
import fluxioair.model.VueloNacional;
import fluxioair.model.VueloInternacional;
import java.util.ArrayList;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los Vuelos.
 *
 * Aplica ABSTRACCIÓN: separa la lógica de negocio del modelo y la interfaz.
 * Aplica POLIMORFISMO: la lista contiene objetos de tipo Vuelo (padre),
 *   pero en tiempo de ejecución pueden ser VueloNacional o VueloInternacional.
 * Usa ArrayList para almacenar la colección de vuelos en memoria.
 */
public class VueloService {

    // ArrayList de tipo Vuelo (POLIMORFISMO: puede contener Nacionales e Internacionales)
    private ArrayList<Vuelo> listaVuelos;

    // ======================== CONSTRUCTOR ========================
    public VueloService() {
        this.listaVuelos = new ArrayList<>();
    }

    // ======================== MÉTODOS DE NEGOCIO ========================

    /**
     * Agrega un vuelo NACIONAL al sistema después de validar las reglas de negocio.
     */
    public String agregarVueloNacional(String codigoVuelo, String ciudadOrigen, String ciudadDestino,
                                        String fechaVuelo, String horaSalida, String horaLlegada,
                                        int capacidadTotal, double precioBase,
                                        double duracionHoras, boolean incluyeAlimentacion) {
        // Validar reglas comunes
        String validacion = validarDatosComunes(codigoVuelo, capacidadTotal, precioBase);
        if (validacion != null) return validacion;

        VueloNacional vuelo = new VueloNacional(codigoVuelo, ciudadOrigen, ciudadDestino,
                fechaVuelo, horaSalida, horaLlegada,
                capacidadTotal, precioBase,
                duracionHoras, incluyeAlimentacion);
        listaVuelos.add(vuelo);
        return "EXITO: Vuelo nacional " + codigoVuelo + " (" + ciudadOrigen + " → " + ciudadDestino + ") registrado correctamente.";
    }

    /**
     * Agrega un vuelo INTERNACIONAL al sistema después de validar las reglas de negocio.
     */
    public String agregarVueloInternacional(String codigoVuelo, String ciudadOrigen, String ciudadDestino,
                                             String fechaVuelo, String horaSalida, String horaLlegada,
                                             int capacidadTotal, double precioBase,
                                             String paisDestino, boolean requiereVisa, double cargoInternacional) {
        // Validar reglas comunes
        String validacion = validarDatosComunes(codigoVuelo, capacidadTotal, precioBase);
        if (validacion != null) return validacion;

        VueloInternacional vuelo = new VueloInternacional(codigoVuelo, ciudadOrigen, ciudadDestino,
                fechaVuelo, horaSalida, horaLlegada,
                capacidadTotal, precioBase,
                paisDestino, requiereVisa, cargoInternacional);
        listaVuelos.add(vuelo);
        return "EXITO: Vuelo internacional " + codigoVuelo + " (" + ciudadOrigen + " → " + ciudadDestino + ") registrado correctamente.";
    }

    /**
     * Valida las reglas de negocio comunes a todos los tipos de vuelo.
     * @return null si todo está bien, o el mensaje de error si hay un problema.
     */
    private String validarDatosComunes(String codigoVuelo, int capacidadTotal, double precioBase) {
        // Regla: no pueden existir dos vuelos con el mismo código
        if (buscarPorCodigo(codigoVuelo) != null) {
            return "Ya existe un vuelo con el código " + codigoVuelo;
        }
        // Regla: capacidad total debe ser mayor a 0
        if (capacidadTotal <= 0) {
            return "ERROR: La capacidad total debe ser mayor a 0.";
        }
        // Regla: precio base debe ser mayor a 0
        if (precioBase <= 0) {
            return "El precio del vuelo debe ser mayor a cero";
        }
        return null; // Sin errores
    }

    /**
     * Busca un vuelo por su código.
     * POLIMORFISMO: retorna tipo Vuelo, pero puede ser Nacional o Internacional.
     * @return el Vuelo encontrado, o null si no existe.
     */
    public Vuelo buscarPorCodigo(String codigoVuelo) {
        for (Vuelo v : listaVuelos) {
            if (v.getCodigoVuelo().equalsIgnoreCase(codigoVuelo)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Cambia el estado de un vuelo existente.
     */
    public String cambiarEstadoVuelo(String codigoVuelo, String nuevoEstado) {
        Vuelo vuelo = buscarPorCodigo(codigoVuelo);
        if (vuelo == null) {
            return "No se encontró el vuelo con código " + codigoVuelo;
        }
        vuelo.setEstadoVuelo(nuevoEstado);
        return "EXITO: Estado del vuelo " + codigoVuelo + " cambiado a '" + nuevoEstado + "'.";
    }

    /**
     * Lista todos los vuelos disponibles (en estado Programado).
     */
    public String listarVuelosProgramados() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════╗\n");
        sb.append("║       VUELOS PROGRAMADOS             ║\n");
        sb.append("╚══════════════════════════════════════╝\n");

        boolean hayVuelos = false;
        for (Vuelo v : listaVuelos) {
            if (v.estaProgramado()) {
                sb.append(v.toString()).append("\n\n");
                hayVuelos = true;
            }
        }
        if (!hayVuelos) {
            sb.append("No hay vuelos programados disponibles.\n");
        }
        return sb.toString();
    }

    /**
     * Lista todos los vuelos del sistema (cualquier estado).
     */
    public String listarTodosLosVuelos() {
        if (listaVuelos.isEmpty()) {
            return "No hay vuelos registrados en el sistema.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════╗\n");
        sb.append("║         TODOS LOS VUELOS             ║\n");
        sb.append("╚══════════════════════════════════════╝\n");
        sb.append("Total: ").append(listaVuelos.size()).append(" vuelo(s)\n\n");
        for (Vuelo v : listaVuelos) {
            sb.append(v.toString()).append("\n\n");
        }
        return sb.toString();
    }

    /**
     * Retorna la lista completa de vuelos.
     */
    public ArrayList<Vuelo> getListaVuelos() {
        return listaVuelos;
    }
}
