package Serie4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutCont extends JPanel{
    private JLabel text;
    private JButton close;

    public AboutCont() {
        text = new JLabel("<html>Programme fait sur IntelliJ IDEA <br/> lol</html>");
        this.add(text);
        close = new JButton("GitHub");
    }
}