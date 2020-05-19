package Serie4;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField.AbstractFormatter;

public class SecondCustomShowInstallationPanel extends JPanel {
    private JLabel dummyLabel,editorTableLabel;
    private JButton findButton;
    private JTable editorTable,requestTable;
    private JScrollPane SQLtable;

    private validationActionManager findButtonListener = new validationActionManager();

    private Connection connection;

    public SecondCustomShowInstallationPanel(Connection connection) {
        this.connection = connection;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        editorTableLabel = new JLabel("Sélectionnez un éditeur à chercher : ");
        add(editorTableLabel);

        try {
            // Requests and shows SQL table
            String sqlInstruction = "SELECT CodeEdit,Designation FROM Editeur;";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            editorTable = new JTable(GenericModel);
            JScrollPane SQLtable = new JScrollPane(editorTable);
            SQLtable.setPreferredSize(new Dimension(450, 230));
            hideColumn(editorTable);
            add(SQLtable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        findButton = new JButton("Rechercher");
        findButton.addActionListener(findButtonListener);
        add(findButton);

        dummyLabel = new JLabel("empty");
        dummyLabel.setVisible(false);
        add(dummyLabel);
    }
    private class validationActionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String editorInput = editorTable.getValueAt(editorTable.getSelectedRow(), 0).toString();
            String instruction = "SELECT * FROM Software WHERE CodeEdit = '" + editorInput + "'";
            SQLRequest(instruction, 900, 300);
        }
    }
    public void SQLRequest(String instruction, int w, int h)
    {
        try {
            // Requests and shows SQL table
            remove(getComponent(3));
            String sqlInstruction = instruction + ";";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            requestTable = new JTable(GenericModel);
            JScrollPane SQLtableX = new JScrollPane(requestTable);
            SQLtableX.setPreferredSize(new Dimension(w, h));
            add(SQLtableX);
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
}
