package fluxioair.model;

/**
 * Clase que representa un pasajero de la aerolínea FluxioAir.
 * Aplica ENCAPSULAMIENTO con atributos privados y getters/setters.
 * Aplica ABSTRACCIÓN modelando solo los datos relevantes del negocio.
 */
public class Pasajero {

    // ======================== ATRIBUTOS PRIVADOS (Encapsulamiento) ========================
    private String cedula;
    private String nombres;
    private String apellidos;
    private int edad;
    private String email;
    private String telefono;
    private String pasaporte;
    private String nacionalidad;

    // ======================== CONSTRUCTOR ========================
    public Pasajero(String cedula, String nombres, String apellidos, int edad,
                    String email, String telefono, String pasaporte, String nacionalidad) {
        this.cedula      = cedula;
        this.nombres     = nombres;
        this.apellidos   = apellidos;
        this.edad        = edad;
        this.email       = email;
        this.telefono    = telefono;
        this.pasaporte   = pasaporte;
        this.nacionalidad = nacionalidad;
    }

    // ======================== GETTERS ========================
    public String getCedula()       { return cedula; }
    public String getNombres()      { return nombres; }
    public String getApellidos()    { return apellidos; }
    public int    getEdad()         { return edad; }
    public String getEmail()        { return email; }
    public String getTelefono()     { return telefono; }
    public String getPasaporte()    { return pasaporte; }
    public String getNacionalidad() { return nacionalidad; }

    // ======================== SETTERS ========================
    public void setCedula(String cedula)           { this.cedula = cedula; }
    public void setNombres(String nombres)         { this.nombres = nombres; }
    public void setApellidos(String apellidos)     { this.apellidos = apellidos; }
    public void setEdad(int edad)                  { this.edad = edad; }
    public void setEmail(String email)             { this.email = email; }
    public void setTelefono(String telefono)       { this.telefono = telefono; }
    public void setPasaporte(String pasaporte)     { this.pasaporte = pasaporte; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    // ======================== MÉTODOS ========================

    /**
     * Retorna el nombre completo del pasajero.
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    /**
     * Representación textual del pasajero para mostrar al usuario.
     */
    @Override
    public String toString() {
        return "╔══════════════════════════════════════╗\n" +
               "║         DATOS DEL PASAJERO           ║\n" +
               "╠══════════════════════════════════════╣\n" +
               "║ Cédula       : " + cedula            + "\n" +
               "║ Nombre       : " + getNombreCompleto()+ "\n" +
               "║ Edad         : " + edad + " años"    + "\n" +
               "║ Email        : " + email             + "\n" +
               "║ Teléfono     : " + telefono          + "\n" +
               "║ Pasaporte    : " + pasaporte         + "\n" +
               "║ Nacionalidad : " + nacionalidad      + "\n" +
               "╚══════════════════════════════════════╝";
    }
}
