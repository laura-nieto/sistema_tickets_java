package Vista;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainView extends JPanel {
    public MainView(AppView frame) {

        setLayout(new BorderLayout());

        String msg = "Bienvenido al sistema";

        JLabel label = new JLabel(msg, SwingConstants.CENTER);

        add(label, BorderLayout.CENTER);
    }
}
