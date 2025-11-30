import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestorDonantes {
        private ArrayList<Donante> donantes;

        public GestorDonantes() {
            donantes = new ArrayList<>();
            donantes.add(new Donante("Ana Pérez", "0102030405", "O+", 25, "Quito", "2025-10-01"));
            donantes.add(new Donante("Luis Gómez", "1102233445", "A-", 32, "Guayaquil", "2025-09-15"));
            donantes.add(new Donante("María Ruiz", "0912345678", "B+", 28, "Cuenca", "2025-10-10"));
            donantes.add(new Donante("Carlos Vega", "1723456789", "AB+", 22, "Ambato", "2025-08-30"));
            donantes.add(new Donante("Sofía León", "0609876543", "O-", 30, "Quito", "2025-10-05"));
        }

        public void agregarDonante(Donante d) {
            if (d != null) {
                donantes.add(d);
            }
        }

        public List<Donante> obtenerDonantes() {
            return donantes;
        }

        public Donante buscarCompatiblePorTipo(String tipoSangre) {
            for (Donante d : donantes) {
                if (d.getTipoSangre().equalsIgnoreCase(tipoSangre)) {
                    return d;
                }
            }
            return null;
        }


        public void ordenarPorTipoSangre() {
            Collections.sort(donantes, new Comparator<Donante>() {
                @Override
                public int compare(Donante o1, Donante o2) {
                    return o1.getTipoSangre().compareToIgnoreCase(o2.getTipoSangre());
                }
            });
        }

        public void ordenarPorCedula() {
            Collections.sort(donantes, new Comparator<Donante>() {
                @Override
                public int compare(Donante o1, Donante o2) {
                    return o1.getCedula().compareToIgnoreCase(o2.getCedula());
                }
            });
        }

        public void ordenarPorFecha() {
            Collections.sort(donantes, new Comparator<Donante>() {
                @Override
                public int compare(Donante o1, Donante o2) {
                    return o1.getFechaRegistro().compareToIgnoreCase(o2.getFechaRegistro());
                }
            });
        }
    }

