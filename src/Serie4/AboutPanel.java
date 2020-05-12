package Serie4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutPanel extends JPanel{
    private JLabel text1,text2,text3;
    private JButton ghlink, keerowlink, unixfoxlink;

    public AboutPanel() {

        this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

        text1 = new JLabel("<html><center>Programme développé en Java</center></html>",SwingConstants.CENTER);
        text2 = new JLabel("<html><center>Programmes utilisés : <br/>IntelliJ IDEA et Visual Studio Code</center></html>",SwingConstants.CENTER);
        text3 = new JLabel("<html><center>Jetez un coup d'oeil au repository et<br/>aux développeurs ci-dessous !</center></html>",SwingConstants.CENTER);
        this.add(text1);
        this.add(text2);
        this.add(text3);

        unixfoxlink = new JButton("<html><a href=''>@unixfox</a></html>");
        keerowlink= new JButton("<html><a href=''>@keerow</a></html>");
        unixfoxlink.setPreferredSize(new Dimension(120, 30));
        keerowlink.setPreferredSize(new Dimension(120, 30));
        this.add(unixfoxlink);
        this.add(keerowlink);

        ghlink = new JButton("<html><a href=''>GitHub Repository</a></html>");
        ghlink.setPreferredSize(new Dimension(250, 30));
        this.add(ghlink);

        ButtonManager buttonListener = new ButtonManager();
        ghlink.addActionListener(buttonListener);
        unixfoxlink.addActionListener(buttonListener);
        keerowlink.addActionListener(buttonListener);
    }

    private class ButtonManager implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if(e.getSource() == ghlink)
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/IESNTI/2B-Q2-JAVA-Projet"));
                else if (e.getSource() == unixfoxlink)
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/unixfox"));
                else if (e.getSource() == keerowlink)
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/keerow"));
            } catch (java.io.IOException x) {
                System.out.println(x.getMessage());
            }
        }
    }
}