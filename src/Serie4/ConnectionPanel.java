package Serie4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPanel extends JPanel {
    private JPanel northPanel, centerPanel;
    private JLabel welcomeLabel, enterCredentialsLabel, usernameLabel, passwordLabel;
    private JButton loginJButton, resetJButton;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private ActionManager actionListener = new ActionManager();
    private ValidationListener validationDocumentListener = new ValidationListener();
    private String[] optionsCancelJOptionPane = { "Oui", "Non" };
    private Connection connection;
    private JMenuBar bar;
    private Container cont;
    WelcomePanel welcomePanel;

    public ConnectionPanel(Connection connection, JMenuBar bar, Container cont, WelcomePanel welcomePanel) {
        this.connection = connection;
        this.bar = bar;
        this.cont = cont;
        this.welcomePanel = welcomePanel;
        setLayout(new BorderLayout());
        addElements();
    }

    void addElements() {
        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 1));
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2));
        centerPanel.setBorder(new EmptyBorder(300, 200, 300, 200));

        welcomeLabel = new JLabel("Bienvenue !");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        enterCredentialsLabel = new JLabel("Veuillez entrer les identifiants de connexion pour continuer.");
        enterCredentialsLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        northPanel.add(welcomeLabel);
        northPanel.add(enterCredentialsLabel);

        usernameLabel = new JLabel("Nom d'utilisateur (requis) : ");
        usernameTextField = new JTextField(100);
        usernameTextField.getDocument().addDocumentListener(validationDocumentListener);
        passwordLabel = new JLabel("Mot de passe (requis) : ");
        passwordTextField = new JPasswordField(100);
        passwordTextField.getDocument().addDocumentListener(validationDocumentListener);
        resetJButton = new JButton("Réinitialiser");
        resetJButton.addActionListener(actionListener);
        loginJButton = new JButton("Se connecter");
        loginJButton.addActionListener(actionListener);
        centerPanel.add(usernameLabel);
        centerPanel.add(usernameTextField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordTextField);
        centerPanel.add(resetJButton);
        centerPanel.add(loginJButton);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        validateElements();
    }

    private class ActionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginJButton) {
                if (validateElements())
                    checkIfCredentialsCorrect();
                else
                    JOptionPane.showMessageDialog(null,
                            "Veuillez vérifier que vous avez bien rempli tous les champs requis (en rouge).", "",
                            JOptionPane.INFORMATION_MESSAGE, null);
            } else if (e.getSource() == resetJButton) {
                int confirmDialog = JOptionPane.showOptionDialog(null, "Voulez vous réinitialiser tous les champs ?",
                        "", 0, JOptionPane.WARNING_MESSAGE, null, optionsCancelJOptionPane, null);
                if (confirmDialog == 0) {
                    refreshPanel();
                }

            }
        }

    }

    private class ValidationListener implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent e) {
            validateElements();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            validateElements();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            validateElements();
        }
    }

    void refreshPanel() {
        removeAll();
        addElements();
        revalidate();
        repaint();
    }

    private Boolean validateElements() {
        Boolean areRequiredElementsCompleted = true;

        if (usernameTextField.getText() != null && usernameTextField.getText().isEmpty()) {
            usernameTextField.setBackground(Color.red);
            areRequiredElementsCompleted = false;
        } else {
            usernameTextField.setBackground(null);
        }

        if (String.valueOf(passwordTextField.getPassword()) != null && String.valueOf(passwordTextField.getPassword()).isEmpty()) {
            passwordTextField.setBackground(Color.red);
            areRequiredElementsCompleted = false;
        } else {
            passwordTextField.setBackground(null);
        }

        return (areRequiredElementsCompleted);

    }

    private void checkIfCredentialsCorrect() {
        try {
            String sqlInstruction = "SELECT nom, motdepasse FROM Utilisateurs WHERE nom=? and motdepasse=?;";
            PreparedStatement myPrepStat = connection.prepareStatement(sqlInstruction);
            myPrepStat.setString(1, usernameTextField.getText());
            myPrepStat.setString(2, String.valueOf(passwordTextField.getPassword()));
            ResultSet resultSet = myPrepStat.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(loginJButton, "Identifiants correct !");
                bar.setVisible(true);
                cont.removeAll();
                cont.add(welcomePanel);
                cont.revalidate();
                cont.repaint();
            } else {
                JOptionPane.showMessageDialog(loginJButton,
                        "Mauvais nom d'utilisateur ou mot de passe. Veuillez réessayer.");
                refreshPanel();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
