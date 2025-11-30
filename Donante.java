public class Donante {

    private String nombre;
    private String cedula;
    private String tipoSangre;
    private int edad;
    private String ciudad;
    private String fecha;

    public Donante(String nombre, String cedula, String tipoSangre,
                   int edad, String ciudad, String fecha) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.tipoSangre = tipoSangre;
        this.edad = edad;
        this.ciudad = ciudad;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return nombre + " | Cédula: " + cedula +
                " | Tipo: " + tipoSangre +
                " | Fecha: " + fecha +
                " | " + ciudad;
    }
}
