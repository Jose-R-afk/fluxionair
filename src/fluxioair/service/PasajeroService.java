package fluxioair.service;

import fluxioair.model.Pasajero;
import java.util.ArrayList;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los Pasajeros.
 *
 * Aplica ABSTRACCIÓN: encapsula la lógica de negocio separándola del modelo y la UI.
 * Usa ArrayList para almacenar la colección de pasajeros en memoria.
 */
public class PasajeroService {

    // ArrayList para almacenar todos los pasajeros registrados en el sistema
    private ArrayList<Pasajero> listaPasajeros;

    // ======================== CONSTRUCTOR ========================
    public PasajeroService() {
        this.listaPasajeros = new ArrayList<>();
    }

    // ======================== MÉTODOS DE NEGOCIO ========================

    /**
     * Registra un nuevo pasajero en el sistema después de validar todas las reglas de negocio.
     *
     * @return mensaje de resultado (éxito o error)
     */
    public String registrarPasajero(String cedula, String nombres, String apellidos,
                                     int edad, String email, String telefono,
                                     String pasaporte, String nacionalidad) {
        // Regla: edad >= 0
        if (edad < 0) {
            return "ERROR: La edad no puede ser negativa.";
        }

        // Regla: email debe contener "@"
        if (!email.contains("@")) {
            return "El email debe contener el símbolo @";
        }

        // Regla: no puede existir dos pasajeros con la misma cédula
        if (buscarPorCedula(cedula) != null) {
            return "Ya existe un pasajero con esa cédula";
        }

        // Regla: no pueden existir dos pasajeros con el mismo pasaporte
        if (buscarPorPasaporte(pasaporte) != null) {
            return "Ya existe un pasajero con ese número de pasaporte";
        }

        // Validaciones básicas de campos vacíos
        if (cedula.trim().isEmpty() || nombres.trim().isEmpty() ||
            apellidos.trim().isEmpty() || telefono.trim().isEmpty() || nacionalidad.trim().isEmpty()) {
            return "ERROR: Todos los campos son obligatorios.";
        }

        // Crear y agregar el pasajero
        Pasajero nuevoPasajero = new Pasajero(cedula, nombres, apellidos, edad,
                                               email, telefono, pasaporte, nacionalidad);
        listaPasajeros.add(nuevoPasajero);
        return "EXITO: Pasajero " + nombres + " " + apellidos + " registrado correctamente.";
    }

    /**
     * Busca un pasajero por su número de cédula.
     * @return el Pasajero encontrado, o null si no existe.
     */
    public Pasajero buscarPorCedula(String cedula) {
        for (Pasajero p : listaPasajeros) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Busca un pasajero por su número de pasaporte.
     * @return el Pasajero encontrado, o null si no existe.
     */
    public Pasajero buscarPorPasaporte(String pasaporte) {
        for (Pasajero p : listaPasajeros) {
            if (p.getPasaporte().equals(pasaporte)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Retorna el número total de pasajeros registrados en el sistema.
     */
    public int getTotalPasajeros() {
        return listaPasajeros.size();
    }

    /**
     * Retorna la lista completa de pasajeros.
     */
    public ArrayList<Pasajero> getListaPasajeros() {
        return listaPasajeros;
    }

    /**
     * Muestra todos los pasajeros registrados en el sistema.
     * @return String con la lista de pasajeros o mensaje si no hay ninguno.
     */
    public String listarPasajeros() {
        if (listaPasajeros.isEmpty()) {
            return "No hay pasajeros registrados en el sistema.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════╗\n");
        sb.append("║      LISTA DE PASAJEROS REGISTRADOS  ║\n");
        sb.append("╚══════════════════════════════════════╝\n");
        sb.append("Total: ").append(listaPasajeros.size()).append(" pasajero(s)\n\n");
        for (int i = 0; i < listaPasajeros.size(); i++) {
            sb.append("[").append(i + 1).append("] ");
            Pasajero p = listaPasajeros.get(i);
            sb.append("Cédula: ").append(p.getCedula())
              .append(" | Nombre: ").append(p.getNombreCompleto())
              .append(" | Pasaporte: ").append(p.getPasaporte())
              .append("\n");
        }
        return sb.toString();
    }
}
