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

public class ShowTablesPanel extends JPanel {
    private JLabel changeTableLabel;
    private String[] tables = { "Installation", "AnneeEtude", "Software", "Editeur", "FamilleSoftware", "Fournisseur",
            "OS", "Professeur", "ResponsableReseaux", "Section", "SoftwarePreinstalle", "TypePC",
            "UtilisationSoftware" };
    private JComboBox tableSelect;
    private JTable myTable;
    private JScrollPane SQLtable;

    private Connection connection;

    public ShowTablesPanel(Connection connection) {
        this.connection = connection;
        setLayout(new FlowLayout(FlowLayout.CENTER));

        changeTableLabel = new JLabel("Changer de table :");
        add(changeTableLabel);
        tableSelect = new JComboBox(tables);
        tableSelect.setSelectedIndex(0);
        tableSelect.setMaximumRowCount(13);
        add(tableSelect);

        try {
            // Requests and shows SQL table
            String sqlInstruction = "select * from Installation ;";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            myTable = new JTable(GenericModel);
            SQLtable = new JScrollPane(myTable);
            SQLtable.setPreferredSize(new Dimension(900, 300));
            add(SQLtable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        CBoxListener itemTable = new CBoxListener();
        tableSelect.addItemListener(itemTable);
    }

    // Reads and updates table on ComboBox input
    private class CBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            SQLRequest(tables[tableSelect.getSelectedIndex()]);
        }
    }

    // Requests table on string input
    public void SQLRequest(String table) {
        try {
            remove(getComponent(2));
            String sqlInstruction = "select * from " + table + ";";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            JTable myTable = new JTable(GenericModel);
            JScrollPane SQLtableX = new JScrollPane(myTable);
            SQLtableX.setPreferredSize(new Dimension(900, 300));
            add(SQLtableX);
            revalidate();
            repaint();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}