package Serie4;

import javax.swing.*;
import java.sql.Connection;

public class WelcomePanel extends JPanel{
    private JLabel welcomeLabel;
    public WelcomePanel(Connection connection) {
        welcomeLabel = new JLabel("Bienvenue");
        JButton joinButton = new JButton ("Commencer le programme.");
        this.add(welcomeLabel);
        this.add(joinButton);

    }

}
