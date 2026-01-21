import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class GestorUsuarios {

    private final Map<String, Usuario> usuarios = new HashMap<>();

    public GestorUsuarios() {
        // ✅ Usuarios de ejemplo (cámbialos luego)
        // contraseña en texto: "Admin1234" y "Personal123"
        usuarios.put("admin@liveshare.com", new Usuario("admin@liveshare.com", hash("Admin1234"), "COORDINADOR"));
        usuarios.put("personal@liveshare.com", new Usuario("personal@liveshare.com", hash("Personal123"), "PERSONAL"));
    }

    // ✅ Regla del PDF: min 8, 1 mayúscula, 1 número
    public boolean passwordFuerte(String pass) {
        if (pass == null) return false;
        if (pass.length() < 8) return false;

        boolean mayus = false, num = false;
        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) mayus = true;
            if (Character.isDigit(c)) num = true;
        }
        return mayus && num;
    }

    public Usuario getUsuario(String correo) {
        if (correo == null) return null;
        return usuarios.get(correo.trim().toLowerCase());
    }

    public ResultadoLogin autenticar(String correo, String passwordPlano) {
        if (correo == null || passwordPlano == null) {
            return ResultadoLogin.error("Ingrese correo y contraseña.");
        }

        String c = correo.trim().toLowerCase();
        Usuario u = usuarios.get(c);

        if (u == null) {
            return ResultadoLogin.error("Usuario no encontrado.");
        }

        if (u.estaBloqueado()) {
            return ResultadoLogin.error("Usuario bloqueado. Intente en " + u.segundosRestantesBloqueo() + "s.");
        }

        String hashIngresado = hash(passwordPlano);
        if (hashIngresado.equals(u.getPasswordHash())) {
            // ✅ éxito: reinicia intentos
            u.setIntentosFallidos(0);
            u.setBloqueadoHastaMs(0);
            return ResultadoLogin.ok(u);
        } else {
            // ❌ fallo: aumenta intentos
            int intentos = u.getIntentosFallidos() + 1;
            u.setIntentosFallidos(intentos);

            if (intentos >= 3) {
                long cincoMin = 5 * 60 * 1000L;
                u.setBloqueadoHastaMs(System.currentTimeMillis() + cincoMin);
                u.setIntentosFallidos(0); // reinicia conteo después de bloquear
                return ResultadoLogin.error("3 intentos fallidos. Usuario bloqueado 5 minutos.");
            }

            return ResultadoLogin.error("Contraseña incorrecta. Intentos restantes: " + (3 - intentos));
        }
    }

    // Hash SHA-256 simple (suficiente para el proyecto)
    public String hash(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] dig = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : dig) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // fallback
            return Integer.toString(texto.hashCode());
        }
    }

    // ✅ Clase interna para devolver resultado limpio
    public static class ResultadoLogin {
        public final boolean ok;
        public final String mensaje;
        public final Usuario usuario;

        private ResultadoLogin(boolean ok, String mensaje, Usuario usuario) {
            this.ok = ok;
            this.mensaje = mensaje;
            this.usuario = usuario;
        }

        public static ResultadoLogin ok(Usuario u) {
            return new ResultadoLogin(true, "OK", u);
        }

        public static ResultadoLogin error(String msg) {
            return new ResultadoLogin(false, msg, null);
        }
    }
}
