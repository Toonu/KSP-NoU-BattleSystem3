package ui;

import crafts.Craft;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * @author Toonu
 */
public class JCraftLabel extends JLabel {
    public JCraftLabel(String text) {
        super(text);
    }

    public void updateUI(Craft craft) {
        setText(Gui.convertToMultiline(String.format("%s\n%s\n%s", craft.toLongString(),
                craft.toWeaponsList(),
                craft.toCountermeasuresList())));

        setBorder(BorderFactory.createTitledBorder("-Craft Details-"));

        super.updateUI();
    }
}
