package Entidades;

import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Vendedor extends Usuario {

    private List<Entrada> entradas;

    public Vendedor(Integer id, String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, contrasena, isAdmin);
    }

    public Vendedor(String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        super(nombre, apellido, doc, usuario, contrasena, isAdmin);
    }

    public Vendedor(Integer id, String nombre, String apellido, String doc, String usuario, Boolean isAdmin) {
        super(id, nombre, apellido, doc, usuario, isAdmin);
    }

    @Override
    public JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuVenta = new JMenu("Venta");
        JMenuItem itemVenta = new JMenuItem("Listado");
        //itemVenta.addActionListener(e -> cambiarVista("venta"));
        itemVenta.setActionCommand("venta");

        menuVenta.add(itemVenta);
        menuBar.add(menuVenta);

        return menuBar;
    }

}
