package Serie4;

import accessBD.AccessBDGen;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.TimeZone;

public class NewInstallationPanel extends JPanel {

    private JLabel dateInstallationLabel, typeInstallationLabel, commentairesLabel, dureeInstallationLabel,
            refProcedureInstallationLabel;
    private JLabel validationLabel, dateValidationLabel, codeSoftwareLabel, matriculeLabel, codeOSLabel;
    private JTextField refProcedureInstallationTextField;
    private JComboBox typeInstallationComboBox, codeSoftwareComboBox, matriculeComboBox, codeOSComboBox;
    private JButton validateButton, cancelButton;
    private ButtonGroup validationButtonGroup;
    private JPanel validationButtonPanel;
    private JTextArea commentairesTextArea;
    private UtilDateModel dateInstallationModel, dateValidationModel;
    private JDatePanelImpl dateInstallationPanel, dateValidationPanel;
    private JDatePickerImpl dateInstallationPicker, dateValidationPicker;
    ArrayList<String> typeInstallationArrayList = new ArrayList<String>(Arrays.asList("Standard", "Personnalisée"));
    private actionManager actionListener = new actionManager();
    private validationActionManager validationActionListener = new validationActionManager();
    private JSpinner timeSpinner;
    private JSpinner.DateEditor timeEditor;
    private String[] options = { "Oui", "Non" };
    private Connection connection;

    public NewInstallationPanel(Connection connection) throws SQLException {
        this.connection = connection;
        setLayout(new GridLayout(12, 2));
        setPreferredSize(new Dimension(400, 300));
        addElements();
    }

    void addElements() throws SQLException {
        dateInstallationLabel = new JLabel("Date de l'installation (requis) : ");
        // dateInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(dateInstallationLabel);
        dateInstallationModel = new UtilDateModel();
        dateInstallationPanel = new JDatePanelImpl(dateInstallationModel);
        dateInstallationPicker = new JDatePickerImpl(dateInstallationPanel);
        dateInstallationPicker.addActionListener(validationActionListener);
        add(dateInstallationPicker);

        typeInstallationLabel = new JLabel("Type de l'installation (requis) : ");
        // typeInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(typeInstallationLabel);
        typeInstallationComboBox = new JComboBox(typeInstallationArrayList.toArray());
        typeInstallationComboBox.setSelectedIndex(-1);
        typeInstallationComboBox.addActionListener(validationActionListener);
        add(typeInstallationComboBox);

        commentairesLabel = new JLabel("Commentaires: ");
        // commentairesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(commentairesLabel);
        commentairesTextArea = new JTextArea(2, 2);
        commentairesTextArea.addKeyListener(commentairesKeyListener);
        add(commentairesTextArea);

        dureeInstallationLabel = new JLabel("Durée de l'installation (heure:minute) : ");
        // dureeInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(dureeInstallationLabel);
        timeSpinner = new JSpinner(new SpinnerDateModel(new Date(0), null, null, Calendar.MINUTE));
        timeEditor = new JSpinner.DateEditor(timeSpinner, "hh:mm");
        timeSpinner.setEditor(timeEditor);
        // timeSpinner.setValue(new Date(0));
        timeEditor.getTextField().setEditable(false);
        add(timeSpinner);

        refProcedureInstallationLabel = new JLabel("Référence de la procédure d'installation : ");
        // refProcedureInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(refProcedureInstallationLabel);
        refProcedureInstallationTextField = new JTextField(50);
        refProcedureInstallationTextField.addKeyListener(refProcedureInstallationKeyListener);
        add(refProcedureInstallationTextField);

        validationLabel = new JLabel("Validation (requis) : ");
        // validationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(validationLabel);
        String validationButtonList[] = { "A prevoir", "Terminee", "En cours" };
        validationButtonGroup = new ButtonGroup();
        validationButtonPanel = new JPanel();
        for (int i = 0; i < validationButtonList.length; i++) {
            JRadioButton validationButton = new JRadioButton(validationButtonList[i]);
            validationButton.addActionListener(validationActionListener);
            validationButtonGroup.add(validationButton);
            validationButtonPanel.add(validationButton);
        }
        add(validationButtonPanel);

        dateValidationLabel = new JLabel("Date de la validation : ");
        // dateValidationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(dateValidationLabel);
        dateValidationModel = new UtilDateModel();
        dateValidationPanel = new JDatePanelImpl(dateValidationModel);
        dateValidationPicker = new JDatePickerImpl(dateValidationPanel);
        add(dateValidationPicker);

        codeSoftwareLabel = new JLabel("Logiciel (requis) : ");
        // codeSoftwareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(codeSoftwareLabel);
        String codeSoftwareSQLInstruction = "SELECT Nom FROM Software;";
        PreparedStatement codeSoftwarePrepStat = connection.prepareStatement(codeSoftwareSQLInstruction);
        Object[] codeSoftwareSQLResult = AccessBDGen.creerListe1Colonne(codeSoftwarePrepStat);
        codeSoftwareComboBox = new JComboBox(codeSoftwareSQLResult);
        codeSoftwareComboBox.setSelectedIndex(-1);
        codeSoftwareComboBox.addActionListener(validationActionListener);
        add(codeSoftwareComboBox);

        matriculeLabel = new JLabel("Matricule (requis) : ");
        // matriculeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(matriculeLabel);
        String matriculeSQLInstruction = "SELECT NomPrenom FROM ResponsableReseaux;";
        PreparedStatement matriculePrepStat = connection.prepareStatement(matriculeSQLInstruction);
        Object[] matriculeSQLResult = AccessBDGen.creerListe1Colonne(matriculePrepStat);
        matriculeComboBox = new JComboBox(matriculeSQLResult);
        matriculeComboBox.setSelectedIndex(-1);
        matriculeComboBox.addActionListener(validationActionListener);
        add(matriculeComboBox);

        codeOSLabel = new JLabel("OS (requis) : ");
        // codeOSLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(codeOSLabel);
        String codeOSSQLInstruction = "SELECT Libelle FROM OS;";
        PreparedStatement codeOSPrepStat = connection.prepareStatement(codeOSSQLInstruction);
        Object[] codeOSSQLResult = AccessBDGen.creerListe1Colonne(codeOSPrepStat);
        codeOSComboBox = new JComboBox(codeOSSQLResult);
        codeOSComboBox.setSelectedIndex(-1);
        codeOSComboBox.addActionListener(validationActionListener);
        add(codeOSComboBox);

        validateButton = new JButton("Valider");
        validateButton.addActionListener(actionListener);
        add(validateButton);

        cancelButton = new JButton("Réinitialiser");
        cancelButton.addActionListener(actionListener);
        add(cancelButton);

        validateElements();
    }

