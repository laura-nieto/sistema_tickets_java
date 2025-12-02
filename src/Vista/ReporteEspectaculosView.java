package Vista;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Timestamp;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Entidades.Espectaculo;
import Excepciones.DatabaseException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.EntradaDB;
import Servicio.ReportesServicios;

public class ReporteEspectaculosView extends JPanel {

    private AppView frame;

    private ReportesServicios service;

    private JTextField txtDesde, txtHasta;
    private JButton btnGenerar;

    private JTable tabla;
    private DefaultTableModel modelo;

    public ReporteEspectaculosView(AppView frame) {

        this.frame = frame;

        this.service = new ReportesServicios(new EntradaDB());

        crearVistaPrincipal();
    }

    public void crearVistaPrincipal() {
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelFiltros.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Columna Desde
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFiltros.add(new JLabel("Desde (yyyy-mm-dd):"), gbc);

        gbc.gridy = 1;
        txtDesde = new JTextField(10);
        panelFiltros.add(txtDesde, gbc);

        // Columna Hasta
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelFiltros.add(new JLabel("Hasta (yyyy-mm-dd):"), gbc);

        gbc.gridy = 1;
        txtHasta = new JTextField(10);
        panelFiltros.add(txtHasta, gbc);

        // Columna Botón
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        btnGenerar = new JButton("Generar Reporte");
        btnGenerar.addActionListener(e -> generarReporte());
        panelFiltros.add(btnGenerar, gbc);

        //  Tabla 
        modelo = new DefaultTableModel(new Object[]{"Espectáculo", "Vendidas", "Total"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        // Se arma la vista
        setLayout(new BorderLayout());
        add(panelFiltros, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void generarReporte() {

        // Validacion
        String desdeTxt = txtDesde.getText().trim();
        String hastaTxt = txtHasta.getText().trim();

        if (desdeTxt.isEmpty() || hastaTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese ambas fechas.", "Fechas invalidas", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            Timestamp desde = Timestamp.valueOf(desdeTxt + " 00:00:00");
            Timestamp hasta = Timestamp.valueOf(hastaTxt + " 23:59:59");

            Map<Integer, Object[]> datos = service.reporteEspectaculos(desde, hasta);

            modelo.setRowCount(0);

            if (datos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados para las fechas ingresadas.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
                return;
            }

            for (Object[] fila : datos.values()) {
                Espectaculo esp = (Espectaculo) fila[0];
                Integer cantidad = (Integer) fila[1];
                Double total = (Double) fila[2];

                modelo.addRow(new Object[]{
                    esp.getName(),
                    cantidad,
                    total
                });
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, "Error al generar el reporte.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(this, "Error al generar el reporte.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
