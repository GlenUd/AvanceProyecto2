import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestorDonantes {
    private List<Donante> donantes = new ArrayList<>();
    public GestorDonantes() {
        donantes.add(new Donante("Ana Pérez",  "0102030405", "O+", 30, "El Inca",      "2025-10-01"));
        donantes.add(new Donante("Luis Gómez", "1102233445", "A-", 40, "Batan", "2025-09-15"));
        donantes.add(new Donante("María Ruiz","0912345678", "B+", 28, "Carolina Alto",     "2025-10-10"));
        donantes.add(new Donante("Carlos Vega","1723456789", "AB+",35,"Monteserrin",     "2025-08-30"));
        donantes.add(new Donante("Sofía León","0609876543", "O-", 25, "Iñaquito",      "2025-10-05"));
    }

    public void agregarDonante(Donante d) {
        if (d != null) {
            donantes.add(d);
        }
    }

    public List<Donante> obtenerDonantes() {
        return new ArrayList<>(donantes);
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
        Collections.sort(donantes,
                Comparator.comparing(Donante::getTipoSangre));
    }

    public void ordenarPorCedula() {
        Collections.sort(donantes,
                Comparator.comparing(Donante::getCedula));
    }

    public void ordenarPorFecha() {
        Collections.sort(donantes,
                Comparator.comparing(Donante::getFecha));
    }
}
