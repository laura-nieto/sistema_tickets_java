package Vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Entidades.Estadio;
import Excepciones.DatabaseException;
import Excepciones.FormularioInvalidoException;
import Persistencia.EstadioDB;
import Servicio.EstadioServicio;

public class EstadioView extends JPanel {

    private AppView frame;

    private EstadioServicio service;

    private CardLayout layout;
    private JPanel panelCards;
    private JPanel panelLista;
    private JPanel panelFormulario;

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtNombre, txtCapacidad, txtDireccion;

    private Boolean modoEdicion = false;
    private Integer idEdicion = -1;

    public EstadioView(AppView frame) {

        this.frame = frame;

        this.service = new EstadioServicio(new EstadioDB());

        layout = new CardLayout();
        panelCards = new JPanel(layout);
        add(panelCards);
        setLayout(new BorderLayout());
        add(panelCards, BorderLayout.CENTER);

        crearVistaLista();
        crearVistaFormulario();

        panelCards.add(getPanelLista(), "lista");
        panelCards.add(getPanelFormulario(), "form");

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
                JOptionPane.showMessageDialog(this, "Seleccioná un estadio para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnBorrar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tabla.getValueAt(fila, 0);
                borrarEstadio(id);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un estadio para borrar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Tabla
        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Capacidad", "Dirección", "Acciones"}, 0);
        tabla = new JTable(modelo) {
            public boolean isCellEditable(int row, int col) {
                return col == 5;
            }
        };

        //tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        //tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Construyo la tabla
        JScrollPane scroll = new JScrollPane(tabla);
        panelLista.add(scroll, BorderLayout.CENTER);

        try {
            List<Estadio> estadios = service.list();

            for (Estadio estadio : estadios) {
                modelo.addRow(new Object[]{estadio.getId(), estadio.getName(), estadio.getCapacity(), estadio.getAddres()});
            }

        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(frame, "Ocurrio un error, vuelva a intentar", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private JPanel getPanelLista() {
        return panelLista;
    }

    // Funciones para el formulario 
    private void crearVistaFormulario() {
        panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Capacidad:"));
        txtCapacidad = new JTextField();
        panelFormulario.add(txtCapacidad);

        panelFormulario.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);

        JButton btnGuardar = new JButton("Guardar");
        panelFormulario.add(btnGuardar);
        btnGuardar.addActionListener(e -> {
            try {
                guardarEstadio();
            } catch (FormularioInvalidoException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Uno de los campos es invalido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        panelFormulario.add(btnCancelar);
        btnCancelar.addActionListener(e -> layout.show(panelCards, "lista"));   
    }

    private JPanel getPanelFormulario() {
        return panelFormulario;
    }

    // --- Métodos auxiliares ---

    private void mostrarFormulario(boolean editar, int id) {
        this.modoEdicion = editar;

        if (editar) {

            this.idEdicion = (Integer) tabla.getValueAt(id, 0);
            Integer cap = (Integer) tabla.getValueAt(id, 2);

            txtNombre.setText((String) tabla.getValueAt(id, 1));
            txtCapacidad.setText(cap.toString());
            txtDireccion.setText((String) tabla.getValueAt(id, 3));
        }

        layout.show(panelCards, "form");
    }

    private void guardarEstadio() throws FormularioInvalidoException {
        String nombre = txtNombre.getText();
        Integer capacidad = Integer.valueOf(txtCapacidad.getText());
        String direccion = txtDireccion.getText();

        validarCampos(nombre, capacidad, direccion);

        if (modoEdicion && idEdicion >= 0) {
            modelo.setValueAt(nombre, idEdicion, 1);
            modelo.setValueAt(capacidad, idEdicion, 2);
            modelo.setValueAt(direccion, idEdicion, 3);

            // update
        } else {
            int nextId = modelo.getRowCount() + 1;
            modelo.addRow(new Object[]{String.valueOf(nextId), nombre, capacidad, direccion});

            // insert
        }

        layout.show(panelCards, "lista");
    }

    private Boolean validarCampos(String nombre, Integer capacidad, String direccion) throws FormularioInvalidoException {

        if (nombre.isEmpty() || direccion.isEmpty() || capacidad <= 0) {
            throw new FormularioInvalidoException();
        }

        return true;
    }

    private void borrarEstadio(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que querés borrar este estadio?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                //service.delete(id);
                //cargarDatos();
                JOptionPane.showMessageDialog(this, "Estadio eliminado");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al borrar el estadio", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
