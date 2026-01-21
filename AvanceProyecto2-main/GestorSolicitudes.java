import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GestorSolicitudes {

    private Queue<SolicitudDonacion> colaSolicitudes;

    public GestorSolicitudes() {
        colaSolicitudes = new LinkedList<>();

        colaSolicitudes.add(new SolicitudDonacion("O+", "Alta", "Juan Pérez", "0102030405", "2025-11-30"));
        colaSolicitudes.add(new SolicitudDonacion("A-", "Media", "María López", "1102233445", "2025-11-29"));
        colaSolicitudes.add(new SolicitudDonacion("B+", "Baja", "Carlos Gómez", "0908070605", "2025-11-28"));
    }

    public void agregarSolicitud(SolicitudDonacion s) {
        if (s != null) {
            colaSolicitudes.add(s);
        }
    }

    public SolicitudDonacion atenderSiguiente() {
        if (!colaSolicitudes.isEmpty()) {
            return colaSolicitudes.poll();
        }
        return null;
    }

    public boolean haySolicitudes() {
        return !colaSolicitudes.isEmpty();
    }

    public List<SolicitudDonacion> obtenerSolicitudes() {
        return new LinkedList<>(colaSolicitudes);
    }

    public void ordenarPorTipoSangre() {
        List<SolicitudDonacion> lista = new LinkedList<>(colaSolicitudes);
        Collections.sort(lista, Comparator.comparing(SolicitudDonacion::getTipoSangre, String.CASE_INSENSITIVE_ORDER));
        colaSolicitudes.clear();
        colaSolicitudes.addAll(lista);
    }

    public void ordenarPorFechaIngreso() {
        List<SolicitudDonacion> lista = new LinkedList<>(colaSolicitudes);
        Collections.sort(lista, Comparator.comparing(SolicitudDonacion::getFechaIngreso, String.CASE_INSENSITIVE_ORDER));
        colaSolicitudes.clear();
        colaSolicitudes.addAll(lista);
    }

    private int prioridadValor(String p) {
        if (p == null) return 99;
        String x = p.trim().toLowerCase();
        if (x.equals("alta")) return 1;
        if (x.equals("media")) return 2;
        if (x.equals("baja")) return 3;
        return 99;
    }

    public void ordenarPorPrioridad() {
        List<SolicitudDonacion> lista = new LinkedList<>(colaSolicitudes);
        Collections.sort(lista, (a, b) -> Integer.compare(prioridadValor(a.getPrioridad()), prioridadValor(b.getPrioridad())));
        colaSolicitudes.clear();
        colaSolicitudes.addAll(lista);
    }
}
