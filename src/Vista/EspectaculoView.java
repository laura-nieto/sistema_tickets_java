package Vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import Entidades.Espectaculo;
import Entidades.Estadio;
import Excepciones.DatabaseException;
import Excepciones.FormularioInvalidoException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.EspectaculosDB;
import Persistencia.EstadioDB;
import Servicio.EspectaculosServicio;
import Servicio.EstadioServicio;

public class EspectaculoView extends JPanel {

    private AppView frame;

    private EspectaculosServicio service;
    private EstadioServicio serviceEstadio; // Lo necesito para traer la data

    private CardLayout layout;
    private JPanel panelCards, panelLista, panelFormulario;

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtNombre, txtFecha;
    private JComboBox<ComboItem> txtEstadio = new JComboBox<>();

    private Boolean modoEdicion = false;
    private Integer idEdicion = -1;


    public EspectaculoView(AppView frame) {
        this.frame = frame;

        this.service = new EspectaculosServicio(new EspectaculosDB());
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
                JOptionPane.showMessageDialog(this, "Seleccioná un espectaculo para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnBorrar.addActionListener(e -> {
            Integer fila = tabla.getSelectedRow();
            if (fila >= 0) {
                Integer id = (Integer) tabla.getValueAt(fila, 0);
                borrarEspectaculo(id);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un espectaculo para borrar", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Tabla
        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Fecha", "Estadio"}, 0);
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
            List<Espectaculo> espectaculos = service.list();

            for (Espectaculo espectaculo : espectaculos) {
                modelo.addRow(new Object[]{espectaculo.getId(), espectaculo.getName(), espectaculo.getTimestamp(), espectaculo.getEstadio().getName()});
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

        panelFormulario.add(new JLabel("Fecha y Hora (yyyy-mm-dd hh:mm):"));
        txtFecha = new JTextField();
        panelFormulario.add(txtFecha);

        panelFormulario.add(new JLabel("Estadio:"));
        crearComboBox();
        panelFormulario.add(txtEstadio);

        JButton btnGuardar = new JButton("Guardar");
        panelFormulario.add(btnGuardar);
        btnGuardar.addActionListener(e -> {
            try {
                guardarEspectaculo();
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
        this.recargarComboEstadios();

        if (editar) {

            this.idEdicion = (Integer) tabla.getValueAt(id, 0);

            txtNombre.setText((String) tabla.getValueAt(id, 1));
            txtFecha.setText(tabla.getValueAt(id, 2).toString());

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

    private void guardarEspectaculo() throws FormularioInvalidoException {
        
        validarCampos();

        String nombre = txtNombre.getText();

        ComboItem item = (ComboItem) txtEstadio.getSelectedItem();
        Integer idEstadio = item.getValue();

        Boolean okAction = false;

        try {
            // Formateo Fecha - Dentro del try porque me tira excepcion sdf.parse
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.setLenient(false); // fuerza formato exacto
            Date fechaDate = sdf.parse(txtFecha.getText());
            Timestamp fechaTime = new Timestamp(fechaDate.getTime());

            // Obtengo el estadio
            Estadio estadio = serviceEstadio.get(idEstadio);

            if (modoEdicion && idEdicion >= 0) {
                service.edit(idEdicion, nombre, estadio, fechaTime);

                okAction = true;

                modoEdicion = false;
                idEdicion = null;
            } else {
                service.insert(nombre, estadio, fechaTime);
                
                okAction = true;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "El formato de la fecha es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(frame, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(frame, "Parece que el registro no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (okAction) {

            txtFecha.setText("");
            txtNombre.setText("");

            cargarDatos();
            layout.show(panelCards, "lista");
        }
    }

    private void validarCampos() throws FormularioInvalidoException {
        
        String nombre = txtNombre.getText();
        String fecha  =  txtFecha.getText();
        
        if (nombre.isEmpty() || fecha.isEmpty()) {
            throw new FormularioInvalidoException();
        }

        try {
           
            // Valido fecha
            String fechaIngresada = txtFecha.getText().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.setLenient(false); // fuerza formato exacto
            Date fechaDate = sdf.parse(fechaIngresada);

            Timestamp fechaTime = new Timestamp(fechaDate.getTime());
            Timestamp ahora = new Timestamp(System.currentTimeMillis());

            if (fechaTime.before(ahora)) {
                System.out.println("La fecha ingresada debe ser posterior al dia de hoy");
                throw new FormularioInvalidoException();
            }

        } catch (ParseException e) {
            e.printStackTrace();
            throw new FormularioInvalidoException();
        }
    }

    private void borrarEspectaculo(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas borrar este espectaculo?", "Confirmar", JOptionPane.YES_NO_OPTION);
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

    private void recargarComboEstadios() {
        txtEstadio.removeAllItems();

        try {
            List<Estadio> estadios = this.serviceEstadio.list();

            for (Estadio estadio : estadios) {
                txtEstadio.addItem(new ComboItem(estadio.getId(), estadio.getName()));
            }

        } catch (DatabaseException | RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(frame, "Hubo un problema, reintente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
