package Serie4;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteInstallationPanel extends JPanel {
    private JLabel welcomeLabel,dummyLabel;
    private JButton findButton, deleteButton;
    private JTextField installationToDelete;
    public JTable myTable;
    private JScrollPane SQLtable;

    private Connection connection;

    public String codeSoftwareInput;

    private String[] options = { "Oui", "Non" };

    public DeleteInstallationPanel(Connection connection) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.connection = connection;

        welcomeLabel = new JLabel("Bienvenue dans le deletage");
        dummyLabel = new JLabel("empty");
        add(welcomeLabel);

        findButton = new JButton("Rechercher");
        installationToDelete = new JTextField(4);
        installationToDelete.setPreferredSize(new Dimension(500, 30));
        installationToDelete.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        deleteButton = new JButton("Supprimer");

        try {
            String sqlInstruction = "SELECT CodeSoftware,Annee,CodeSection from AnneeEtude JOIN UtilisationSoftware ON AnneeEtude.IdAnneeEtude=UtilisationSoftware.IdAnneeEtude;";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            myTable = new JTable(GenericModel);
            SQLtable = new JScrollPane(myTable);
            SQLtable.setPreferredSize(new Dimension(970, 300));
            add(SQLtable);
            add(findButton);
            add(dummyLabel);
            dummyLabel.setVisible(false);
        }
        catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }

        ScrollPaneManager SPmanager = new ScrollPaneManager();
        findButton.addActionListener(SPmanager);

        DeleteButtonManager DelBmanager = new DeleteButtonManager();
        deleteButton.addActionListener(DelBmanager);
    }
    private class ScrollPaneManager implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           codeSoftwareInput = myTable.getValueAt(myTable.getSelectedRow(),0).toString();
            JOptionPane.showConfirmDialog(null, "You Selected : " + codeSoftwareInput, "Display",
                    JOptionPane.PLAIN_MESSAGE);

            SQLRequest(codeSoftwareInput);

        }
    }

    private class DeleteButtonManager implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                int confirmDialog = JOptionPane.showOptionDialog(null,"Voulez vous supprimer l'installation n°" + installationToDelete.getText()+ " ?","", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
                if(confirmDialog == 0){
                    try {
                    String sqlInstruction = ("DELETE FROM Installation WHERE idInstallation = "+ installationToDelete.getText() + ";");
                    PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
                    prepStat.execute();
                        JOptionPane.showConfirmDialog(null, "La ligne a été effacée", "Display",
                                JOptionPane.PLAIN_MESSAGE);
                    } catch (SQLException o) {
                        System.out.println(o.getMessage());
                    }
                }
        }
    }
    public void SQLRequest(String codeSoftwareInput) {
        try {
            remove(getComponent(3));
            String sqlInstruction = ("SELECT IdInstallation,DateInstallation,Matricule,CodeSoftware " +
                    "FROM Installation WHERE CodeSoftware = '"+ codeSoftwareInput + "'");
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            JTable myTable = new JTable(GenericModel);
            JScrollPane SQLtableX = new JScrollPane(myTable);
            SQLtableX.setPreferredSize(new Dimension(970, 300));
            add(SQLtableX);
            add(installationToDelete);
            add(deleteButton);
            revalidate();
            repaint();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //TODO : SELECT IdInstallation,DateInstallation,Matricule,CodeSoftware FROM Installation choose and delete

}
