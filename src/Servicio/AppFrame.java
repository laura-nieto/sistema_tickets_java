package Servicio;

import javax.swing.*;

import Vista.EstadioView;
import Vista.LoginView;
import Vista.MainView;

import java.awt.*;

public class AppFrame extends JFrame {

    private CardLayout cardLayout;

    private JPanel mainPanel;

    public AppFrame() {
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

        // Vista de pantallas
        mainPanel.add(loginPanel, "login");
        mainPanel.add(principalPanel, "principal");
        mainPanel.add(estadioPanel, "listado_estadios");

        borderMain.add(mainPanel);
        add(borderMain);
        setVisible(true);
    }

    public void createMenu(Boolean isAdmin) {
        JMenuBar menuBar = new JMenuBar();

        if (isAdmin) {
            JMenu menuEspectaculo = new JMenu("Espectaculos");
            JMenu menuEstadio = new JMenu("Estadios");
            JMenu menuUsuario = new JMenu("Usuarios");
            JMenu menuReportes = new JMenu("Reportes");

            JMenuItem itemEstadio = new JMenuItem("Listado");
            itemEstadio.addActionListener(e -> cambiarVista("listado_estadios"));

            JMenuItem itemEspectaculo = new JMenuItem("Listado");
            JMenuItem itemUsuario = new JMenuItem("Listado");

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
