public class SolicitudDonacion {

    private String tipoSangre;
    private String prioridad;
    private String nombrePaciente;
    private String cedulaPaciente;
    private String fechaIngreso;

    public SolicitudDonacion(String tipoSangre, String prioridad) {
        this(tipoSangre, prioridad, "", "", "");
    }

    public SolicitudDonacion(String tipoSangre, String prioridad,
                             String nombrePaciente, String cedulaPaciente,
                             String fechaIngreso) {
        this.tipoSangre = tipoSangre;
        this.prioridad = prioridad;
        this.nombrePaciente = nombrePaciente;
        this.cedulaPaciente = cedulaPaciente;
        this.fechaIngreso = fechaIngreso;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public String getCedulaPaciente() {
        return cedulaPaciente;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    @Override
    public String toString() {
        String base = "Tipo: " + tipoSangre + " | Prioridad: " + prioridad;
        if (nombrePaciente == null || nombrePaciente.isEmpty()) {
            return base;
        }
        return nombrePaciente + " | Cédula: " + cedulaPaciente + " | " +
                base + " | Fecha: " + fechaIngreso;
    }
}
