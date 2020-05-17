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
    private JLabel welcomeLabel;
    public JTable myTable;
    private JScrollPane SQLtable;

    public DeleteInstallationPanel(Connection connection) {
        welcomeLabel = new JLabel("Bienvenue dans le deletage");
        this.add(welcomeLabel);
        JButton btn = new JButton("Get selected");
        this.add(btn);
        try {
            String sqlInstruction = "SELECT CodeSoftware,Annee,CodeSection from AnneeEtude JOIN UtilisationSoftware ON AnneeEtude.IdAnneeEtude=UtilisationSoftware.IdAnneeEtude;";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            myTable = new JTable(GenericModel);
            SQLtable = new JScrollPane(myTable);
            SQLtable.setPreferredSize(new Dimension(900, 300));
            this.add(SQLtable);
        }
        catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
        ScrollPaneManager manager = new ScrollPaneManager();
        btn.addActionListener(manager);
    }
    private class ScrollPaneManager implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showConfirmDialog(null, "You Selected : " + myTable.getValueAt(myTable.getSelectedRow(),0), "Display",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
    //TODO : SELECT IdInstallation,DateInstallation,Matricule,CodeSoftware FROM Installation choose and delete

}
