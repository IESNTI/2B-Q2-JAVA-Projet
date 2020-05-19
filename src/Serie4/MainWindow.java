package Serie4;

import accessBD.AccessBDGen;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;

public class MainWindow extends JFrame {
    private static final Connection MainWindow = null;

    private Connection connection = AccessBDGen.connecter("DbInstallations", "java", "b88iBowEv5te");

    private Container cont = getContentPane();
    private JMenuBar bar = new JMenuBar();

    private JMenu menuModifyInstallations = new JMenu("Modifier les installations");
    private JMenu menuSearch = new JMenu("Recherche");
    private JMenu menuHelp = new JMenu("Aide");
    private JMenuItem menuHelpAbout = new JMenuItem("A propos");
    private JMenuItem menuModifyInstallationsNew = new JMenuItem("Nouvelle installation");
    private JMenuItem menuSearchShowTables = new JMenuItem("Recherche dans toutes les tables");
    private JMenuItem menuSearchFirstCustomShow = new JMenuItem("1ère recherche personnalisée");
    private JMenuItem menuSearchSecondCustomShow = new JMenuItem("2ème recherche personnalisée");
    private JMenuItem menuModifyInstallationsDelete = new JMenuItem("Supprimer des installations");
    private WelcomePanel welcomePanel = new WelcomePanel(MainWindow);
    private ConnectionPanel connectionPanel = new ConnectionPanel(connection, bar, cont, welcomePanel);
    private NewInstallationPanel newInstallationPanel = new NewInstallationPanel(connection);
    private ShowTablesPanel showTablesPanel = new ShowTablesPanel(connection);
    private FirstCustomShowInstallationPanel firstCustomShowInstallationPanel = new FirstCustomShowInstallationPanel(
            connection);
    private SecondCustomShowInstallationPanel secondCustomShowInstallationPanel = new SecondCustomShowInstallationPanel(
            connection);
    private DeleteInstallationPanel deleteInstallationPanel = new DeleteInstallationPanel(connection);

    public MainWindow() throws SQLException {
        super("Gestion des installations de logiciels");
        setMinimumSize(new Dimension(1000, 800));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);

        bar.add(menuModifyInstallations);
        bar.add(menuSearch);
        bar.add(menuHelp);
        bar.setVisible(false);
        menuModifyInstallations.add(menuModifyInstallationsNew);
        menuModifyInstallations.add(menuModifyInstallationsDelete);
        menuSearch.add(menuSearchShowTables);
        menuSearch.add(menuSearchFirstCustomShow);
        menuSearch.add(menuSearchSecondCustomShow);
        menuHelp.add(menuHelpAbout);
        setJMenuBar(bar);

        cont.add(connectionPanel);
        setVisible(true);
        setResizable(false);

        actionManager actionListener = new actionManager();
        menuModifyInstallationsNew.addActionListener(actionListener);
        menuModifyInstallationsDelete.addActionListener(actionListener);
        menuSearchShowTables.addActionListener(actionListener);
        menuSearchFirstCustomShow.addActionListener(actionListener);
        menuSearchSecondCustomShow.addActionListener(actionListener);
        menuHelpAbout.addActionListener(actionListener);
    }

    private void switchPanel(JPanel panel) {
        cont.removeAll();
        cont.add(panel);
        cont.revalidate();
        cont.repaint();
    }

    private class actionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menuHelpAbout) {
                new AboutWindow();
            } else if (e.getSource() == menuModifyInstallationsNew) {
                switchPanel(newInstallationPanel);
            } else if (e.getSource() == menuSearchShowTables) {
                showTablesPanel.sqlRequest("Installation");
                switchPanel(showTablesPanel);
            } else if (e.getSource() == menuSearchFirstCustomShow) {
                switchPanel(firstCustomShowInstallationPanel);
            } else if (e.getSource() == menuSearchSecondCustomShow) {
                switchPanel(secondCustomShowInstallationPanel);
            } else if (e.getSource() == menuModifyInstallationsDelete) {
                switchPanel(deleteInstallationPanel);
            }
        }

    }
}
