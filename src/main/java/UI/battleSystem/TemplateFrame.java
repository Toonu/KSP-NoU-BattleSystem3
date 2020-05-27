package ui.battleSystem;

import crafts.Craft;
import impl.OOB;
import ui.MainUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Toonu
 */
public class TemplateFrame extends JFrame {
    /**
     * Constructor.
     *
     * @param title title of the frame.
     */
    public TemplateFrame(String title) {
        super(title);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        Container c = getContentPane();
        c.setBackground(MainUI.BACKGROUND);
        c.setSize(MainUI.WIDTH, MainUI.HEIGHT);

        LinkedList<Craft> localTemplate = OOB.TEMPLATE.getCrafts();

        ArrayList<JButton> localTemplateButtons = new ArrayList<>();

        gc.gridy = 0;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;

        for (Craft craft : OOB.TEMPLATE.getCrafts()) {
            localTemplateButtons.add(new JButton(craft.getName()));
            ++gc.gridy;
            c.add(localTemplateButtons.get(gc.gridy - 1), gc);
        }

        gc.gridx = 1;
        JLabel label = new JLabel("DSSAS");
        c.add(label, gc);
    }
}
