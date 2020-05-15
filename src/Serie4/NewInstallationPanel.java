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
import java.util.Date;

public class NewInstallationPanel extends JPanel {

    private JLabel idInstallationLabel, dateInstallationLabel, typeInstallationLabel, commentairesLabel,
            dureeInstallationLabel, refProcedureInstallationLabel;
    private JLabel validationLabel, dateValidationLabel, codeSoftwareLabel, matriculeLabel, codeOSLabel;
    private JTextField refProcedureInstallationTextField;
    private JComboBox typeInstallationComboBox, codeSoftwareComboBox, matriculeComboBox, codeOSComboBox;
    private JButton validateButton, cancelButton;
    private ButtonGroup validationButtonGroup;
    private JTextArea commentairesTextArea;
    private UtilDateModel model = new UtilDateModel();
    private JDatePanelImpl datePanel = new JDatePanelImpl(model);
    private JDatePickerImpl dateInstallationPicker, dateValidationPicker;
    private actionManager actionListener = new actionManager();
    private validationManager validationListener = new validationManager();
    private Connection connection;

    public NewInstallationPanel(Connection connection) throws SQLException {
        this.connection = connection;
        this.setLayout(new GridLayout(14, 10, 1, 1));

        // idInstallationLabel = new JLabel("ID de l'installation: ");
        // // idInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // this.add(idInstallationLabel);
        // idInstallationTextField = new JTextField(30);
        // idInstallationTextField.getDocument().addDocumentListener(validationListener);
        // this.add(idInstallationTextField);

        dateInstallationLabel = new JLabel("Date de l'installation: ");
        // dateInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(dateInstallationLabel);
        dateInstallationPicker = new JDatePickerImpl(datePanel);
        this.add(dateInstallationPicker);

        typeInstallationLabel = new JLabel("Type de l'installation: ");
        // typeInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(typeInstallationLabel);
        String typeInstallationList[] = { "Standard", "Personnalisée" };
        typeInstallationComboBox = new JComboBox(typeInstallationList);
        typeInstallationComboBox.setSelectedIndex(-1);
        ((JTextField)typeInstallationComboBox.getEditor().getEditorComponent()).getDocument().addDocumentListener(validationListener);
        this.add(typeInstallationComboBox);

        commentairesLabel = new JLabel("Commentaires: ");
        // commentairesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(commentairesLabel);
        commentairesTextArea = new JTextArea(2, 2);
        commentairesTextArea.addKeyListener(commentairesKeyListener);
        this.add(commentairesTextArea);

        dureeInstallationLabel = new JLabel("Durée de l'installation (HH:mm:ss): ");
        // dureeInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(dureeInstallationLabel);
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date(0));
        timeEditor.getTextField().setEditable(false);
        this.add(timeSpinner);

        refProcedureInstallationLabel = new JLabel("Référence de la procédure d'installation: ");
        // refProcedureInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(refProcedureInstallationLabel);
        refProcedureInstallationTextField = new JTextField(50);
        refProcedureInstallationTextField.addKeyListener(refProcedureInstallationKeyListener);
        refProcedureInstallationTextField.getDocument().addDocumentListener(validationListener);
        this.add(refProcedureInstallationTextField);

        validationLabel = new JLabel("Validation: ");
        // validationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(validationLabel);
        String validationButtonList[] = { "A prevoir", "Terminee", "En cours" };
        validationButtonGroup = new ButtonGroup();
        for (int i = 0; i < validationButtonList.length; i++) {
            JRadioButton validationButton = new JRadioButton(validationButtonList[i]);
            validationButtonGroup.add(validationButton);
            this.add(validationButton);
        }

        dateValidationLabel = new JLabel("Date de la validation: ");
        // dateValidationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(dateValidationLabel);
        dateValidationPicker = new JDatePickerImpl(datePanel);
        this.add(dateValidationPicker);

