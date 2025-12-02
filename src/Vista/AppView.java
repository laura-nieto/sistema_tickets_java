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
        ReporteEspectaculosView reportEspectaculoView = new ReporteEspectaculosView(this);

        // Vista de pantallas
        mainPanel.add(loginPanel, "login");
        mainPanel.add(principalPanel, "principal");
        mainPanel.add(estadioPanel, "listado_estadios");
        mainPanel.add(ubicacionPanel, "listado_ubicaciones");
        mainPanel.add(usuarioPanel, "listado_usuarios");
        mainPanel.add(espectaculoPanel, "listado_espectaculos");
        mainPanel.add(ventaView, "venta");
        mainPanel.add(reportEspectaculoView, "reporte_espectaculos");

        borderMain.add(mainPanel);
        add(borderMain);
        setVisible(true);
    }

    public void createMenu() {
        JMenuBar menuBar = usuarioLogeado.crearMenu();

        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu m = menuBar.getMenu(i);

            for (int j = 0; j < m.getItemCount(); j++) {
                JMenuItem item = m.getItem(j);

                if (item == null) continue;

                switch (item.getActionCommand()) {
                    case "reportes":
                        item.addActionListener(e -> cambiarVista("reporte_espectaculos"));
                        break;
                    case "abm_estadios":
                        item.addActionListener(e -> cambiarVista("listado_estadios"));
                        break;
                    case "abm_ubicaciones":
                        item.addActionListener(e -> cambiarVista("listado_ubicaciones"));
                        break;
                    case "abm_espectaculos":
                        item.addActionListener(e -> cambiarVista("listado_espectaculos"));
                        break;
                    case "abm_usuarios":
                        item.addActionListener(e -> cambiarVista("listado_usuarios"));
                        break;
                    case "venta":
                        item.addActionListener(e -> cambiarVista("venta"));
                        break;
                }
            }
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
