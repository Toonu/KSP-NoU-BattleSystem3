package ui;

import crafts.Craft;

import javax.swing.JLabel;

/**
 * @author Toonu
 */
public class JCraftLabel extends JLabel {
    public JCraftLabel(String text) {
        super();
    }

    public void updateUI(Craft craft) {
        setText(Gui.convertToMultiline(String.format("Details:\n%s\n%s\n%s", craft.toLongString(),
                craft.toWeaponsList(),
                craft.toCountermeasuresList())));

        super.updateUI();
    }


}
