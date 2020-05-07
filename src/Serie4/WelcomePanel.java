package Serie4;

import javax.swing.*;

public class WelcomeCont extends JPanel{
    private JLabel welcomeLabel;
    public WelcomeCont() {
        welcomeLabel = new JLabel("Bienvenue");
        JButton joinButton = new JButton ("Commencer le programme.");
        this.add(welcomeLabel);
        this.add(joinButton);

    }

}
