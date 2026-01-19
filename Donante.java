import java.util.Objects;

public class Donante {

    private String nombre;
    private String cedula;
    private String tipoSangre;
    private int edad;
    private String ciudad;
    private String fecha;
    private String contacto;

    public Donante(String nombre, String cedula, String tipoSangre, int edad, String ciudad, String fecha) {
        this(nombre, cedula, tipoSangre, edad, ciudad, fecha, "");
    }

    public Donante(String nombre, String cedula, String tipoSangre, int edad, String ciudad, String fecha, String contacto) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.tipoSangre = tipoSangre;
        this.edad = edad;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.contacto = contacto;
    }

    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getTipoSangre() { return tipoSangre; }
    public int getEdad() { return edad; }
    public String getCiudad() { return ciudad; }
    public String getFecha() { return fecha; }
    public String getContacto() { return contacto; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    @Override
    public String toString() {
        String c = (contacto == null) ? "" : contacto.trim();
        if (c.isEmpty()) {
            return nombre + " | Cédula: " + cedula + " | Tipo: " + tipoSangre + " | Edad: " + edad + " | Ciudad: " + ciudad + " | Fecha: " + fecha;
        }
        return nombre + " | Cédula: " + cedula + " | Tipo: " + tipoSangre + " | Edad: " + edad + " | Ciudad: " + ciudad + " | Fecha: " + fecha + " | Contacto: " + c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donante)) return false;
        Donante donante = (Donante) o;
        return Objects.equals(cedula, donante.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }
}
