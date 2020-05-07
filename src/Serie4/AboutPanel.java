package Serie4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutPanel extends JPanel{
    private JLabel text;
    private JButton close;

    public AboutPanel() {
        text = new JLabel("<html>Programme fait sur IntelliJ IDEA <br/> lol</html>");
        this.add(text);
        close = new JButton("GitHub");
    }
}