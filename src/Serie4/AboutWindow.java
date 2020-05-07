package Serie4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutWindow extends JFrame{
    private Container cont;
    private AboutCont panel;

    public AboutWindow() {
        super("A propos");
        setMinimumSize(new Dimension(300, 150));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });

        panel = new AboutCont();
        cont = getContentPane();
        cont.add(panel);
        cont.setVisible(true);
        setVisible(true);
    }


}
