package Entidades;

import javax.swing.JMenuBar;

public abstract class Usuario {

    private Integer id;
    private String name;
    private String lastname;
    private String document;
    private String username;
    private String password;
    private Boolean isAdmin;

    public Usuario (Integer id, String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        this.id       = id;
        this.name     = nombre;
        this.lastname = apellido;
        this.document = doc;
        this.username = usuario;
        this.password = contrasena;
        this.isAdmin  = isAdmin;
    }

    public Usuario (String nombre, String apellido, String doc, String usuario, String contrasena, Boolean isAdmin) {
        this.name     = nombre;
        this.lastname = apellido;
        this.document = doc;
        this.username = usuario;
        this.password = contrasena;
        this.isAdmin  = isAdmin;
    }

    public Usuario (Integer id, String nombre, String apellido, String doc, String usuario, Boolean isAdmin) {
        this.id       = id;
        this.name     = nombre;
        this.lastname = apellido;
        this.document = doc;
        this.username = usuario;
        this.isAdmin  = isAdmin;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDocument() {
        return document;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public abstract JMenuBar crearMenu();
}
