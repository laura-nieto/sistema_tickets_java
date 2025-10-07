public abstract class Usuario {
    private String name;
    private String lastname;

    private String username;
    private String password;

    public Usuario (String nombre, String apellido, String usuario, String contrasena) {
        this.name     = nombre;
        this.lastname = apellido;
        this.username = usuario;
        this.password = contrasena;
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

    public void login(String username, String password) {
        if (username == this.username && password == this.password) {
            System.out.println("Ingresado");
        } else {
            System.out.println("Error en alguno de los datos");
        }
    }
}
