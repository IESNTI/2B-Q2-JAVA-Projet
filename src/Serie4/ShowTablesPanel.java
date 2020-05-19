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

public class ShowTablesPanel extends JPanel {
    private JLabel dummyLabel,changeTableLabel;
    private String showTables = "SHOW TABLES";

    private JButton findButton;
    private JTable requestTable,showTablesTable;

    private ValidationActionManager searchButtonListener = new ValidationActionManager();

    private Connection connection;

    public ShowTablesPanel(Connection connection) {
        this.connection = connection;
        setLayout(new FlowLayout(FlowLayout.CENTER));

        changeTableLabel = new JLabel("Changer de table :");
        add(changeTableLabel);

        getTablesInDB();
        findButton = new JButton("Rechercher");
        findButton.addActionListener(searchButtonListener);
        add(findButton);

        addDummyLabel();
    }

    // Reads and updates table on ComboBox input
    private class ValidationActionManager implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(showTablesTable.getSelectedRow() == -1)
            {
                JOptionPane.showConfirmDialog(null, "Veuillez sélectionner un élément de la liste", "", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                String tableInput = showTablesTable.getValueAt(showTablesTable.getSelectedRow(), 0).toString();
                sqlRequest(tableInput);
            }
        }
    }

    public void getTablesInDB(){
        try {
            PreparedStatement showTablePrepStat = connection.prepareStatement(showTables);
            TableModelGen showTableModel = AccessBDGen.creerTableModel(showTablePrepStat);
            showTablesTable = new JTable(showTableModel);
            JScrollPane sqlTables = new JScrollPane(showTablesTable);
            sqlTables.setPreferredSize(new Dimension(900, 300));
            add(sqlTables);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Requests table on string input
    public void sqlRequest(String table) {
        try {
            remove(getComponent(3));
            String sqlInstruction = "select * from " + table + ";";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            requestTable = new JTable(GenericModel);
            JScrollPane sqlTableX = new JScrollPane(requestTable);
            sqlTableX.setPreferredSize(new Dimension(900, 300));
            add(sqlTableX);
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