package Serie4;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import java.sql.Connection;

public class ShowInstallationPanel extends JPanel {
    private JLabel changeTableLabel;
    private String[] tables = {"Installation","AnneeEtude","Software","Editeur","FamilleSoftware","Fournisseur","OS",
            "Professeur","ResponsableReseaux","Section","SoftwarePreinstalle","TypePC","UtilisationSoftware"};
    private JComboBox tableSelect;
    private JTable myTable;
    private JScrollPane SQLtable;

    private Connection connection;

    public ShowInstallationPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        changeTableLabel = new JLabel("Changer de table :");
        this.add(changeTableLabel);
        tableSelect= new JComboBox(tables);
        tableSelect.setSelectedIndex(0);
        tableSelect.setMaximumRowCount(13);
        this.add(tableSelect);

        try {
            //Requests and shows SQL table
            String sqlInstruction = "select * from Installation ;";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            myTable = new JTable (GenericModel);
            SQLtable = new JScrollPane(myTable);
            SQLtable.setPreferredSize(new Dimension(900, 300));
            this.add(SQLtable);
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }

        CBoxListener itemTable = new CBoxListener();
        tableSelect.addItemListener(itemTable);
    }

    //Reads and updates table on ComboBox input
    private class CBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            SQLRequest(tables[tableSelect.getSelectedIndex()]);
        }
    }

    //Requests table on string input
    public void SQLRequest(String table){
        try {
            this.remove(this.getComponent(2));
            String sqlInstruction = "select * from " + table + ";";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            JTable myTable = new JTable (GenericModel);
            JScrollPane SQLtableX = new JScrollPane(myTable);
            SQLtableX.setPreferredSize(new Dimension(900, 300));
            this.add(SQLtableX);
            this.revalidate();
            this.repaint();
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}