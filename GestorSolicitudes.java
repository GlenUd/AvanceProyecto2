import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GestorSolicitudes {
    private Queue<SolicitudDonacion> colaSolicitudes;
    public GestorSolicitudes() {
        colaSolicitudes = new LinkedList<>();

        colaSolicitudes.add(new SolicitudDonacion(
                "O+", "Alta", "Juan Pérez", "0102030405", "2025-11-30"));
        colaSolicitudes.add(new SolicitudDonacion(
                "A-", "Media", "María López", "1102233445", "2025-11-29"));
        colaSolicitudes.add(new SolicitudDonacion(
                "B+", "Baja", "Carlos Gómez", "0908070605", "2025-11-28"));
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
}
