package Serie4;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class MainWindow extends JFrame {
    // Etablir la connexion ("le câble qui relie le programme Java à la BD")
    private Connection connection = AccessBDGen.connecter("DbInstallations", "java", "b88iBowEv5te");

    private Container cont = getContentPane();
    private WelcomePanel welcomePanel = new WelcomePanel(connection);
    private NewInstallationPanel newInstallationPanel = new NewInstallationPanel(connection);
    private ShowInstallationPanel showInstallationPanel = new ShowInstallationPanel(connection);
    private JMenuBar bar = new JMenuBar();
    private JMenu menuInstallations = new JMenu("Installations");
    private JMenu menuHelp = new JMenu("Aide");
    private JMenuItem menuHelpAbout = new JMenuItem("A propos");
    private JMenuItem menuInstallationsNew = new JMenuItem("Nouvelle installation");
    private JMenuItem menuInstallationsShow = new JMenuItem("Afficher les installations");

    public MainWindow() throws SQLException {
        super("Formulaire de DB");
        setMinimumSize(new Dimension(1000, 800));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //Centers the display window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        bar.add(menuInstallations);
        bar.add(menuHelp);
        menuHelp.add(menuHelpAbout);
        menuInstallations.add(menuInstallationsNew);
        menuInstallations.add(menuInstallationsShow);
        add(bar, BorderLayout.PAGE_START);

        cont.add(welcomePanel);
        setVisible(true);

        actionManager actionListener = new actionManager();
        menuHelpAbout.addActionListener(actionListener);
        menuInstallationsNew.addActionListener(actionListener);
        menuInstallationsShow.addActionListener(actionListener);
    }

    private void switchPanel(JPanel panel) {
        cont.remove(cont.getComponent(1));
        cont.add(panel);
        cont.revalidate();
        cont.repaint();
    }

    private class actionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menuHelpAbout){
                new AboutWindow();}
            else if (e.getSource() == menuInstallationsNew){
                switchPanel(newInstallationPanel);}
            else if (e.getSource() == menuInstallationsShow){
                showInstallationPanel.SQLRequest("Installation");
                switchPanel(showInstallationPanel);}
        }

    }
}
