package Serie4;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import java.sql.Connection;

public class ShowInstallationPanel extends JPanel {
    private JLabel welcomeLabel;
    private String[] tables = {"Installation","AnneeEtude","Editeur","FamilleSoftware","Fournisseur","OS",
            "Professeur","ResponsableReseaux","Section","SoftwarePreinstalle","TypePC","UtilisationSoftware"};
    private JComboBox tableSelect;
    public ShowInstallationPanel(Connection connection) {
        this.setLayout(new GridLayout(3,2));
        welcomeLabel = new JLabel("Affichage des nouvelles installations");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(welcomeLabel);
        tableSelect= new JComboBox(tables);
        tableSelect.setSelectedIndex(0);
        tableSelect.setMaximumRowCount(15);

        this.add(tableSelect);
        try {
            String sqlInstruction = "select * from Installation ";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen InstallationsModel = AccessBDGen.creerTableModel(prepStat);
            JTable myTable = new JTable (InstallationsModel);
            //myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            JScrollPane defilant = new JScrollPane(myTable);
            this.add(defilant);
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
