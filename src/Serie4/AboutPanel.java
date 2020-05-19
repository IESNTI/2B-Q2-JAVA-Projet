package Serie4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutPanel extends JPanel{
    private JLabel text1,text2,text3;
    private JButton ghLink, keerowLink, unixfoxLink;

    public AboutPanel() {

        setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

        text1 = new JLabel("<html><center>Programme développé en Java</center></html>",SwingConstants.CENTER);
        text2 = new JLabel("<html><center>Programmes utilisés : <br/>IntelliJ IDEA et Visual Studio Code</center></html>",SwingConstants.CENTER);
        text3 = new JLabel("<html><center>Jetez un coup d'oeil au repository et<br/>aux développeurs ci-dessous !</center></html>",SwingConstants.CENTER);
        add(text1);
        add(text2);
        add(text3);

        unixfoxLink = new JButton("<html><a href=''>@unixfox</a></html>");
        keerowLink= new JButton("<html><a href=''>@keerow</a></html>");
        unixfoxLink.setPreferredSize(new Dimension(120, 30));
        keerowLink.setPreferredSize(new Dimension(120, 30));
        add(unixfoxLink);
        add(keerowLink);

        ghLink = new JButton("<html><a href=''>GitHub Repository</a></html>");
        ghLink.setPreferredSize(new Dimension(250, 30));
        add(ghLink);

        ButtonManager buttonListener = new ButtonManager();
        ghLink.addActionListener(buttonListener);
        unixfoxLink.addActionListener(buttonListener);
        keerowLink.addActionListener(buttonListener);
    }

    private class ButtonManager implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if(e.getSource() == ghLink)
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/IESNTI/2B-Q2-JAVA-Projet"));
                else if (e.getSource() == unixfoxLink)
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/unixfox"));
                else if (e.getSource() == keerowLink)
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/keerow"));
            } catch (java.io.IOException x) {
                System.out.println(x.getMessage());
            }
        }
    }
}