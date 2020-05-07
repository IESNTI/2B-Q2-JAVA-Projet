package Serie4;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.util.Date;
import java.util.Random;

public class NewInstallationPanel extends JPanel {

    private JLabel idInstallationLabel, dateInstallationLabel, typeInstallationLabel, commentairesLabel, dureeInstallationLabel, refProcedureInstallationLabel;
    private JLabel validationLabel, dateValidationLabel, codeSoftwareLabel, matriculeLabel, codeOSLabel;
    private JTextField idInstallationText;
    private JComboBox typeInstallationComboBox;
    private JTextArea commentairesTextArea;
    private UtilDateModel model = new UtilDateModel();
    private JDatePanelImpl datePanel = new JDatePanelImpl(model);
    private JDatePickerImpl dateInstallationPicker = new JDatePickerImpl(datePanel);

    Random rand = new Random();
    int rand_int1 = rand.nextInt(1000);

    public NewInstallationPanel(Connection connection) {
        this.setLayout(new GridLayout(8, 2, 5, 5));

        idInstallationLabel = new JLabel("ID de l'installation: ");
        idInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(idInstallationLabel);
        idInstallationText = new JTextField(30);
        this.add(idInstallationText);

        dateInstallationLabel = new JLabel("Date de l'installation: ");
        dateInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(dateInstallationLabel);
        this.add(dateInstallationPicker);

        typeInstallationLabel = new JLabel("Type de l'installation: ");
        typeInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(typeInstallationLabel);
        typeInstallationComboBox = new JComboBox();
        this.add(typeInstallationComboBox);

        commentairesLabel = new JLabel("Commentaires: ");
        commentairesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(commentairesLabel);
        commentairesTextArea = new JTextArea(2, 5);
        //commentairesTextArea.addKeyListener(commentairesKeyListener);
        this.add(commentairesTextArea);

        dureeInstallationLabel = new JLabel("Durée de l'installation (HH:mm:ss): ");
        dureeInstallationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(dureeInstallationLabel);
        JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date(0));
        this.add(timeSpinner);
    }

    KeyListener dureeInstallationListener = new KeyListener() {
        //TODO: tenter de remove ces deux trucs
        public void keyPressed(KeyEvent keyEvent) {
        }

        public void keyReleased(KeyEvent keyEvent) {
        }

        public void keyTyped(KeyEvent e) {
            //e.getKeyChar().chars().allMatch( Character::isDigit );
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
