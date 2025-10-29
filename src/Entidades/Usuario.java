package Entidades;
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
}
