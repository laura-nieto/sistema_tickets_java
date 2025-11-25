package Vista;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Entidades.Usuario;

public class AppView extends JFrame {

    private CardLayout cardLayout;

    private JPanel mainPanel;

    private Usuario usuarioLogeado;

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
        MainView principalPanel = new MainView(this);
        EstadioView estadioPanel = new EstadioView(this);
        UbicacionView ubicacionPanel = new UbicacionView(this);
        UsuarioView usuarioPanel = new UsuarioView(this);
        EspectaculoView espectaculoPanel = new EspectaculoView(this);
        VentaView ventaView = new VentaView(this);

        // Vista de pantallas
        mainPanel.add(loginPanel, "login");
        mainPanel.add(principalPanel, "principal");
        mainPanel.add(estadioPanel, "listado_estadios");
        mainPanel.add(ubicacionPanel, "listado_ubicaciones");
        mainPanel.add(usuarioPanel, "listado_usuarios");
        mainPanel.add(espectaculoPanel, "listado_espectaculos");
        mainPanel.add(ventaView, "venta");

        borderMain.add(mainPanel);
        add(borderMain);
        setVisible(true);
    }

    public void createMenu(Boolean isAdmin) {
        JMenuBar menuBar = new JMenuBar();

        if (isAdmin) {
            JMenu menuReportes = new JMenu("Reportes");

            JMenu menuEstadio = new JMenu("Estadios");
            JMenuItem itemEstadio = new JMenuItem("Listado Estadio");
            JMenuItem itemUbicacion = new JMenuItem("Ubicaciones");
            itemEstadio.addActionListener(e -> cambiarVista("listado_estadios"));
            itemUbicacion.addActionListener(e -> cambiarVista("listado_ubicaciones"));

            JMenu menuEspectaculo = new JMenu("Espectaculos");
            JMenuItem itemEspectaculo = new JMenuItem("Listado");
            itemEspectaculo.addActionListener(e -> cambiarVista("listado_espectaculos"));

            JMenu menuUsuario = new JMenu("Usuarios");
            JMenuItem itemUsuario = new JMenuItem("Listado");
            itemUsuario.addActionListener(e -> cambiarVista("listado_usuarios"));

            menuEstadio.add(itemEstadio);
            menuEstadio.add(itemUbicacion);
            menuUsuario.add(itemUsuario);
            menuEspectaculo.add(itemEspectaculo);

            menuBar.add(menuEspectaculo);
            menuBar.add(menuEstadio);
            menuBar.add(menuUsuario);
            menuBar.add(menuReportes);


            // Porque me molesta tener que cerrar sesion para revisar la db

            JMenu menuVenta = new JMenu("Venta");
            JMenuItem itemVenta = new JMenuItem("Listado");

            itemVenta.addActionListener(e -> cambiarVista("venta"));

            menuVenta.add(itemVenta);
            menuBar.add(menuVenta);

        } else {
            JMenu menuVenta = new JMenu("Venta");
            JMenuItem itemVenta = new JMenuItem("Listado");

            itemVenta.addActionListener(e -> cambiarVista("venta"));

            menuVenta.add(itemVenta);
            menuBar.add(menuVenta);
        }

        setJMenuBar(menuBar);
        revalidate();
        repaint();
    }

    public void cambiarVista(String nombrePantalla) {
        cardLayout.show(mainPanel, nombrePantalla);
    }

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }
}
