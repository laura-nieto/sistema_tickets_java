package Vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Entidades.Entrada;
import Entidades.Espectaculo;
import Entidades.Ubicacion;
import Excepciones.DatabaseException;
import Excepciones.EspacioRestanteException;
import Excepciones.FormularioInvalidoException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.EntradaDB;
import Persistencia.EspectaculosDB;
import Persistencia.UbicacionDB;
import Servicio.VentaServicio;

public class VentaView extends JPanel{

    private AppView frame;

    private VentaServicio service;

    private CardLayout layout;
    private JPanel panelCards, panelLista, panelFormulario;

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtNombre, txtDoc;
    private JComboBox<ComboItem> txtUbicacion = new JComboBox<>();

    private Integer idEspectaculoElegido = -1;

    public VentaView(AppView frame){
        this.frame = frame;

        this.service = new VentaServicio(new EntradaDB(), new EspectaculosDB(), new UbicacionDB());

        layout = new CardLayout();
        panelCards = new JPanel(layout);
        add(panelCards);
        setLayout(new BorderLayout());
        add(panelCards, BorderLayout.CENTER);

        crearVistaLista();
        crearVistaFormulario();

        panelCards.add(panelLista, "lista");
        panelCards.add(panelFormulario, "form");

        layout.show(panelCards, "lista");
    }

    private void crearVistaLista() {
        
        panelLista = new JPanel(new BorderLayout(10, 10));

        JPanel panelButtons = new JPanel(new GridBagLayout());
      
        // Boton crear
        JButton btnVenta = new JButton("Realizar Venta");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 2;

        panelButtons.add(btnVenta);
        panelLista.add(panelButtons, BorderLayout.NORTH);

        btnVenta.addActionListener(e -> {
            Integer fila = tabla.getSelectedRow();
            if (fila >= 0) {
                try {
                    Espectaculo espectaculo = getEspectaculoSeleccionado();

                    crearComboBox(espectaculo.getEstadio().getId());

                    mostrarFormulario(fila);
                } catch (DatabaseException e1) {
                    JOptionPane.showMessageDialog(this, "Ocurrió un error al cargar los datos. Por favor, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (RegistroNotFoundExeption e1) {
                    JOptionPane.showMessageDialog(this, "Ocurrió un error al cargar los datos. Por favor, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un espectaculo para realizar la venta", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        
        // Tabla
        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Fecha", "Estadio", "Disponible"}, 0);
        tabla = new JTable(modelo) {
            public boolean isCellEditable(int row, int col) {
                return col == 5;
            }
        };

        JScrollPane scroll = new JScrollPane(tabla);
        panelLista.add(scroll, BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {

        modelo.setRowCount(0);

        try {
            List<Espectaculo> espectaculos = service.listEspectaculos();

            for (Espectaculo espectaculo : espectaculos) {

                modelo.addRow(new Object[]{espectaculo.getId(), espectaculo.getName(), espectaculo.getTimestamp(), espectaculo.getEstadio().getName(), espectaculo.espacioRestante()});
            }
        } catch (DatabaseException | RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al cargar los datos. Por favor, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearVistaFormulario() {
        panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

        panelFormulario.add(new JLabel("Apellido y Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Documento:"));
        txtDoc = new JTextField();
        panelFormulario.add(txtDoc);

        panelFormulario.add(new JLabel("Ubicacion:"));
        txtUbicacion = new JComboBox<>();
        panelFormulario.add(txtUbicacion);

        JButton btnGuardar = new JButton("Guardar");
        panelFormulario.add(btnGuardar);
        btnGuardar.addActionListener(e -> {
            try {
                realizarVenta();
            } catch (FormularioInvalidoException ex) {
                JOptionPane.showMessageDialog(frame, "Uno de los campos es invalido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RegistroNotFoundExeption ex) {
                JOptionPane.showMessageDialog(frame, "Parece que el espectaculo no se encontro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        panelFormulario.add(btnCancelar);
        btnCancelar.addActionListener(e -> layout.show(panelCards, "lista"));   
    }

    private void crearComboBox(Integer estadioId) {

        try {
            List<Ubicacion> ubicaciones = this.service.listUbicaciones(estadioId);

            txtUbicacion.removeAllItems();

            for (Ubicacion ubicacion : ubicaciones) {

                String label = ubicacion.getNombre() + " - $" + ubicacion.getPrecio();

                txtUbicacion.addItem(new ComboItem(ubicacion.getId(), label));
            }

        } catch (RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(frame, "No se encontraron ubicaciones.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(frame, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarVenta() throws FormularioInvalidoException, RegistroNotFoundExeption {
        validarCampos();

        String nombre = txtNombre.getText();
        String doc = txtDoc.getText();

        try {
            Entrada entrada = service.sell(frame.getUsuarioLogeado(), nombre, doc, idEspectaculoElegido);

            if (entrada != null) {
                JOptionPane.showMessageDialog(frame, "Se realizo la venta", "Venta exitosa", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(frame, "Parece que hubo un problema.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (EspacioRestanteException e) {
            JOptionPane.showMessageDialog(frame, "Parece que no hay mas entradas a la venta.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        cargarDatos();
        layout.show(panelCards, "lista");
    }

    private void mostrarFormulario(int id) {
        this.idEspectaculoElegido = (Integer) tabla.getValueAt(id, 0);

        layout.show(panelCards, "form");
    }

    private void validarCampos() throws FormularioInvalidoException {

        String nombre = txtNombre.getText();
        String documento  =  txtDoc.getText();

        if (nombre.isEmpty() || documento.isEmpty()) {
            throw new FormularioInvalidoException();
        }
    }

    private Espectaculo getEspectaculoSeleccionado() throws DatabaseException, RegistroNotFoundExeption {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un espectáculo.", "Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        return this.service.getEspectaculo(id);
    }
}
