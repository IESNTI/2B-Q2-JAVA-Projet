package Serie4;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteInstallationPanel extends JPanel {
    private JLabel chooseOneLabel,deleteLabel,dummyLabel;
    private JButton findButton, deleteButton;
    private JTextField installationToDelete;
    public JTable codeSoftwareTable,requestTable;
    private JScrollPane sqlTable;

    private ScrollPaneManager scrollPaneManager = new ScrollPaneManager();
    private DeleteButtonManager delButtonManager = new DeleteButtonManager();

    public String codeSoftwareInput;
    private String[] confirmOptions = { "Oui", "Non" };

    private Connection connection;

    public DeleteInstallationPanel(Connection connection) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.connection = connection;

        chooseOneLabel = new JLabel("Choisissez l'année et l'option à rechercher.");
        add(chooseOneLabel);

        findButton = new JButton("Rechercher");
        findButton.addActionListener(scrollPaneManager);

        deleteLabel = new JLabel("Veuillez encoder le numéro d'idInstallation à supprimer :   ");
        installationToDelete = new JTextField(4);
        installationToDelete.setPreferredSize(new Dimension(500, 30));
        installationToDelete.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(delButtonManager);
        codeSoftwareRequest();

    }
    private class ScrollPaneManager implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(codeSoftwareTable.getSelectedRow() == -1)
            {
                JOptionPane.showConfirmDialog(null, "Veuillez sélectionner un élément de la liste", "", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                codeSoftwareInput = codeSoftwareTable.getValueAt(codeSoftwareTable.getSelectedRow(), 0).toString();
                sqlRequest(codeSoftwareInput);
            }
        }
    }

    private class DeleteButtonManager implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int confirmDialog = JOptionPane.showOptionDialog(null,"Voulez vous supprimer l'installation n°" + installationToDelete.getText()+ " ?","", 0,JOptionPane.INFORMATION_MESSAGE,null, confirmOptions,null);
            if(confirmDialog == 0){
                try {
                    String sqlRequestInstruction = ("SELECT * FROM Installation WHERE idInstallation = "+ installationToDelete.getText() + ";");
                    PreparedStatement myPrepStat = connection.prepareStatement(sqlRequestInstruction);
                    ResultSet resultSet = myPrepStat.executeQuery();
                    if (resultSet.next()) {
                        String sqlDeleteInstruction = ("DELETE FROM Installation WHERE idInstallation = " + installationToDelete.getText() + ";");
                        PreparedStatement prepStat = connection.prepareStatement(sqlDeleteInstruction);
                        prepStat.execute();
                        JOptionPane.showConfirmDialog(null, "La ligne a été effacée", "Display", JOptionPane.PLAIN_MESSAGE);
                        sqlRequest(codeSoftwareInput);
                    }
                    else {
                        JOptionPane.showConfirmDialog(null, "Le idInstallation que vous avez encodé n'existe pas.", "Display", JOptionPane.PLAIN_MESSAGE);
                    }
                    } catch (SQLException o) {
                        System.out.println(o.getMessage());
                    }
                }
        }
    }

    public void codeSoftwareRequest(){
        try {
            String sqlInstruction = "SELECT CodeSoftware,Annee,CodeSection from AnneeEtude JOIN UtilisationSoftware ON AnneeEtude.IdAnneeEtude=UtilisationSoftware.IdAnneeEtude;";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            codeSoftwareTable = new JTable(GenericModel);
            sqlTable = new JScrollPane(codeSoftwareTable);
            sqlTable.setPreferredSize(new Dimension(970, 300));
            add(sqlTable);
            add(findButton);
            addDummyLabel();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sqlRequest(String codeSoftwareInput) {
        try {
            remove(getComponent(3));
            String sqlInstruction = ("SELECT IdInstallation,DateInstallation,Matricule,CodeSoftware " +
                    "FROM Installation WHERE CodeSoftware = '"+ codeSoftwareInput + "'");
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            requestTable = new JTable(GenericModel);
            JScrollPane sqlTableX = new JScrollPane(requestTable);
            sqlTableX.setPreferredSize(new Dimension(970, 300));
            add(sqlTableX);
            add(deleteLabel);
            add(installationToDelete);
            add(deleteButton);
            revalidate();
            repaint();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addDummyLabel(){
        dummyLabel = new JLabel("empty");
        dummyLabel.setVisible(false);
        add(dummyLabel);
    }
}
