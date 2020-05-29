package ui;

import crafts.Craft;

import javax.swing.*;
import java.awt.*;

/**
 * @author Toonu
 */
public class JCraftLabel extends JPanel {
    private final JLabel craft = new JLabel();
    private final JLabel weapons = new JLabel();
    private final JLabel countermeasures = new JLabel();

    public JCraftLabel(String text) {
        super();
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                "Craft Details", SwingConstants.TOP, SwingConstants.CENTER, super.getFont(), Color.WHITE));
        setOpaque(true);
        setBackground(Gui.BACKGROUND);
        setPreferredSize(new Dimension(400, 200));
        setLayout(new BorderLayout());

        craft.setForeground(Gui.FOREGROUND);
        countermeasures.setForeground(Gui.FOREGROUND);
        weapons.setForeground(Gui.FOREGROUND);
        craft.setBackground(Gui.BACKGROUND);
        countermeasures.setBackground(Gui.BACKGROUND);
        weapons.setBackground(Gui.BACKGROUND);
        craft.setHorizontalAlignment(SwingConstants.LEFT);
        craft.setVerticalAlignment(SwingConstants.NORTH);
        countermeasures.setHorizontalAlignment(SwingConstants.LEFT);
        countermeasures.setVerticalAlignment(SwingConstants.NORTH);
        weapons.setHorizontalAlignment(SwingConstants.LEFT);
        weapons.setVerticalAlignment(SwingConstants.NORTH);

        add(craft, BorderLayout.NORTH);
        add(new JSeparator(), BorderLayout.CENTER);
        add(countermeasures, BorderLayout.EAST);
        add(weapons, BorderLayout.WEST);
    }

    public void updateUI(JList<Craft> list) {
        craft.setText(list.getSelectedValue().toLongString());
        countermeasures.setText(Gui.convertToMultiline(list.getSelectedValue().toWeaponsList()));
        weapons.setText(Gui.convertToMultiline(list.getSelectedValue().toCountermeasuresList()));
    }
}
