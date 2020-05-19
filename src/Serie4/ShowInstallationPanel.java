package Serie4;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import java.sql.Connection;

public class ShowInstallationPanel extends JPanel {
    private JLabel dummyLabel,changeTableLabel;
    private String showTables = "SHOW TABLES";

    private JButton findButton;
    private JTable requestTable,showTablesTable;
    private JScrollPane SQLtable;

    private ValidationActionManager searchButtonListener = new ValidationActionManager();

    private Connection connection;

    public ShowInstallationPanel(Connection connection) {
        this.connection = connection;
        setLayout(new FlowLayout(FlowLayout.CENTER));

        changeTableLabel = new JLabel("Changer de table :");
        add(changeTableLabel);

        GetTablesInDB();
        findButton = new JButton("Rechercher");
        findButton.addActionListener(searchButtonListener);
        add(findButton);

        AddsDummyLabel();
    }

    // Reads and updates table on ComboBox input
    private class ValidationActionManager implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String tableInput = showTablesTable.getValueAt(showTablesTable.getSelectedRow(),0).toString();
            SQLRequest(tableInput);
        }
    }

    public void GetTablesInDB(){
        try {
            PreparedStatement showTablePrepStat = connection.prepareStatement(showTables);
            TableModelGen showTableModel = AccessBDGen.creerTableModel(showTablePrepStat);
            showTablesTable = new JTable(showTableModel);
            JScrollPane SQLtables = new JScrollPane(showTablesTable);
            SQLtables.setPreferredSize(new Dimension(900, 300));
            add(SQLtables);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Requests table on string input
    public void SQLRequest(String table) {
        try {
            remove(getComponent(3));
            String sqlInstruction = "select * from " + table + ";";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            requestTable = new JTable(GenericModel);
            JScrollPane SQLtableX = new JScrollPane(requestTable);
            SQLtableX.setPreferredSize(new Dimension(900, 300));
            add(SQLtableX);
            revalidate();
            repaint();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void AddsDummyLabel(){
        dummyLabel = new JLabel("empty");
        dummyLabel.setVisible(false);
        add(dummyLabel);
    }
}