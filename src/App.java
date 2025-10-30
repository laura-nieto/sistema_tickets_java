import javax.swing.SwingUtilities;

import Vista.AppView;


public class App {
    public static void main(String[] args) throws Exception {
        
        SwingUtilities.invokeLater(() -> {
            new AppView();
        });
    
    }
}
