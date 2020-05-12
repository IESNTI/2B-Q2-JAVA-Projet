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

        //Centers the display window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        panel = new AboutPanel();
        cont = getContentPane();
        cont.add(panel);
        cont.setVisible(true);
        setVisible(true);
    }


}
