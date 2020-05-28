package ui;

import crafts.Craft;

<<<<<<< HEAD
import javax.swing.BorderFactory;
=======
>>>>>>> master
import javax.swing.JLabel;

/**
 * @author Toonu
 */
public class JCraftLabel extends JLabel {
    public JCraftLabel(String text) {
        super(text);
    }

    public void updateUI(Craft craft) {
<<<<<<< HEAD
        setText(Gui.convertToMultiline(String.format("%s\n%s\n%s", craft.toLongString(),
                craft.toWeaponsList(),
                craft.toCountermeasuresList())));

        setBorder(BorderFactory.createTitledBorder("-Craft Details-"));

        super.updateUI();
    }
=======
        setText(MainUI.convertToMultiline(String.format("Details:\n%s\n%s\n%s", craft.toLongString(),
                craft.toWeaponsList(),
                craft.toCountermeasuresList())));

        super.updateUI();
    }


>>>>>>> master
}
