public class App {
    public static void main(String[] args) throws Exception {
        
        Usuario admin = new Administrador("Pedro", "Juarez", "pjuarez", "123455");
        Usuario vende = new Vendedor("Roberto","Perez","rperez","123456");

        admin.login("pjuarez", "123455");

    }
}
