
public class Donante {
        private String nombre;
        private String cedula;
        private String tipoSangre;
        private int edad;
        private String ciudad;
        private String fechaRegistro;

        public Donante(String nombre, String cedula, String tipoSangre, int edad, String ciudad, String fechaRegistro) {
            this.nombre = nombre;
            this.cedula = cedula;
            this.tipoSangre = tipoSangre;
            this.edad = edad;
            this.ciudad = ciudad;
            this.fechaRegistro = fechaRegistro;
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

        public String getFechaRegistro() {
            return fechaRegistro;
        }

        @Override
        public String toString() {
            return nombre + " | " + tipoSangre + " | Cédula: " + cedula +
                    " | Fecha: " + fechaRegistro + " | " + ciudad;
        }
    }
