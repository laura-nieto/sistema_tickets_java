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

import Entidades.Estadio;
import Entidades.Ubicacion;
import Excepciones.DatabaseException;
import Excepciones.FormularioInvalidoException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.EstadioDB;
import Persistencia.UbicacionDB;
import Servicio.EstadioServicio;
import Servicio.UbicacionServicio;

public class UbicacionView extends JPanel {

    private AppView frame;

    private UbicacionServicio service;
    private EstadioServicio serviceEstadio;

    private CardLayout layout;
    private JPanel panelCards, panelLista, panelFormulario;

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtNombre, txtPrecio, txtCapacidad;
    private JComboBox<ComboItem> txtEstadio = new JComboBox<>();

    private Boolean modoEdicion = false;
    private Integer idEdicion = -1;

    public UbicacionView(AppView frame) {

        this.frame = frame;

        this.service = new UbicacionServicio(new UbicacionDB());
        this.serviceEstadio = new EstadioServicio(new EstadioDB());

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
        JButton btnCrear = new JButton("Crear");
        JButton btnEditar = new JButton("Editar");
        JButton btnBorrar = new JButton("Borrar");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 2;

        panelButtons.add(btnCrear);
        panelButtons.add(btnEditar);
        panelButtons.add(btnBorrar);
        panelLista.add(panelButtons, BorderLayout.NORTH);

        btnCrear.addActionListener(e -> mostrarFormulario(false, -1));
        btnEditar.addActionListener(e -> {
            Integer fila = tabla.getSelectedRow();
            if (fila >= 0) {
                mostrarFormulario(true, fila);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un ubicacion para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnBorrar.addActionListener(e -> {
            Integer fila = tabla.getSelectedRow();
            if (fila >= 0) {
                Integer id = (Integer) tabla.getValueAt(fila, 0);
                borrarUbicacion(id);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná una ubicacion para borrar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Tabla
        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Capacidad", "Precio", "Estadio"}, 0);
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
            List<Ubicacion> ubicaciones = service.list();

            for (Ubicacion ubicacion : ubicaciones) {
                modelo.addRow(new Object[]{ubicacion.getId(), ubicacion.getNombre(), ubicacion.getCapacidad(), ubicacion.getPrecio(), ubicacion.getEstadio().getName()});
            }
        } catch (DatabaseException | RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al cargar los datos. Por favor, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearVistaFormulario() {
        panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Capacidad:"));
        txtCapacidad = new JTextField();
        panelFormulario.add(txtCapacidad);

        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Estadio:"));
        crearComboBox();
        panelFormulario.add(txtEstadio);

        JButton btnGuardar = new JButton("Guardar");
        panelFormulario.add(btnGuardar);
        btnGuardar.addActionListener(e -> {
            try {
                guardarUbicacion();
            } catch (FormularioInvalidoException ex) {
                JOptionPane.showMessageDialog(frame, "Uno de los campos es invalido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        panelFormulario.add(btnCancelar);
        btnCancelar.addActionListener(e -> layout.show(panelCards, "lista"));   
    }

    private void crearComboBox() {

        try {
            List<Estadio> estadios = this.serviceEstadio.list();

            for (Estadio estadio : estadios) {
                txtEstadio.addItem(new ComboItem(estadio.getId(), estadio.getName()));
            }

        } catch (DatabaseException | RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(frame, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void mostrarFormulario(boolean editar, int id) {
        this.modoEdicion = editar;

        if (editar) {

            this.idEdicion = (Integer) tabla.getValueAt(id, 0);

            txtNombre.setText((String) tabla.getValueAt(id, 1));
            txtCapacidad.setText(tabla.getValueAt(id, 2).toString());
            txtPrecio.setText(tabla.getValueAt(id, 3).toString());

            for (int i = 0; i < txtEstadio.getItemCount(); i++) {
                ComboItem item = (ComboItem) txtEstadio.getItemAt(i);

                if (item.getLabel().equals((String) tabla.getValueAt(id, 4))) {
                    txtEstadio.setSelectedIndex(i);
                    break;
                }
            }
        }

        layout.show(panelCards, "form");
    }

    private void guardarUbicacion() throws FormularioInvalidoException {
        
        validarCampos();

        String nombre = txtNombre.getText();
        Double precio = Double.valueOf(txtPrecio.getText());
        Integer capacidad = Integer.valueOf(txtCapacidad.getText());

        ComboItem item = (ComboItem) txtEstadio.getSelectedItem();
        Integer idEstadio = item.getValue();

        try {

            // Obtengo el estadio
            Estadio estadio = serviceEstadio.get(idEstadio);

            if (modoEdicion && idEdicion >= 0) {
                service.edit(idEdicion, nombre, precio, capacidad, estadio);

                modoEdicion = false;
                idEdicion = null;
            } else {
                service.insert(nombre, estadio, precio, capacidad);
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(frame, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(frame, "Parece que el registro no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        cargarDatos();
        layout.show(panelCards, "lista");
    }

    private void validarCampos() throws FormularioInvalidoException {
        
        String nombre = txtNombre.getText();
        
        if (nombre.isEmpty()) {
            throw new FormularioInvalidoException();
        }

        // Valido precio y capacidad
        try {
           
            Integer capacidad  =  Integer.valueOf(txtCapacidad.getText());
            Double precio  =  Double.valueOf(txtPrecio.getText());

            if (capacidad <= 0 || precio < 0) {
                throw new FormularioInvalidoException();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new FormularioInvalidoException();
        }
    }

    private void borrarUbicacion(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas borrar esta ubicacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.delete(id);
                cargarDatos();
            } catch (DatabaseException e) {
                JOptionPane.showMessageDialog(this, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RegistroNotFoundExeption e) {
                JOptionPane.showMessageDialog(frame, "Parece que el registro no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