    private class actionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == validateButton) {
                if (validateElements())
                    submitForm();
                else
                    JOptionPane.showMessageDialog(null,"Veuillez vérifier que vous avez bien rempli tous les champs requis (en rouge).","", JOptionPane.INFORMATION_MESSAGE,null);
            } else if (e.getSource() == cancelButton) {
                int confirmDialog = JOptionPane.showOptionDialog(null,"Voulez vous réinitialiser tous les champs?","", 0,JOptionPane.WARNING_MESSAGE,null,options,null);
                if(confirmDialog == 0){
                    removeAll();
                    try {
                        addElements();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    revalidate();
                    repaint();
                }

            }
        }

    }

    void refreshPanel() {
        removeAll();
        try {
            addElements();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        revalidate();
        repaint();
    }

    void submitForm() {
        try {
            String sqlInstructionSubmitForm = "insert into Installation "
                    + "(IdInstallation, DateInstallation, TypeInstallation, Commentaires, DureeInstallation, RefProcedureInstallation, Validation, DateValidation, CodeSoftware, Matricule, CodeOS) "
                    + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement myPrepStatSubmitForm = connection.prepareStatement(sqlInstructionSubmitForm);

            String idInstallationSQLInstruction = "SELECT MAX(IdInstallation) FROM Installation;";
            PreparedStatement idInstallationPrepStat = connection.prepareStatement(idInstallationSQLInstruction);
            Object[] idInstallationSQLResult = AccessBDGen.creerListe1Colonne(idInstallationPrepStat);
            myPrepStatSubmitForm.setInt(1, ((int) idInstallationSQLResult[0]) + 1);

            Date dateInstallationSQLDate = (Date) dateInstallationPicker.getModel().getValue();
            myPrepStatSubmitForm.setDate(2, new java.sql.Date(dateInstallationSQLDate.getTime()));

            String typeInstallationSelectedItem = ((JTextField) typeInstallationComboBox.getEditor()
                    .getEditorComponent()).getText();
            myPrepStatSubmitForm.setInt(3, typeInstallationArrayList.indexOf(typeInstallationSelectedItem));

            myPrepStatSubmitForm.setString(4, commentairesTextArea.getText());

            SimpleDateFormat dureeInstallationFormat = new SimpleDateFormat("HH:mm");
            dureeInstallationFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date dureeInstallation = new Date();
            try {
                dureeInstallation = dureeInstallationFormat
                        .parse(timeEditor.getFormat().format(timeSpinner.getValue()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            myPrepStatSubmitForm.setInt(5, (int) (dureeInstallation.getTime() / 1000));

            myPrepStatSubmitForm.setString(6, refProcedureInstallationTextField.getText());

            myPrepStatSubmitForm.setString(7, getSelectedButtonText(validationButtonGroup));

            if (dateValidationPicker.getModel().getValue() == null) {
                myPrepStatSubmitForm.setNull(8, Types.DATE);
            } else {
                Date dateValidation = (Date) dateValidationPicker.getModel().getValue();
                java.sql.Date dateValidationSQLDate = new java.sql.Date(dateValidation.getTime());
                myPrepStatSubmitForm.setDate(8, dateValidationSQLDate);
            }

            String codeSoftwareSQLInstruction = "SELECT CodeSoftware FROM Software WHERE Nom = ?;";
            PreparedStatement codeSoftwarePrepStat = connection.prepareStatement(codeSoftwareSQLInstruction);
            String codeSoftwareSelectedItem = ((JTextField) codeSoftwareComboBox.getEditor().getEditorComponent())
                    .getText();
            codeSoftwarePrepStat.setString(1, codeSoftwareSelectedItem);
            Object[] codeSoftwareSQLResult = AccessBDGen.creerListe1Colonne(codeSoftwarePrepStat);
            myPrepStatSubmitForm.setString(9, (String) codeSoftwareSQLResult[0]);

            String matriculeSQLInstruction = "SELECT Matricule FROM ResponsableReseaux WHERE NomPrenom = ?;";
            PreparedStatement matriculePrepStat = connection.prepareStatement(matriculeSQLInstruction);
            String matriculeSelectedItem = ((JTextField) matriculeComboBox.getEditor().getEditorComponent()).getText();
            matriculePrepStat.setString(1, matriculeSelectedItem);
            Object[] matriculeSQLResult = AccessBDGen.creerListe1Colonne(matriculePrepStat);
            myPrepStatSubmitForm.setString(10, (String) matriculeSQLResult[0]);

            String codeOSSQLInstruction = "SELECT CodeOS FROM OS WHERE Libelle = ?;";
            PreparedStatement codeOSPrepStat = connection.prepareStatement(codeOSSQLInstruction);
            String codeOSSelectedItem = ((JTextField) codeOSComboBox.getEditor().getEditorComponent()).getText();
            codeOSPrepStat.setString(1, codeOSSelectedItem);
            Object[] codeOSSQLResult = AccessBDGen.creerListe1Colonne(codeOSPrepStat);
            myPrepStatSubmitForm.setString(11, (String) codeOSSQLResult[0]);

            myPrepStatSubmitForm.executeUpdate();
            JOptionPane.showMessageDialog(null,"Vos changements ont bien été effectués !","", JOptionPane.INFORMATION_MESSAGE,null);
            refreshPanel();
        } catch (SQLException e) {
            // print message d'erreur
            e.printStackTrace();
        }
    }

    private class validationActionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            validateElements();
        }

    }

    private boolean validateElements() {
        Boolean areRequiredElementsCompleted = true;

        JComboBox[] requiredComboBoxes = new JComboBox[] { typeInstallationComboBox, codeSoftwareComboBox,
                matriculeComboBox, codeOSComboBox };
        for (JComboBox requiredComboBox : requiredComboBoxes) {
            if (((JTextField) requiredComboBox.getEditor().getEditorComponent()).getText().length() == 0) {
                requiredComboBox.setBackground(Color.red);
                areRequiredElementsCompleted = false;
            } else {
                requiredComboBox.setBackground(null);
            }
        }

        if (dateInstallationPicker.getJFormattedTextField().getText() != null
                && dateInstallationPicker.getJFormattedTextField().getText().isEmpty()) {
            dateInstallationPicker.getJFormattedTextField().setBackground(Color.red);
            areRequiredElementsCompleted = false;
        } else {
            dateInstallationPicker.getJFormattedTextField().setBackground(null);
        }

        if (getSelectedButtonText(validationButtonGroup) == null) {
            validationButtonPanel.setBackground(Color.red);
            areRequiredElementsCompleted = false;
        } else {
            validationButtonPanel.setBackground(null);
        }

        return areRequiredElementsCompleted;
    }

    private KeyListener refProcedureInstallationKeyListener = new KeyListener() {
        public void keyPressed(KeyEvent keyEvent) {
        }

        public void keyReleased(KeyEvent keyEvent) {
        }

        public void keyTyped(KeyEvent e) {
            int max = 50;
            if (refProcedureInstallationTextField.getText().length() > max + 1) {
                e.consume();
                String shortened = refProcedureInstallationTextField.getText().substring(0, max);
                refProcedureInstallationTextField.setText(shortened);
            } else if (refProcedureInstallationTextField.getText().length() > max) {
                e.consume();
            }
        }
    };

    private KeyListener commentairesKeyListener = new KeyListener() {
        public void keyPressed(KeyEvent keyEvent) {
        }

        public void keyReleased(KeyEvent keyEvent) {
        }

        public void keyTyped(KeyEvent e) {
            int max = 100;
            if (commentairesTextArea.getText().length() > max + 1) {
                e.consume();
                String shortened = commentairesTextArea.getText().substring(0, max);
                commentairesTextArea.setText(shortened);
            } else if (commentairesTextArea.getText().length() > max) {
                e.consume();
            }
        }
    };

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
}
