package Vista;

import javax.swing.*;

import Entidades.Usuario;
import Excepciones.FormularioInvalidoException;
import Excepciones.RegistroNotFoundExeption;
import Persistencia.UsuarioDB;

import java.awt.*;

import Servicio.AppFrame;
import Servicio.LoginServicio;

public class LoginView extends JPanel {
    
    private AppFrame frame;

    private LoginServicio service;

    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView(AppFrame frame) {

        this.frame = frame;

        this.service = new LoginServicio(new UsuarioDB());

        this.crearVista();

    }

    private void crearVista() {

        setLayout(new GridLayout(3, 3, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(260, 150, 260, 150));
        
        JLabel usuarioLabel = new JLabel("Usuario:", SwingConstants.RIGHT);
        usuarioField = new JTextField(15);
        
        JLabel passwordLabel = new JLabel("Contraseña:", SwingConstants.RIGHT);
        passwordField = new JPasswordField(15);
        
        loginButton = new JButton("Ingresar");

        // Agregamos input y boton
        add(usuarioLabel);
        add(usuarioField);
        add(new JLabel());
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(new JLabel());
        add(loginButton);

        loginButton.addActionListener(e -> validarLogin());
    }

    private void validarLogin() {

        String usuario = usuarioField.getText();
        String password = new String(passwordField.getPassword());

        try {
            validarCampos(usuario, password);

            // Valido el usuario
            Usuario user = service.login(usuario, password);

            // Armo el menu
            if (user.getIsAdmin()) {
                frame.createMenu(true);
            } else {
                frame.createMenu(false);
            }

            // Muestro mensaje
            JOptionPane.showMessageDialog(frame, "Login exitoso", "Warning", JOptionPane.INFORMATION_MESSAGE);

            // Cambio de vista
            frame.cambiarVista("principal");

        } catch (FormularioInvalidoException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RegistroNotFoundExeption e) {
            JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean validarCampos(String usuario, String password) throws FormularioInvalidoException {

        if (usuario.isEmpty() || password.isEmpty()) {
            throw new FormularioInvalidoException();
        }

        return true;
    }
}
