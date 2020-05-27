package UI;

import javax.swing.*;
import java.awt.*;

/**
 * @author Toonu
 */
public class TemplateFrame extends JFrame {
    TemplateFrame(String title) {
        super(title);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        Container c = getContentPane();
        c.setBackground(MainUI.BACKGROUND);


    }
}