        codeSoftwareLabel = new JLabel("Logiciel: ");
        // codeSoftwareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(codeSoftwareLabel);
        String codeSoftwareSQLInstruction = "SELECT Nom FROM Software;";
        PreparedStatement codeSoftwarePrepStat = connection.prepareStatement(codeSoftwareSQLInstruction);
        Object[] codeSoftwareSQLResult = AccessBDGen.creerListe1Colonne(codeSoftwarePrepStat);
        codeSoftwareComboBox = new JComboBox(codeSoftwareSQLResult);
        codeSoftwareComboBox.setSelectedIndex(-1);
        ((JTextField)codeSoftwareComboBox.getEditor().getEditorComponent()).getDocument().addDocumentListener(validationListener);
        this.add(codeSoftwareComboBox);

        matriculeLabel = new JLabel("Matricule: ");
        // matriculeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(matriculeLabel);
        String matriculeSQLInstruction = "SELECT NomPrenom FROM ResponsableReseaux;";
        PreparedStatement matriculePrepStat = connection.prepareStatement(matriculeSQLInstruction);
        Object[] matriculeSQLResult = AccessBDGen.creerListe1Colonne(matriculePrepStat);
        matriculeComboBox = new JComboBox(matriculeSQLResult);
        matriculeComboBox.setSelectedIndex(-1);
        ((JTextField)matriculeComboBox.getEditor().getEditorComponent()).getDocument().addDocumentListener(validationListener);
        this.add(matriculeComboBox);

        codeOSLabel = new JLabel("OS: ");
        // codeOSLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(codeOSLabel);
        String codeOSSQLInstruction = "SELECT Libelle FROM OS;";
        PreparedStatement codeOSPrepStat = connection.prepareStatement(codeOSSQLInstruction);
        Object[] codeOSSQLResult = AccessBDGen.creerListe1Colonne(codeOSPrepStat);
        codeOSComboBox = new JComboBox(codeOSSQLResult);
        codeOSComboBox.setSelectedIndex(-1);
        ((JTextField)codeOSComboBox.getEditor().getEditorComponent()).getDocument().addDocumentListener(validationListener);
        this.add(codeOSComboBox);

        validateButton = new JButton("Valider");
        validateButton.addActionListener(actionListener);
        this.add(validateButton);

        cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(actionListener);
        this.add(cancelButton);
    }

    private class actionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == validateButton) {
                System.out.println("valider");
            } else if (e.getSource() == cancelButton) {
                System.out.println("cancel");
            }
        }

    }

    private class validationManager implements DocumentListener {

        public void changedUpdate(DocumentEvent e) {
            validation();
        }

        public void removeUpdate(DocumentEvent e) {
            validation();
        }

        public void insertUpdate(DocumentEvent e) {
            validation();
        }

    }

    private boolean validation() {
        StringBuilder errorText = new StringBuilder();

        JTextField[] requiredTextFields = new JTextField[] { refProcedureInstallationTextField };
        for (JTextField requiredTextField : requiredTextFields) {
            if (requiredTextField.getText().length() == 0) {
                requiredTextField.setBackground(Color.red);
            } else {
                requiredTextField.setBackground(null);
            }
        }

        JComboBox[] requiredComboBoxes = new JComboBox[] { typeInstallationComboBox, codeSoftwareComboBox,
                matriculeComboBox, codeOSComboBox };
        for (JComboBox requiredComboBox : requiredComboBoxes) {
            if (((JTextField)requiredComboBox.getEditor().getEditorComponent()).getText().length() == 0) {
                requiredComboBox.setBackground(Color.red);
            } else {
                requiredComboBox.setBackground(null);
            }
            // if (requiredComboBox.getSelectedItem() == null) {
            //     requiredComboBox.setBackground(Color.red);
            // } else {
            //     requiredComboBox.setBackground(null);
            // }
        }

        // Show the errorText in a message box, or in a label, or ...

        return errorText.length() == 0;
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
}
