package Serie4;

import accessBD.AccessBDGen;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class NewInstallationPanel extends JPanel {

    private JLabel idInstallationLabel, dateInstallationLabel, typeInstallationLabel, commentairesLabel, dureeInstallationLabel, refProcedureInstallationLabel;
    private JLabel validationLabel, dateValidationLabel, codeSoftwareLabel, matriculeLabel, codeOSLabel;
    private JTextField idInstallationTextField, refProcedureInstallationTextField;
    private JComboBox typeInstallationComboBox, codeSoftwareComboBox, matriculeComboBox, codeOSComboBox;
    private ButtonGroup validationButtonGroup;
    private JTextArea commentairesTextArea;
    private UtilDateModel model = new UtilDateModel();
    private JDatePanelImpl datePanel = new JDatePanelImpl(model);
    private JDatePickerImpl dateInstallationPicker, dateValidationPicker;
    private Connection connection;

    public NewInstallationPanel(Connection connection) throws SQLException {
        this.connection = connection;
        this.setLayout(new GridLayout(12, 10, 1, 1));

        idInstallationLabel = new JLabel("ID de l'installation: ");
        //idInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(idInstallationLabel);
        idInstallationTextField = new JTextField(30);
        this.add(idInstallationTextField);

        dateInstallationLabel = new JLabel("Date de l'installation: ");
        //dateInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(dateInstallationLabel);
        dateInstallationPicker = new JDatePickerImpl(datePanel);
        this.add(dateInstallationPicker);

        typeInstallationLabel = new JLabel("Type de l'installation: ");
        //typeInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(typeInstallationLabel);
        String typeInstallationList[] = { "Standard", "Personnalisée" };
        typeInstallationComboBox = new JComboBox(typeInstallationList);
        this.add(typeInstallationComboBox);

        commentairesLabel = new JLabel("Commentaires: ");
        //commentairesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(commentairesLabel);
        commentairesTextArea = new JTextArea(2, 2);
        commentairesTextArea.addKeyListener(commentairesKeyListener);
        this.add(commentairesTextArea);

        dureeInstallationLabel = new JLabel("Durée de l'installation (HH:mm:ss): ");
        //dureeInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(dureeInstallationLabel);
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date(0));
        timeEditor.getTextField().setEditable(false);
        this.add(timeSpinner);

        refProcedureInstallationLabel = new JLabel("Référence de la procédure d'installation: ");
        //refProcedureInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(refProcedureInstallationLabel);
        refProcedureInstallationTextField = new JTextField(50);
        refProcedureInstallationTextField.addKeyListener(refProcedureInstallationKeyListener);
        this.add(refProcedureInstallationTextField);

        validationLabel = new JLabel("Validation: ");
        //validationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(validationLabel);
        String validationButtonList[] = { "A prevoir", "Terminee", "En cours" };
        validationButtonGroup = new ButtonGroup();
        for (int i = 0; i < validationButtonList.length; i++) {
            JRadioButton validationButton = new JRadioButton(validationButtonList[i]);
            validationButtonGroup.add(validationButton);
            this.add(validationButton);
        }

        dateValidationLabel = new JLabel("Date de la validation: ");
        //dateValidationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(dateValidationLabel);
        dateValidationPicker = new JDatePickerImpl(datePanel);
        this.add(dateValidationPicker);

        codeSoftwareLabel = new JLabel("Logiciel: ");
        //codeSoftwareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(codeSoftwareLabel);
        String codeSoftwareSQLInstruction = "SELECT Nom FROM Software;";
        PreparedStatement codeSoftwarePrepStat = connection.prepareStatement(codeSoftwareSQLInstruction);
        Object[] codeSoftwareSQLResult = AccessBDGen.creerListe1Colonne(codeSoftwarePrepStat);
        codeSoftwareComboBox = new JComboBox(codeSoftwareSQLResult);
        this.add(codeSoftwareComboBox);

        matriculeLabel = new JLabel("Matricule: ");
        //matriculeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(matriculeLabel);
        String matriculeSQLInstruction = "SELECT NomPrenom FROM ResponsableReseaux;";
        PreparedStatement matriculePrepStat = connection.prepareStatement(matriculeSQLInstruction);
        Object[] matriculeSQLResult = AccessBDGen.creerListe1Colonne(matriculePrepStat);
        matriculeComboBox = new JComboBox(matriculeSQLResult);
        this.add(matriculeComboBox);

        codeOSLabel = new JLabel("OS: ");
        //codeOSLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(codeOSLabel);
        String codeOSSQLInstruction = "SELECT Libelle FROM OS;";
        PreparedStatement codeOSPrepStat = connection.prepareStatement(codeOSSQLInstruction);
        Object[] codeOSSQLResult = AccessBDGen.creerListe1Colonne(codeOSPrepStat);
        codeOSComboBox = new JComboBox(codeOSSQLResult);
        this.add(codeOSComboBox);
    }

    KeyListener refProcedureInstallationKeyListener = new KeyListener() {
        //TODO: tenter de remove ces deux trucs
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

    KeyListener commentairesKeyListener = new KeyListener() {
        //TODO: tenter de remove ces deux trucs
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
