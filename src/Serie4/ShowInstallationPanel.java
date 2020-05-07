package Serie4;

import javax.swing.*;

public class NewInstallationPanel extends JPanel {
    private JLabel welcomeLabel;
    public NewInstallationPanel() {
        welcomeLabel = new JLabel("Bienvenue dans une nouvelle installation");
        this.add(welcomeLabel);
    }
}
