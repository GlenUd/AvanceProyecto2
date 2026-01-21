import javax.swing.SwingUtilities;

public class Main {

    private static GestorDonantes gestorDonantes = new GestorDonantes();
    private static GestorSolicitudes gestorSolicitudes = new GestorSolicitudes();
    private static GestorUsuarios gestorUsuarios = new GestorUsuarios();

    private static Usuario usuarioActual;

    public static GestorDonantes getGestorDonantes() {
        return gestorDonantes;
    }
    public static GestorSolicitudes getGestorSolicitudes() {
        return gestorSolicitudes;
    }

    public static GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario u) {
        usuarioActual = u;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow login = new LoginWindow();
            login.setVisible(true);
        });
    }
}
