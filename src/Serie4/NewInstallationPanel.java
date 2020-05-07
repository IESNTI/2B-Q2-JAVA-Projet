package Serie4;

import javax.swing.*;

public class NewInstallationCont extends JPanel {
    private JLabel welcomeLabel;
    public NewInstallationCont() {
        welcomeLabel = new JLabel("Bienvenue");
        JButton joinButton = new JButton ("Commencer le programme.");
        this.add(welcomeLabel);
        this.add(joinButton);
    }
}
