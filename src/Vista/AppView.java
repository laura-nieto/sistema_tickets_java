package Vista;

import javax.swing.*;

import java.awt.*;

public class AppView extends JFrame {

    private CardLayout cardLayout;

    private JPanel mainPanel;

    public AppView() {
        setTitle("Ticket Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        JPanel borderMain = new JPanel(new BorderLayout());
        borderMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel = new JPanel(cardLayout);

        LoginView loginPanel = new LoginView(this);
        MainView principalPanel = new MainView();
        EstadioView estadioPanel = new EstadioView(this);
        UsuarioView usuarioPanel = new UsuarioView(this);
        EspectaculoView espectaculoPanel = new EspectaculoView(this);

        // Vista de pantallas
        mainPanel.add(loginPanel, "login");
        mainPanel.add(principalPanel, "principal");
        mainPanel.add(estadioPanel, "listado_estadios");
        mainPanel.add(usuarioPanel, "listado_usuarios");
        mainPanel.add(espectaculoPanel, "listado_espectaculos");

        borderMain.add(mainPanel);
        add(borderMain);
        setVisible(true);
    }

    public void createMenu(Boolean isAdmin) {
        JMenuBar menuBar = new JMenuBar();

        if (isAdmin) {
            JMenu menuReportes = new JMenu("Reportes");

            JMenu menuEstadio = new JMenu("Estadios");
            JMenuItem itemEstadio = new JMenuItem("Listado");
            itemEstadio.addActionListener(e -> cambiarVista("listado_estadios"));

            JMenu menuEspectaculo = new JMenu("Espectaculos");
            JMenuItem itemEspectaculo = new JMenuItem("Listado");
            itemEspectaculo.addActionListener(e -> cambiarVista("listado_espectaculos"));

            JMenu menuUsuario = new JMenu("Usuarios");
            JMenuItem itemUsuario = new JMenuItem("Listado");
            itemUsuario.addActionListener(e -> cambiarVista("listado_usuarios"));

            menuEstadio.add(itemEstadio);
            menuUsuario.add(itemUsuario);
            menuEspectaculo.add(itemEspectaculo);

            menuBar.add(menuEspectaculo);
            menuBar.add(menuEstadio);
            menuBar.add(menuUsuario);
            menuBar.add(menuReportes);
        } else {
            JMenu menuVenta = new JMenu("Venta");

            menuBar.add(menuVenta);
        }

        setJMenuBar(menuBar);
        revalidate();
        repaint();
    }

    public void cambiarVista(String nombrePantalla) {
        cardLayout.show(mainPanel, nombrePantalla);
    }
}
