import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private JPanel panel1;
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JLabel lblEstado;

    public LoginWindow() {
        setTitle("Login - LiveShare");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // =========================
        // PANEL PRINCIPAL
        // =========================
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 12, 10, 12);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCorreo = new JLabel("Correo:");
        JLabel lblPass = new JLabel("Contraseña:");

        txtCorreo = new JTextField(22);
        txtPassword = new JPasswordField(22);

        btnIngresar = new JButton("INGRESAR");

        lblEstado = new JLabel(" ");
        lblEstado.setForeground(Color.RED);

        // =========================
        // FILA 0 - CORREO
        // =========================
        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        panel.add(lblCorreo, g);

        g.gridx = 1; g.gridy = 0; g.weightx = 1;
        panel.add(txtCorreo, g);

        // =========================
        // FILA 1 - CONTRASEÑA
        // =========================
        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        panel.add(lblPass, g);

        g.gridx = 1; g.gridy = 1; g.weightx = 1;
        panel.add(txtPassword, g);

        // =========================
        // FILA 2 - BOTÓN
        // =========================
        g.gridx = 0; g.gridy = 2; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        panel.add(btnIngresar, g);

        // =========================
        // FILA 3 - MENSAJES
        // =========================
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.CENTER;
        panel.add(lblEstado, g);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        // =========================
        // ACCIONES
        // =========================
        btnIngresar.addActionListener(e -> login());
        getRootPane().setDefaultButton(btnIngresar);
    }

    private void login() {
        String correo = txtCorreo.getText().trim();
        String pass = new String(txtPassword.getPassword());

        GestorUsuarios.ResultadoLogin r =
                Main.getGestorUsuarios().autenticar(correo, pass);

        if (!r.ok) {
            lblEstado.setText(r.mensaje);
            return;
        }

        // =========================
        // LOGIN CORRECTO
        // =========================
        Main.setUsuarioActual(r.usuario);

        // Abrir Ventana principal
        JFrame frame = new JFrame("LiveShare");
        Ventana v = new Ventana();
        frame.setContentPane(v.getPrincipalPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Cerrar login
        dispose();
    }
}
