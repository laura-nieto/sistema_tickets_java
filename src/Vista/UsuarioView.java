package Vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Entidades.Usuario;
import Excepciones.DatabaseException;
import Excepciones.FormularioInvalidoException;
import Excepciones.RegistroNotFoundExeption;
import Excepciones.UsuarioExistenteException;
import Persistencia.UsuarioDB;
import Servicio.UsuarioServicio;


public class UsuarioView extends JPanel{

    private AppView frame;

    private UsuarioServicio service;

    private CardLayout layout;
    private JPanel panelCards, panelLista, panelFormulario;

    private JTextField txtNombre, txtApellido, txtDocumento, txtUsername, txtPassword;
    private JCheckBox txtIsAdmin;

    private JTable tabla;
    private DefaultTableModel modelo;

    private Boolean modoEdicion = false;
    private Integer idEdicion = -1;

    public UsuarioView(AppView frame) {
        this.frame = frame;

        this.service = new UsuarioServicio(new UsuarioDB());

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
                JOptionPane.showMessageDialog(this, "Seleccioná un usuario para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnBorrar.addActionListener(e -> {
            Integer fila = tabla.getSelectedRow();
            if (fila >= 0) {
                Integer id = (Integer) tabla.getValueAt(fila, 0);
                borrarUsuario(id);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un usuario para borrar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Tabla
        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Documento", "Usuario", "Es administrador"}, 0);
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
            List<Usuario> users = service.list();

            for (Usuario user : users) {
                modelo.addRow(new Object[]{user.getId(), user.getName(), user.getLastname(), user.getDocument(), user.getUsername(), user.getIsAdmin()});
            }
        } catch (DatabaseException | RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al cargar los datos. Por favor, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearVistaFormulario() {
        panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Documento:"));
        txtDocumento = new JTextField();
        panelFormulario.add(txtDocumento);

        panelFormulario.add(new JLabel("Usuario:"));
        txtUsername = new JTextField();
        panelFormulario.add(txtUsername);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JTextField();
        panelFormulario.add(txtPassword);

        panelFormulario.add(new JLabel());
        txtIsAdmin = new JCheckBox("Administrador");
        panelFormulario.add(txtIsAdmin);

        JButton btnGuardar = new JButton("Guardar");
        panelFormulario.add(btnGuardar);
        btnGuardar.addActionListener(e -> {
            try {
                guardarUsuario();
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

            txtNombre.setText((String) tabla.getValueAt(id, 1));
            txtApellido.setText((String) tabla.getValueAt(id, 2));
            txtDocumento.setText((String) tabla.getValueAt(id, 3));
            txtUsername.setText((String) tabla.getValueAt(id, 4));
            txtIsAdmin.setSelected((Boolean) tabla.getValueAt(id, 5));
        }

        layout.show(panelCards, "form");
    }

    private void guardarUsuario() throws FormularioInvalidoException {

        validarCampos();

        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String documento = txtDocumento.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        Boolean admin = txtIsAdmin.isSelected();

        if (modoEdicion && idEdicion >= 0) {
            try {
                service.edit(idEdicion, nombre, apellido, documento, username, password, admin);
            } catch (DatabaseException e) {
                JOptionPane.showMessageDialog(frame, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RegistroNotFoundExeption e) {
                JOptionPane.showMessageDialog(frame, "Parece que el registro no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            modoEdicion = false;
            idEdicion = null;
        } else {
            try {
                service.insert(nombre, apellido, documento, username, password, admin);
            } catch (DatabaseException | RegistroNotFoundExeption e) {
                JOptionPane.showMessageDialog(frame, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (UsuarioExistenteException e) {
                JOptionPane.showMessageDialog(frame, "Ya existe el usuario ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        cargarDatos();
        layout.show(panelCards, "lista");
    }

    private void validarCampos() throws FormularioInvalidoException {

        String nombre    = txtNombre.getText();
        String apellido  = txtApellido.getText();
        String documento = txtDocumento.getText();
        String username  = txtUsername.getText();
        String password  = txtPassword.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || documento.isEmpty() || username.isEmpty()) {
            throw new FormularioInvalidoException();
        }

        if (!modoEdicion && password.isEmpty()) {
            throw new FormularioInvalidoException();
        }
    }

    private void borrarUsuario(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas borrar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
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
