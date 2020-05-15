package Serie4;

import javax.swing.*;
import java.sql.Connection;

public class CustomShowInstallationPanel extends JPanel {
    private JLabel welcomeLabel;

    public CustomShowInstallationPanel(Connection connection) {
        welcomeLabel = new JLabel("Bienvenue dans la recherche custom des install");
        this.add(welcomeLabel);

    }

}
