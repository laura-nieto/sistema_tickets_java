package Vista;

import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    public MainView() {

        setLayout(new BorderLayout());
        JLabel label = new JLabel("Bienvenido al sistema", SwingConstants.CENTER);

        add(label, BorderLayout.CENTER);
    }
}
