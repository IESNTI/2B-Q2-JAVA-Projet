package Serie4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutWindow extends JFrame{
    private Container cont;
    private AboutPanel panel;

    public AboutWindow() {
        super("A propos");
        setMinimumSize(new Dimension(300, 250));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });

        setLocationRelativeTo(null);

        panel = new AboutPanel();
        cont = getContentPane();
        cont.add(panel);
        cont.setVisible(true);
        setVisible(true);
    }


}
