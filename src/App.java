import javax.swing.SwingUtilities;

import Servicio.AppFrame;


public class App {
    public static void main(String[] args) throws Exception {
        
        SwingUtilities.invokeLater(() -> {
            new AppFrame();
        });
    
    }
}
