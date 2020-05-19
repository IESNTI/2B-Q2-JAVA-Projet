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

public class FirstCustomShowInstallationPanel extends JPanel {
    private JLabel dummyLabel,tableSoftLabel,dateInstallationLabel;
    private JButton findButton;

    private UtilDateModel dateInstallationModel;
    private JDatePanelImpl dateInstallationPanel;
    private JDatePickerImpl dateInstallationPicker;

    private JTable softFamTable,requestTable;
    private JScrollPane SQLtable;

    private validationActionManager findButtonListener = new validationActionManager();

    private Connection connection;

    public FirstCustomShowInstallationPanel(Connection connection) {
        this.connection = connection;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        //welcomeLabel = new JLabel("Bienvenue dans la recherche custom des install");
        //add(welcomeLabel);

        dateInstallationLabel = new JLabel("Date de l'installation (requis) : ");
        dateInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(dateInstallationLabel);

        dateInstallationModel = new UtilDateModel();
        dateInstallationPanel = new JDatePanelImpl(dateInstallationModel);
        dateInstallationPicker = new JDatePickerImpl(dateInstallationPanel);
        add(dateInstallationPicker);
        add(Box.createRigidArea(new Dimension(120, 0)));
        findButton = new JButton("Rechercher");
        findButton.addActionListener(findButtonListener);
        add(findButton);

        add(Box.createRigidArea(new Dimension(300, 0)));

        tableSoftLabel = new JLabel("Famille de software (requis) : ");
        add(tableSoftLabel);
        //SQLRequest("SELECT * FROM FamilleSoftware",450,230,softFamTable);
        try {
            // Requests and shows SQL table
            String sqlInstruction = "SELECT * FROM FamilleSoftware ;";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            TableModelGen GenericModel = AccessBDGen.creerTableModel(prepStat);
            softFamTable = new JTable(GenericModel);
            JScrollPane SQLtable = new JScrollPane(softFamTable);
            SQLtable.setPreferredSize(new Dimension(450, 230));
            hideColumn(softFamTable);
            add(SQLtable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        dummyLabel = new JLabel("empty");
        dummyLabel.setVisible(false);
        add(dummyLabel);

    }
    private class validationActionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Date dateInstallationSQLDate = (Date) dateInstallationPicker.getModel().getValue();
            String idFamSoftLibelleInput = softFamTable.getValueAt(softFamTable.getSelectedRow(),0).toString();
            String instruction = "SELECT IdInstallation,DateInstallation,TypeInstallation,Commentaires,DureeInstallation," +
                    "RefProcedureInstallation,Validation,DateValidation,Matricule,CodeOS,idFamSoft FROM Installation " +
                    "JOIN Software ON Installation.CodeSoftware=Software.CodeSoftware " +
                    "WHERE idFamSoft = " + idFamSoftLibelleInput ;
            SQLRequest(instruction,900,300,dateInstallationSQLDate);
        }

    }
    public void SQLRequest(String instruction, int w, int h,Date date)
    {
        try {
            // Requests and shows SQL table
            remove(getComponent(7));
            String sqlInstruction = instruction + " AND DateInstallation < ? ;";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            prepStat.setDate(1, new java.sql.Date(date.getTime()));
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
