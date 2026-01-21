import javax.swing.*;
import java.io.File;
import java.io.FileWriter;

public class Reportes {

    public static void exportarReporte(JFrame parent, String contenido) {
        if (contenido == null || contenido.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "No hay ningún reporte para exportar.", "Exportar reporte", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte");
        chooser.setSelectedFile(new File("reporte_donantes.txt"));

        int opcion = chooser.showSaveDialog(parent);
        if (opcion != JFileChooser.APPROVE_OPTION) return;

        File archivo = chooser.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".txt")) {
            archivo = new File(archivo.getAbsolutePath() + ".txt");
        }

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(contenido);
            JOptionPane.showMessageDialog(parent, "Reporte exportado correctamente.\n\nRuta:\n" + archivo.getAbsolutePath(),
                    "Exportación exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error al exportar el reporte:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
