package Entidades;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Administrador extends Usuario {

    public Administrador(Integer id, String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, contrasena, isAdmin);
    }

    public Administrador(String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(nombre, apellido, doc, usuario, contrasena, isAdmin);
    }

    public Administrador(Integer id, String nombre, String apellido, String doc, String usuario, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, isAdmin);
    }

    @Override
    public JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReporteEspectaculos = new JMenuItem("Espectaculos");
        itemReporteEspectaculos.setActionCommand("reportes");
        //itemReporteEspectaculos.addActionListener(e -> cambiarVista("reporte_espectaculos"));

        JMenu menuEstadio = new JMenu("Estadios");
        JMenuItem itemEstadio = new JMenuItem("Listado Estadio");
        JMenuItem itemUbicacion = new JMenuItem("Ubicaciones");
        itemEstadio.setActionCommand("abm_estadios");
        itemUbicacion.setActionCommand("abm_ubicaciones");
        //itemEstadio.addActionListener(e -> cambiarVista("listado_estadios"));
        //itemUbicacion.addActionListener(e -> cambiarVista("listado_ubicaciones"));

        JMenu menuEspectaculo = new JMenu("Espectaculos");
        JMenuItem itemEspectaculo = new JMenuItem("Listado");
        itemEspectaculo.setActionCommand("abm_espectaculos");
        //itemEspectaculo.addActionListener(e -> cambiarVista("listado_espectaculos"));

        JMenu menuUsuario = new JMenu("Usuarios");
        JMenuItem itemUsuario = new JMenuItem("Listado");
        itemUsuario.setActionCommand("abm_usuarios");
        //itemUsuario.addActionListener(e -> cambiarVista("listado_usuarios"));

        menuEstadio.add(itemEstadio);
        menuEstadio.add(itemUbicacion);
        menuUsuario.add(itemUsuario);
        menuEspectaculo.add(itemEspectaculo);
        menuReportes.add(itemReporteEspectaculos);

        menuBar.add(menuEspectaculo);
        menuBar.add(menuEstadio);
        menuBar.add(menuUsuario);
        menuBar.add(menuReportes);

        // Porque me molesta tener que cerrar sesion para revisar la db
        JMenu menuVenta = new JMenu("Venta");
        JMenuItem itemVenta = new JMenuItem("Listado");
        itemVenta.setActionCommand("venta");
        //itemVenta.addActionListener(e -> cambiarVista("venta"));
        menuVenta.add(itemVenta);
        menuBar.add(menuVenta);

        return menuBar;
    }

}
