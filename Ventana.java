import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Ventana extends JFrame {
    private JPanel Principal;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtFecha;
    private JComboBox<String> cbTipoSandre;
    private JSpinner spEdad;
    private JComboBox<String> cbCiudad;
    private JButton btnAgregarDonanteActionPerformed;
    private JComboBox<String> cbTipoSolicitud;
    private JComboBox<String> cbPrioridad;
    private JButton btnAgregarSolicitud;
    private JButton btnAtenderSolicitud;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JList<String> listaSolicitudes;
    private JList<String> lstDonantes;
    private JList<String> lstSolicitudes;
    private JButton btnSangre;
    private JButton btnCedula;
    private JButton btnFecha;
    private JButton btnEditarSolicitud;
    private JButton btnBuscarTipoSangre;
    private JButton btnEliminar;
    private DefaultListModel<String> modeloDonantes;
    private DefaultListModel<String> modeloSolicitudes;
    private int solicitudEditando = -1;

    public Ventana() {
        setTitle("LiveShare - Red de donación de sangre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        modeloDonantes = new DefaultListModel<>();
        if (lstDonantes != null) {
            lstDonantes.setModel(modeloDonantes);
        }

        modeloSolicitudes = new DefaultListModel<>();
        if (listaSolicitudes != null) {
            listaSolicitudes.setModel(modeloSolicitudes);
        }
        if (lstSolicitudes != null) {
            lstSolicitudes.setModel(modeloSolicitudes);
        }

        actualizarListaDonantes();
        actualizarListaSolicitudes();

        if (btnAgregarDonanteActionPerformed != null)
            btnAgregarDonanteActionPerformed.addActionListener(this::btnAgregarDonanteActionPerformed);

        if (btnAgregarSolicitud != null)
            btnAgregarSolicitud.addActionListener(this::btnAgregarSolicitudActionPerformed);

        if (btnAtenderSolicitud != null)
            btnAtenderSolicitud.addActionListener(this::btnAtenderSolicitudActionPerformed);

        if (btnSangre != null)
            btnSangre.addActionListener(this::btnSangreActionPerformed);

        if (btnCedula != null)
            btnCedula.addActionListener(this::btnCedulaActionPerformed);

        if (btnFecha != null)
            btnFecha.addActionListener(this::btnFechaActionPerformed);


        btnEditarSolicitud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (solicitudEditando == -1) {

                    int index = listaSolicitudes.getSelectedIndex(); // ← ESTA ES LA LISTA VISIBLE

                    if (index == -1) {
                        JOptionPane.showMessageDialog(null,
                                "Seleccione una solicitud para editar.",
                                "Sin selección",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    solicitudEditando = index;

                    // Obtener solicitud desde el gestor
                    SolicitudDonacion s =
                            Main.getGestorSolicitudes().obtenerSolicitudes().get(index);

                    // Cargar los datos EN TU FORMULARIO REAL
                    textField1.setText(s.getNombrePaciente());
                    textField2.setText(s.getCedulaPaciente());
                    textField3.setText(s.getFechaIngreso());

                    cbTipoSolicitud.setSelectedItem(s.getTipoSangre());
                    cbPrioridad.setSelectedItem(s.getPrioridad());

                    JOptionPane.showMessageDialog(null,
                            "Modo edición activado.\nModifique los datos y vuelva a presionar EDITAR SOLICITUD para guardar.",
                            "Edición iniciada",
                            JOptionPane.INFORMATION_MESSAGE);

                    return; // fase 1 termina
                }

                // =====================================================
                // FASE 2 → Guardar los cambios
                // =====================================================
                SolicitudDonacion s =
                        Main.getGestorSolicitudes().obtenerSolicitudes().get(solicitudEditando);

                // Guardar los datos modificados
                s.setNombrePaciente(textField1.getText().trim());
                s.setCedulaPaciente(textField2.getText().trim());
                s.setFechaIngreso(textField3.getText().trim());
                s.setTipoSangre((String) cbTipoSolicitud.getSelectedItem());
                s.setPrioridad((String) cbPrioridad.getSelectedItem());

                actualizarListaSolicitudes();

                JOptionPane.showMessageDialog(null,
                        "La solicitud ha sido actualizada correctamente.",
                        "Edición completada",
                        JOptionPane.INFORMATION_MESSAGE);

                solicitudEditando = -1;  // salir del modo edición
            }
        });
        btnBuscarTipoSangre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = listaSolicitudes.getSelectedIndex();

                if (index == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Seleccione una solicitud para verificar compatibilidad.",
                            "Sin selección",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Obtener la solicitud seleccionada
                SolicitudDonacion s = Main.getGestorSolicitudes()
                        .obtenerSolicitudes().get(index);

                String tipoReceptor = s.getTipoSangre();

                // Buscar donantes compatibles
                List<Donante> lista = Main.getGestorDonantes().obtenerDonantes();
                StringBuilder compatibles = new StringBuilder();

                int contador = 0;
                for (Donante d : lista) {
                    if (Main.getGestorDonantes().esCompatible(d.getTipoSangre(), tipoReceptor)) {
                        contador++;
                        compatibles.append("• ").append(d.getNombre())
                                .append(" | Tipo: ").append(d.getTipoSangre())
                                .append("\n");
                    }
                }

                if (contador == 0) {
                    JOptionPane.showMessageDialog(null,
                            "No existen donantes compatibles para el tipo solicitado: " + tipoReceptor,
                            "Sin compatibilidad",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null,
                        "Donantes compatibles con " + tipoReceptor + ":\n\n" +
                                compatibles.toString() +
                                "\nTotal: " + contador,
                        "Compatibilidad encontrada",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public JPanel getPrincipalPanel() {
        return Principal;
    }

    private void actualizarListaDonantes() {
        modeloDonantes.clear();
        List<Donante> lista = Main.getGestorDonantes().obtenerDonantes();
        for (Donante d : lista) {
            modeloDonantes.addElement(d.toString());
        }
    }

    private void actualizarListaSolicitudes() {
        modeloSolicitudes.clear();
        List<SolicitudDonacion> lista = Main.getGestorSolicitudes().obtenerSolicitudes();
        for (SolicitudDonacion s : lista) {
            modeloSolicitudes.addElement(s.toString());
        }
    }


    private void btnAgregarDonanteActionPerformed(ActionEvent evt) {

        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        int    edad   = (Integer) spEdad.getValue();
        String fecha  = txtFecha.getText();
        String tipo   = (String) cbTipoSandre.getSelectedItem();
        String ciudad = (String) cbCiudad.getSelectedItem();

        if (nombre.isEmpty() || cedula.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar Nombre, Cédula y Fecha.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Donante d = new Donante(nombre, cedula, tipo, edad, ciudad, fecha);
        Main.getGestorDonantes().agregarDonante(d);
        txtNombre.setText("");
        txtCedula.setText("");
        txtFecha.setText("");
        actualizarListaDonantes();
    }

    private void btnAgregarSolicitudActionPerformed(ActionEvent evt) {
        String tipo          = (String) cbTipoSolicitud.getSelectedItem();
        String prioridad     = (String) cbPrioridad.getSelectedItem();
        String nombrePac     = textField1.getText().trim();
        String cedula        = textField2.getText().trim();
        String fechaIngreso  = textField3.getText().trim();


        if (nombrePac.isEmpty() || cedula.isEmpty() || fechaIngreso.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar Nombre del paciente, Cédula y Fecha de ingreso.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "La cédula solo debe contener números.",
                    "Cédula inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cedula.length() == 0 || cedula.length() > 10) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un número mayor a 0 y de máximo 10 dígitos.",
                    "Cédula inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (Long.parseLong(cedula) <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un número mayor a 0 y de máximo 10 dígitos.",
                    "Cédula inválida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        SolicitudDonacion s = new SolicitudDonacion(tipo, prioridad, nombrePac, cedula, fechaIngreso);
        Main.getGestorSolicitudes().agregarSolicitud(s);
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        actualizarListaSolicitudes();
    }

    private void btnAtenderSolicitudActionPerformed(ActionEvent evt) {
        SolicitudDonacion s = Main.getGestorSolicitudes().atenderSiguiente();

        if (s == null) {
            JOptionPane.showMessageDialog(this,
                    "No hay solicitudes en la cola.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Donante encontrado = Main.getGestorDonantes().buscarCompatiblePorTipo(s.getTipoSangre());

        if (encontrado != null) {
            JOptionPane.showMessageDialog(this,
                    "Solicitud atendida con el donante: " + encontrado.getNombre(),
                    "Solicitud atendida", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró un donante compatible.",
                    "Sin coincidencias", JOptionPane.WARNING_MESSAGE);
        }

        actualizarListaSolicitudes();
    }

    private void btnSangreActionPerformed(ActionEvent evt) {
        Main.getGestorDonantes().ordenarPorTipoSangre();
        actualizarListaDonantes();
    }

    private void btnCedulaActionPerformed(ActionEvent evt) {
        Main.getGestorDonantes().ordenarPorCedula();
        actualizarListaDonantes();
    }

    private void btnFechaActionPerformed(ActionEvent evt) {
        Main.getGestorDonantes().ordenarPorFecha();
        actualizarListaDonantes();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        Ventana v = new Ventana();
        frame.setContentPane(v.getPrincipalPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}