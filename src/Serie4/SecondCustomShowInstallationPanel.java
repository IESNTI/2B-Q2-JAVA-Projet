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

public class SecondCustomShowInstallationPanel extends JPanel {
    private JLabel dummyLabel,editorTableLabel;
    private JButton findButton;
    private JTable editorTable,requestTable;

    private ValidationActionManager findButtonListener = new ValidationActionManager();

    private Connection connection;

    public SecondCustomShowInstallationPanel(Connection connection) {
        this.connection = connection;
        setLayout(new FlowLayout(FlowLayout.CENTER));

        editorTableLabel = new JLabel("Sélectionnez un éditeur à chercher : ");
        add(editorTableLabel);

        editorRequest();

        findButton = new JButton("Rechercher");
        findButton.addActionListener(findButtonListener);
        add(findButton);

        addDummyLabel();
    }

    private class ValidationActionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String editorInput = editorTable.getValueAt(editorTable.getSelectedRow(), 0).toString();
            String instruction = "SELECT * FROM Software WHERE CodeEdit = '" + editorInput + "'";
            sqlRequest(instruction, 900, 300);
        }
    }

    public void editorRequest(){
        try {
            String sqlInstruction = "SELECT CodeEdit,Designation FROM Editeur;";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            editorTable = new JTable(GenericModel);
            JScrollPane sqlTable = new JScrollPane(editorTable);
            sqlTable.setPreferredSize(new Dimension(450, 230));
            hideColumn(editorTable);
            add(sqlTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sqlRequest(String instruction, int w, int h)
    {
        try {
            // Requests and shows SQL table
            remove(getComponent(3));
            String sqlInstruction = instruction + ";";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            requestTable = new JTable(GenericModel);
            JScrollPane sqlTableX = new JScrollPane(requestTable);
            sqlTableX.setPreferredSize(new Dimension(w, h));
            add(sqlTableX);
            revalidate();
            repaint();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void hideColumn(JTable table){
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setResizable(false);
    }

    public void addDummyLabel(){
        dummyLabel = new JLabel("empty");
        dummyLabel.setVisible(false);
        add(dummyLabel);
    }
}
