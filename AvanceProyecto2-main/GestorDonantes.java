import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestorDonantes {

    private List<Donante> donantes = new ArrayList<>();

    private ArbolBSTDonantes indiceCedula = new ArbolBSTDonantes();

    public GestorDonantes() {
        donantes.add(new Donante("Ana Pérez", "0102030405", "O+", 30, "El Inca", "2025-10-01"));
        donantes.add(new Donante("Luis Gómez", "1102233445", "A-", 40, "Batan", "2025-09-15"));
        donantes.add(new Donante("María Ruiz", "0912345678", "B+", 28, "Carolina Alto", "2025-10-10"));
        donantes.add(new Donante("Carlos Vega", "1723456789", "AB+", 35, "Monteserrin", "2025-08-30"));
        donantes.add(new Donante("Sofía León", "0609876543", "O-", 25, "Iñaquito", "2025-10-05"));

        for (Donante d : donantes) {
            indiceCedula.insertar(d);
        }
    }

    public void agregarDonante(Donante d) {
        if (d != null) {
            donantes.add(d);
            indiceCedula.insertar(d);
        }
    }

    public List<Donante> obtenerDonantes() {
        return new ArrayList<>(donantes);
    }

    public Donante buscarPorCedula(String cedula) {
        return indiceCedula.buscar(cedula);
    }

    public Donante buscarCompatiblePorTipo(String tipo) {
        if (tipo == null) return null;
        for (Donante d : donantes) {
            if (tipo.equalsIgnoreCase(d.getTipoSangre())) {
                return d;
            }
        }
        return null;
    }

    public void ordenarPorTipoSangre() {
        Collections.sort(donantes, Comparator.comparing(Donante::getTipoSangre, String.CASE_INSENSITIVE_ORDER));
    }

    public void ordenarPorCedula() {
        Collections.sort(donantes, Comparator.comparing(Donante::getCedula, String.CASE_INSENSITIVE_ORDER));
    }

    public void ordenarPorFecha() {
        Collections.sort(donantes, Comparator.comparing(Donante::getFecha, String.CASE_INSENSITIVE_ORDER));
    }

    public boolean esCompatible(String donante, String receptor) {
        if (donante == null || receptor == null) return false;

        switch (receptor) {
            case "O-": return donante.equals("O-");
            case "O+": return donante.equals("O-") || donante.equals("O+");
            case "A-": return donante.equals("A-") || donante.equals("O-");
            case "A+": return donante.equals("A+") || donante.equals("A-") || donante.equals("O+") || donante.equals("O-");
            case "B-": return donante.equals("B-") || donante.equals("O-");
            case "B+": return donante.equals("B+") || donante.equals("B-") || donante.equals("O+") || donante.equals("O-");
            case "AB-": return donante.equals("AB-") || donante.equals("A-") || donante.equals("B-") || donante.equals("O-");
            case "AB+": return true;
        }
        return false;
    }
    public String generarReporteDonantesPorTipoConNombres() {
        // Orden fijo de tipos (como el que ya muestras)
        String[] tipos = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};

        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE: Donantes por tipo de sangre (con nombres)\n\n");

        for (String tipo : tipos) {
            List<String> nombres = new ArrayList<>();

            for (Donante d : donantes) {
                if (d != null && d.getTipoSangre() != null &&
                        d.getTipoSangre().equalsIgnoreCase(tipo)) {
                    nombres.add(d.getNombre());
                }
            }

            sb.append(tipo).append(" (").append(nombres.size()).append("): ");

            if (nombres.isEmpty()) {
                sb.append("Sin donantes");
            } else {
                sb.append(String.join(", ", nombres));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
