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
    private JLabel change;
    private String[] tables = {"Installation","AnneeEtude","Editeur","FamilleSoftware","Fournisseur","OS",
            "Professeur","ResponsableReseaux","Section","SoftwarePreinstalle","TypePC","UtilisationSoftware"};
    private JComboBox tableSelect;
    private JTable myTable;
    private JScrollPane SQLtable;
    public ShowInstallationPanel(Connection connection) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        change = new JLabel("Changer de table :");
        this.add(change);
        tableSelect= new JComboBox(tables);
        tableSelect.setSelectedIndex(0);
        tableSelect.setMaximumRowCount(12);
        this.add(tableSelect);
        try {
            String sqlInstruction = "select * from Installation ";
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
    private class CBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {

            switch (tableSelect.getSelectedIndex()) {
                case 0:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[0]);
                        break;
                case 1:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[1]);
                        break;
                case 2:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[2]);
                        break;
                case 3:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[3]);
                    break;
                case 4:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[4]);
                    break;
                case 5:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[5]);
                    break;
                case 6:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[6]);
                    break;
                case 7:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[7]);
                    break;
                case 8:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[8]);
                    break;
                case 9:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[9]);
                    break;
                case 10:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[10]);
                    break;
                case 11:
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        SQLRequest(tables[11]);
                    break;
            }
        }
    }
    public void SQLRequest(String table){
        try {
            this.remove(SQLtable);

            JOptionPane.showMessageDialog(null, ""+table, "", JOptionPane.WARNING_MESSAGE);
            String sqlInstruction = "select * from " + table;
            PreparedStatement prepStat = AccessBDGen.connecter("DbInstallations", "java", "b88iBowEv5te").prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            JTable myTable = new JTable (GenericModel);
            JScrollPane SQLtableX = new JScrollPane(myTable);
            this.remove(SQLtableX);
            SQLtableX.setPreferredSize(new Dimension(900, 300));
            this.add(SQLtableX);
            SQLtableX.repaint();

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}