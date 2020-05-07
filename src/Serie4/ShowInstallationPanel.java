package Serie4;

import javax.swing.*;
import java.sql.Connection;

public class ShowInstallationPanel extends JPanel {
    private JLabel welcomeLabel;
    public ShowInstallationPanel(Connection connection) {
        welcomeLabel = new JLabel("Affichage des nouvelles installations");
        this.add(welcomeLabel);
    }
}
