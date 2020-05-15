package Serie4;

import javax.swing.*;
import java.sql.Connection;

public class DeleteInstallationPanel extends JPanel {
    private JLabel welcomeLabel;

    public DeleteInstallationPanel(Connection connection) {
        welcomeLabel = new JLabel("Bienvenue dans le deletage");
        this.add(welcomeLabel);

    }

}
