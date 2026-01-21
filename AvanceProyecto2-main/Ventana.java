import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private JComboBox cboFiltrarSangre;

    private JButton btnReporteDonantesPorTipo;
    private JTextArea textAreaReporte;
    private JButton btnExportarReporte;

    private JButton btnFiltrado;

    private JTextField txtContacto;

    private JTable tblSolicitudes;
    private JTable tblDonantes;

    private JComboBox cboOrden;
    private JButton btnEliminar;

    private DefaultListModel<String> modeloDonantes;
    private DefaultListModel<String> modeloSolicitudes;

    private DefaultTableModel modeloTablaDonantes;
    private DefaultTableModel modeloTablaSolicitudes;
    private int solicitudEditando = -1;

    private JList<String> listDonantes;
    private JList<String> listSolicitudes;
    private JComboBox<String> comboBox1;
    private JButton btnComparaciones;
    private JTextField txtBuscarCedulaLista;
    private JButton btnBuscarCedulaLista;
    private JButton btnMostrarTodos;

    private DefaultListModel<String> modeloListDonantesComparacion;
    private DefaultListModel<String> modeloListSolicitudesComparacion;

    private String normalizarTexto(String texto) {
        if (texto == null) return "";
        texto = texto.toLowerCase();
        texto = texto.replace("á", "a");
        texto = texto.replace("é", "e");
        texto = texto.replace("í", "i");
        texto = texto.replace("ó", "o");
        texto = texto.replace("ú", "u");
        return texto.trim();
    }

    // =========================
    // ✅ NUEVO: Reporte con nombres
    // =========================
    private String generarReportePorTipoConNombres() {
        String[] tipos = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};

        List<Donante> lista = Main.getGestorDonantes().obtenerDonantes();
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE: Donantes por tipo de sangre (con nombres)\n\n");

        for (String tipo : tipos) {
            List<String> nombres = new ArrayList<>();

            for (Donante d : lista) {
                if (d != null && d.getTipoSangre() != null &&
                        d.getTipoSangre().equalsIgnoreCase(tipo)) {
                    nombres.add(d.getNombre());
                }
            }

            sb.append(tipo)
                    .append(" (").append(nombres.size()).append("): ");

            if (nombres.isEmpty()) {
                sb.append("Sin donantes");
            } else {
                sb.append(String.join(", ", nombres));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // =========================
    // ✅ NUEVO: Restricción de botones por rol
    // =========================
    private void aplicarRestriccionesPorRol() {
        Usuario u = Main.getUsuarioActual();
        if (u == null) return;

        // Si el rol es PERSONAL → restringir REPORTE y EXPORTAR
        if ("PERSONAL".equalsIgnoreCase(u.getRol())) {
            if (btnReporteDonantesPorTipo != null) {
                btnReporteDonantesPorTipo.setEnabled(false);
                btnReporteDonantesPorTipo.setToolTipText("Solo administradores pueden generar reportes.");
            }
            if (btnExportarReporte != null) {
                btnExportarReporte.setEnabled(false);
                btnExportarReporte.setToolTipText("Solo administradores pueden exportar reportes.");
            }
        }
    }

    public Ventana() {
        setTitle("LiveShare - Red de donación de sangre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modeloDonantes = new DefaultListModel<>();
        if (lstDonantes != null) lstDonantes.setModel(modeloDonantes);

        modeloSolicitudes = new DefaultListModel<>();
        if (listaSolicitudes != null) listaSolicitudes.setModel(modeloSolicitudes);
        if (lstSolicitudes != null) lstSolicitudes.setModel(modeloSolicitudes);

        inicializarTablas();

        modeloListDonantesComparacion = new DefaultListModel<>();
        if (listDonantes != null) listDonantes.setModel(modeloListDonantesComparacion);

        modeloListSolicitudesComparacion = new DefaultListModel<>();
        if (listSolicitudes != null) listSolicitudes.setModel(modeloListSolicitudesComparacion);

        actualizarListaDonantes();
        actualizarListaSolicitudes();
        actualizarTablaDonantes();
        actualizarTablaSolicitudes();

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

        if (btnComparaciones != null)
            btnComparaciones.addActionListener(e -> actualizarComparacionesPorTipoExacto());

        if (btnEditarSolicitud != null) {
            btnEditarSolicitud.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (solicitudEditando == -1) {
                        int index = (listaSolicitudes != null) ? listaSolicitudes.getSelectedIndex() : -1;
                        if (index == -1) {
                            JOptionPane.showMessageDialog(null, "Seleccione una solicitud para editar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        solicitudEditando = index;

                        SolicitudDonacion s = Main.getGestorSolicitudes().obtenerSolicitudes().get(index);

                        textField1.setText(s.getNombrePaciente());
                        textField2.setText(s.getCedulaPaciente());
                        textField3.setText(s.getFechaIngreso());
                        cbTipoSolicitud.setSelectedItem(s.getTipoSangre());
                        cbPrioridad.setSelectedItem(s.getPrioridad());

                        JOptionPane.showMessageDialog(null,
                                "Modo edición activado.\nModifique los datos y vuelva a presionar EDITAR SOLICITUD para guardar.",
                                "Edición iniciada", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    SolicitudDonacion s = Main.getGestorSolicitudes().obtenerSolicitudes().get(solicitudEditando);

                    s.setNombrePaciente(textField1.getText().trim());
                    s.setCedulaPaciente(textField2.getText().trim());
                    s.setFechaIngreso(textField3.getText().trim());
                    s.setTipoSangre((String) cbTipoSolicitud.getSelectedItem());
                    s.setPrioridad((String) cbPrioridad.getSelectedItem());

                    actualizarListaSolicitudes();
                    actualizarTablaSolicitudes();

                    JOptionPane.showMessageDialog(null, "La solicitud ha sido actualizada correctamente.", "Edición completada", JOptionPane.INFORMATION_MESSAGE);
                    solicitudEditando = -1;
                }
            });
        }

        if (btnBuscarTipoSangre != null) {
            btnBuscarTipoSangre.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String tipoReceptor = (String) cboFiltrarSangre.getSelectedItem();
                    if (tipoReceptor == null || tipoReceptor.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Seleccione un tipo de sangre en el filtro.", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    List<Donante> lista = Main.getGestorDonantes().obtenerDonantes();
                    StringBuilder compatibles = new StringBuilder();
                    int contador = 0;

                    for (Donante d : lista) {
                        if (Main.getGestorDonantes().esCompatible(d.getTipoSangre(), tipoReceptor)) {
                            contador++;
                            compatibles.append("• ")
                                    .append(d.getNombre())
                                    .append(" | Cédula: ").append(d.getCedula())
                                    .append(" | Tipo: ").append(d.getTipoSangre())
                                    .append("\n");
                        }
                    }

                    if (contador == 0) {
                        JOptionPane.showMessageDialog(null, "No existen donantes compatibles con " + tipoReceptor, "Sin compatibles", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(null,
                            "Donantes compatibles con " + tipoReceptor + ":\n\n" + compatibles + "\nTotal encontrados: " + contador,
                            "Compatibilidad", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }

        // =========================
        // ✅ MODIFICACIÓN 1: REPORTE con nombres
        // =========================
        if (btnReporteDonantesPorTipo != null) {
            btnReporteDonantesPorTipo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (textAreaReporte != null) {
                        textAreaReporte.setText(generarReportePorTipoConNombres());
                    }
                }
            });
        }

        // Exportar sigue igual, pero estará restringido por rol
        if (btnExportarReporte != null) {
            btnExportarReporte.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Reportes.exportarReporte(Ventana.this, (textAreaReporte != null) ? textAreaReporte.getText() : "");
                }
            });
        }

        if (btnFiltrado != null) {
            btnFiltrado.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String tipoRequerido = (String) cboFiltrarSangre.getSelectedItem();
                    if (tipoRequerido == null || tipoRequerido.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Seleccione un tipo de sangre.", "Filtro", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    List<Donante> lista = Main.getGestorDonantes().obtenerDonantes();
                    int contador = 0;

                    for (Donante d : lista) {
                        if (tipoRequerido.equalsIgnoreCase(d.getTipoSangre())) contador++;
                    }

                    JOptionPane.showMessageDialog(null,
                            "Tipo requerido: " + tipoRequerido + "\nDonantes disponibles: " + contador,
                            "Resultado del filtrado", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }

        if (btnBuscarCedulaLista != null) {
            btnBuscarCedulaLista.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String cedula = txtBuscarCedulaLista.getText().trim();
                    Donante d = Main.getGestorDonantes().buscarPorCedula(cedula);

                    if (d == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró un donante con esa cédula.");
                        return;
                    }

                    DefaultTableModel model = (DefaultTableModel) tblDonantes.getModel();
                    model.setRowCount(0);

                    model.addRow(new Object[]{
                            d.getNombre(),
                            d.getCedula(),
                            d.getEdad(),
                            d.getFecha(),
                            d.getContacto(),
                            d.getTipoSangre(),
                            d.getCiudad()
                    });
                }
            });
        }

        if (btnMostrarTodos != null) {
            btnMostrarTodos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    txtBuscarCedulaLista.setText("");
                    actualizarTablaDonantes();
                }
            });
        }

        // =========================
        // ✅ MODIFICACIÓN 2: RESTRICCIÓN POR ROL
        // =========================
        aplicarRestriccionesPorRol();
    }

    public JPanel getPrincipalPanel() {
        return Principal;
    }

    private void inicializarTablas() {
        modeloTablaDonantes = new DefaultTableModel();
        modeloTablaDonantes.addColumn("Cédula");
        modeloTablaDonantes.addColumn("Nombre");
        modeloTablaDonantes.addColumn("Edad");
        modeloTablaDonantes.addColumn("Fecha");
        modeloTablaDonantes.addColumn("Contacto");
        modeloTablaDonantes.addColumn("Tipo Sangre");
        modeloTablaDonantes.addColumn("Ciudad");
        if (tblDonantes != null) tblDonantes.setModel(modeloTablaDonantes);

        modeloTablaSolicitudes = new DefaultTableModel();
        modeloTablaSolicitudes.addColumn("Paciente");
        modeloTablaSolicitudes.addColumn("Cédula");
        modeloTablaSolicitudes.addColumn("Fecha ingreso");
        modeloTablaSolicitudes.addColumn("Tipo sangre");
        modeloTablaSolicitudes.addColumn("Prioridad");
        if (tblSolicitudes != null) tblSolicitudes.setModel(modeloTablaSolicitudes);
    }

    private void actualizarTablaDonantes() {
        if (modeloTablaDonantes == null) return;
        modeloTablaDonantes.setRowCount(0);

        List<Donante> lista = Main.getGestorDonantes().obtenerDonantes();
        for (Donante d : lista) {
            modeloTablaDonantes.addRow(new Object[]{
                    d.getCedula(),
                    d.getNombre(),
                    d.getEdad(),
                    d.getFecha(),
                    d.getContacto(),
                    d.getTipoSangre(),
                    d.getCiudad()
            });
        }
    }

    private void actualizarTablaSolicitudes() {
        if (modeloTablaSolicitudes == null) return;
        modeloTablaSolicitudes.setRowCount(0);

        List<SolicitudDonacion> lista = Main.getGestorSolicitudes().obtenerSolicitudes();
        for (SolicitudDonacion s : lista) {
            modeloTablaSolicitudes.addRow(new Object[]{
                    s.getNombrePaciente(),
                    s.getCedulaPaciente(),
                    s.getFechaIngreso(),
                    s.getTipoSangre(),
                    s.getPrioridad()
            });
        }
    }

    private void actualizarListaDonantes() {
        if (modeloDonantes == null) return;
        modeloDonantes.clear();
        List<Donante> lista = Main.getGestorDonantes().obtenerDonantes();
        for (Donante d : lista) {
            modeloDonantes.addElement(d.toString());
        }
    }

    private void actualizarListaSolicitudes() {
        if (modeloSolicitudes == null) return;
        modeloSolicitudes.clear();
        List<SolicitudDonacion> lista = Main.getGestorSolicitudes().obtenerSolicitudes();
        for (SolicitudDonacion s : lista) {
            modeloSolicitudes.addElement(s.toString());
        }
    }

    private void actualizarComparacionesPorTipoExacto() {
        String tipo = (comboBox1 != null) ? (String) comboBox1.getSelectedItem() : null;
        if (tipo == null || tipo.trim().isEmpty()) return;

        if (modeloListDonantesComparacion != null) modeloListDonantesComparacion.clear();
        if (modeloListSolicitudesComparacion != null) modeloListSolicitudesComparacion.clear();

        List<Donante> donantes = Main.getGestorDonantes().obtenerDonantes();
        for (Donante d : donantes) {
            if (tipo.equalsIgnoreCase(d.getTipoSangre())) {
                modeloListDonantesComparacion.addElement(d.toString());
            }
        }

        List<SolicitudDonacion> solicitudes = Main.getGestorSolicitudes().obtenerSolicitudes();
        for (SolicitudDonacion s : solicitudes) {
            if (tipo.equalsIgnoreCase(s.getTipoSangre())) {
                modeloListSolicitudesComparacion.addElement(s.toString());
            }
        }
    }

    private void btnAgregarDonanteActionPerformed(ActionEvent evt) {
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        int edad = (Integer) spEdad.getValue();
        String fecha = txtFecha.getText().trim();
        String tipo = (String) cbTipoSandre.getSelectedItem();
        String ciudad = (String) cbCiudad.getSelectedItem();
        String contacto = (txtContacto != null) ? txtContacto.getText().trim() : "";

        if (nombre.isEmpty() || cedula.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar Nombre, Cédula y Fecha.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Donante> lista = Main.getGestorDonantes().obtenerDonantes();
        String nombreNormalizado = normalizarTexto(nombre);

        for (Donante d : lista) {
            String nombreExistenteNormalizado = normalizarTexto(d.getNombre());

            if (nombreExistenteNormalizado.equals(nombreNormalizado) && !d.getCedula().equals(cedula)) {
                JOptionPane.showMessageDialog(this, "Esta persona ya está registrada con otra cédula.", "Registro duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!nombreExistenteNormalizado.equals(nombreNormalizado) && d.getCedula().equals(cedula)) {
                JOptionPane.showMessageDialog(this, "La cédula ingresada ya pertenece a otra persona registrada.", "Registro duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (nombreExistenteNormalizado.equals(nombreNormalizado) && d.getCedula().equals(cedula)) {
                JOptionPane.showMessageDialog(this, "Este donante ya se encuentra registrado.", "Registro duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Donante nuevo = new Donante(nombre, cedula, tipo, edad, ciudad, fecha, contacto);
        Main.getGestorDonantes().agregarDonante(nuevo);

        txtNombre.setText("");
        txtCedula.setText("");
        txtFecha.setText("");
        spEdad.setValue(0);
        if (txtContacto != null) txtContacto.setText("");

        actualizarListaDonantes();
        actualizarTablaDonantes();
        actualizarComparacionesPorTipoExacto();
    }

    private void btnAgregarSolicitudActionPerformed(ActionEvent evt) {
        String tipo = (String) cbTipoSolicitud.getSelectedItem();
        String prioridad = (String) cbPrioridad.getSelectedItem();
        String nombrePac = textField1.getText().trim();
        String cedula = textField2.getText().trim();
        String fechaIngreso = textField3.getText().trim();

        if (nombrePac.isEmpty() || cedula.isEmpty() || fechaIngreso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar Nombre del paciente, Cédula y Fecha de ingreso.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "La cédula solo debe contener números.", "Cédula inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cedula.length() == 0 || cedula.length() > 10) {
            JOptionPane.showMessageDialog(this, "Ingrese un número mayor a 0 y de máximo 10 dígitos.", "Cédula inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (Long.parseLong(cedula) <= 0) {
            JOptionPane.showMessageDialog(this, "Ingrese un número mayor a 0 y de máximo 10 dígitos.", "Cédula inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SolicitudDonacion s = new SolicitudDonacion(tipo, prioridad, nombrePac, cedula, fechaIngreso);
        Main.getGestorSolicitudes().agregarSolicitud(s);

        textField1.setText("");
        textField2.setText("");
        textField3.setText("");

        actualizarListaSolicitudes();
        actualizarTablaSolicitudes();
        actualizarComparacionesPorTipoExacto();
    }

    private void btnAtenderSolicitudActionPerformed(ActionEvent evt) {
        SolicitudDonacion s = Main.getGestorSolicitudes().atenderSiguiente();
        if (s == null) {
            JOptionPane.showMessageDialog(this, "No hay solicitudes en la cola.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Donante encontrado = Main.getGestorDonantes().buscarCompatiblePorTipo(s.getTipoSangre());
        if (encontrado != null) {
            JOptionPane.showMessageDialog(this, "Solicitud atendida con el donante: " + encontrado.getNombre(), "Solicitud atendida", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un donante compatible.", "Sin coincidencias", JOptionPane.WARNING_MESSAGE);
        }

        actualizarListaSolicitudes();
        actualizarTablaSolicitudes();
        actualizarComparacionesPorTipoExacto();
    }

    private void btnSangreActionPerformed(ActionEvent evt) {
        Main.getGestorDonantes().ordenarPorTipoSangre();
        actualizarListaDonantes();
        actualizarTablaDonantes();
    }

    private void btnCedulaActionPerformed(ActionEvent evt) {
        Main.getGestorDonantes().ordenarPorCedula();
        actualizarListaDonantes();
        actualizarTablaDonantes();
    }

    private void btnFechaActionPerformed(ActionEvent evt) {
        if (cboOrden != null && cboOrden.getSelectedItem() != null) {
            String op = cboOrden.getSelectedItem().toString().trim().toLowerCase();

            if (op.contains("prior")) {
                Main.getGestorSolicitudes().ordenarPorPrioridad();
            } else if (op.contains("fecha")) {
                Main.getGestorDonantes().ordenarPorFecha();
                Main.getGestorSolicitudes().ordenarPorFechaIngreso();
            } else if (op.contains("tipo")) {
                Main.getGestorDonantes().ordenarPorTipoSangre();
                Main.getGestorSolicitudes().ordenarPorTipoSangre();
            } else {
                Main.getGestorDonantes().ordenarPorFecha();
            }
        } else {
            Main.getGestorDonantes().ordenarPorFecha();
        }

        actualizarListaDonantes();
        actualizarListaSolicitudes();
        actualizarTablaDonantes();
        actualizarTablaSolicitudes();
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
