public class SolicitudDonacion {
        private String tipoSangre;
        private String prioridad;
        private String nombrePaciente;
        private String cedula;
        private String fechaIngreso;

        public SolicitudDonacion(String tipoSangre, String prioridad, String nombrePaciente, String cedula, String fechaIngreso) {
            this.tipoSangre = tipoSangre;
            this.prioridad = prioridad;
            this.nombrePaciente = nombrePaciente;
            this.cedula = cedula;
            this.fechaIngreso = fechaIngreso;
        }

        public SolicitudDonacion(String tipoSangre, String prioridad) {
            this(tipoSangre, prioridad, "Paciente", "0", "");
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

        public String getCedula() {
            return cedula;
        }

        public String getFechaIngreso() {
            return fechaIngreso;
        }

        @Override
        public String toString() {
            return nombrePaciente
                    + " | Cédula: " + cedula
                    + " | Tipo: " + tipoSangre
                    + " | Prioridad: " + prioridad
                    + " | Fecha: " + fechaIngreso;
        }
    }
