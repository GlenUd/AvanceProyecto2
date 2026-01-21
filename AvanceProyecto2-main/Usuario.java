public class Usuario {
    private String correo;
    private String passwordHash; // hash simple
    private String rol; // "COORDINADOR" o "PERSONAL"

    private int intentosFallidos;
    private long bloqueadoHastaMs; // timestamp en ms

    public Usuario(String correo, String passwordHash, String rol) {
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.intentosFallidos = 0;
        this.bloqueadoHastaMs = 0;
    }

    public String getCorreo() { return correo; }
    public String getPasswordHash() { return passwordHash; }
    public String getRol() { return rol; }

    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }

    public long getBloqueadoHastaMs() { return bloqueadoHastaMs; }
    public void setBloqueadoHastaMs(long bloqueadoHastaMs) { this.bloqueadoHastaMs = bloqueadoHastaMs; }

    public boolean estaBloqueado() {
        return System.currentTimeMillis() < bloqueadoHastaMs;
    }

    public long segundosRestantesBloqueo() {
        long diff = bloqueadoHastaMs - System.currentTimeMillis();
        return Math.max(0, diff / 1000);
    }
}
