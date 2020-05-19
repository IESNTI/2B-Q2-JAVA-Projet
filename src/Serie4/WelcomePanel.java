package Serie4;

import javax.swing.*;
import java.sql.Connection;
import java.awt.*;

public class WelcomePanel extends JPanel {
    private JLabel welcomeLabel;

    public WelcomePanel(Connection connection) {
        setLayout(new BorderLayout());

        welcomeLabel = new JLabel(
                "<html><center>Bienvenue dans le programme pour la gestion des installations de logiciels."
                        + "<br/> Pour naviguer, veuillez sélectionner votre choix dans le menu tout en haut à gauche.</center></html>",
                SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        add(welcomeLabel, BorderLayout.CENTER);

    }

}
