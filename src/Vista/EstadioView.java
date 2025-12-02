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
import Excepciones.RegistroNotFoundExeption;
import Persistencia.EstadioDB;
import Servicio.EstadioServicio;

public class EstadioView extends JPanel {

    private AppView frame;

    private EstadioServicio service;

    private CardLayout layout;
    private JPanel panelCards, panelLista, panelFormulario;

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
                JOptionPane.showMessageDialog(this, "Seleccioná un estadio para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnBorrar.addActionListener(e -> {
            Integer fila = tabla.getSelectedRow();
            if (fila >= 0) {
                Integer id = (Integer) tabla.getValueAt(fila, 0);
                borrarEstadio(id);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un estadio para borrar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Tabla
        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Capacidad", "Dirección"}, 0);
        tabla = new JTable(modelo) {
            public boolean isCellEditable(int row, int col) {
                return col == 4;
            }
        };

        JScrollPane scroll = new JScrollPane(tabla);
        panelLista.add(scroll, BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {

        modelo.setRowCount(0);

        try {
            List<Estadio> estadios = service.list();

            for (Estadio estadio : estadios) {
                modelo.addRow(new Object[]{estadio.getId(), estadio.getName(), estadio.getCapacity(), estadio.getAddres()});
            }
        } catch (DatabaseException | RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al cargar los datos. Por favor, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
        
        validarCampos();

        String nombre = txtNombre.getText();
        Integer capacidad = Integer.valueOf(txtCapacidad.getText());
        String direccion = txtDireccion.getText();

        Boolean okAction = false;

        if (modoEdicion && idEdicion >= 0) {
            try {
                service.edit(idEdicion, nombre, capacidad, direccion);

                okAction = true;

                modoEdicion = false;
                idEdicion = null;
            } catch (DatabaseException e) {
                JOptionPane.showMessageDialog(frame, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RegistroNotFoundExeption e) {
                JOptionPane.showMessageDialog(frame, "Parece que el registro no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } else {
            try {
                service.insert(nombre, capacidad, direccion);

                okAction = true;
            } catch (DatabaseException e) {
                JOptionPane.showMessageDialog(frame, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        // Si se hizo el insert o update, limpio campos y recargo vista principal
        if (okAction) {
            txtNombre.setText("");
            txtDireccion.setText("");
            txtNombre.setText("");

            cargarDatos();
            layout.show(panelCards, "lista");
        }
    }

    private void validarCampos() throws FormularioInvalidoException {

        String nombre = txtNombre.getText();
        String capacidad = txtCapacidad.getText();
        String direccion = txtDireccion.getText();
        
        if (nombre.isEmpty() || direccion.isEmpty() || capacidad.isEmpty()) {
            throw new FormularioInvalidoException();
        }

        // Esto debo hacerlo por si ingresan letras dentro de capacidad
        try {
            Integer capacidadNum = Integer.valueOf(txtCapacidad.getText());
            
            if (capacidadNum <= 0) {
                throw new FormularioInvalidoException();
            }

        } catch (NumberFormatException e) {
            throw new FormularioInvalidoException();
        }
    }

    private void borrarEstadio(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas borrar este estadio?", "Confirmar", JOptionPane.YES_NO_OPTION);
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
