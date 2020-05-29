package ui;

import crafts.Craft;
import impl.OOB;
import systems.Countermeasure;
import systems.Weapon;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Toonu
 */
public class JCraftPanel extends JPanel {
    private final JLabel craft = new JLabel();
    private final JLabel weapons = new JLabel();
    private final JLabel countermeasures = new JLabel();
    private final boolean switched = false;
    private ArrayList<Craft> selectedCrafts;

    /**
     * Constructor.
     *
     * @param text           label text.
     * @param selectedCrafts list of selected crafts.
     * @param simple         if weapon manager should be shown.
     */
    public JCraftPanel(String text, ArrayList<Craft> selectedCrafts, boolean simple) {
        super();
        this.selectedCrafts = selectedCrafts;

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                "Craft Details", SwingConstants.TOP, SwingConstants.CENTER, super.getFont(), Color.WHITE));
        setOpaque(true);
        setBackground(Gui.BACKGROUND);
        setLayout(new GridBagLayout());

        craft.setForeground(Gui.FOREGROUND);
        countermeasures.setForeground(Gui.FOREGROUND);
        weapons.setForeground(Gui.FOREGROUND);
        craft.setBackground(Gui.BACKGROUND);
        countermeasures.setBackground(Gui.BACKGROUND);
        weapons.setBackground(Gui.BACKGROUND);
        craft.setHorizontalAlignment(SwingConstants.CENTER);
        craft.setVerticalAlignment(SwingConstants.NORTH);
        countermeasures.setHorizontalAlignment(SwingConstants.CENTER);
        countermeasures.setVerticalAlignment(SwingConstants.NORTH);
        weapons.setHorizontalAlignment(SwingConstants.CENTER);
        weapons.setVerticalAlignment(SwingConstants.NORTH);

        JPanel equip = new JPanel();

        equip.setLayout(new GridBagLayout());
        equip.setBackground(Gui.BACKGROUND);

        LinkedList<JButton> systemButtons = new LinkedList<>();
        ArrayList<Weapon> templateWeapons = new ArrayList<>();
        ArrayList<Countermeasure> templateSystems = new ArrayList<>();

        JPanel systemsPanel = new JPanel(new GridBagLayout());
        JScrollPane systemsPanelScroll = new JScrollPane(systemsPanel);
        systemsPanelScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        systemsPanelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        systemsPanelScroll.setPreferredSize(new Dimension(180, 300));
        systemsPanel.setBackground(Gui.BACKGROUND);
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.EAST;
        equip.add(systemsPanelScroll, gc);

        gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.EAST;
        for (Countermeasure sys : OOB.TEMPLATE_COUNTERMEASURES) {
            systemButtons.add(new JButton(sys.toShortString()));
            systemButtons.getLast().setHorizontalAlignment(SwingConstants.RIGHT);
            systemButtons.getLast().addActionListener(e -> {
                setSelectedCrafts(Gui.getOob().getSelectedCraftsFromList());
                selectedCrafts.forEach(craft -> craft.addSystem(sys));
                Gui.getOob().getDetails().updateUI(Gui.getOob().getLastSelectedList());
            });
        }
        for (Weapon sys : OOB.TEMPLATE_WEAPONS) {
            systemButtons.add(new JButton(sys.toShortString()));
            systemButtons.getLast().setHorizontalAlignment(SwingConstants.RIGHT);
            systemButtons.getLast().addActionListener(e -> {
                setSelectedCrafts(Gui.getOob().getSelectedCraftsFromList());
                selectedCrafts.forEach(craft -> craft.addSystem(sys));
                Gui.getOob().getDetails().updateUI(Gui.getOob().getLastSelectedList());
            });
        }
        for (JButton but : systemButtons) {
            ++gc.gridy;
            systemsPanel.add(but, gc);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(1, 2, 8, 2);
        add(craft, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.3;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(weapons, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JSeparator(JSeparator.VERTICAL), gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.3;
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(countermeasures, gbc);


        gbc = new GridBagConstraints();
        gbc.weightx = 0.3;
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(1, 10, 1, 1);
        if (!simple) {
            add(equip, gbc);
        }

        setVisible(true);
    }

    /**
     * Updates UI with craft from the list.
     *
     * @param list crafts list.
     */
    public void updateUI(JList<Craft> list) {
        selectedCrafts = (ArrayList<Craft>) list.getSelectedValuesList();
        craft.setText(list.getSelectedValue().toLongString());
        countermeasures.setText(Gui.convertToMultiline(list.getSelectedValue().toCountermeasuresList()));
        weapons.setText(Gui.convertToMultiline(list.getSelectedValue().toWeaponsList()));
    }

    public ArrayList<Craft> getSelectedCrafts() {
        return selectedCrafts;
    }

    public void setSelectedCrafts(ArrayList<Craft> selectedCrafts) {
        this.selectedCrafts = selectedCrafts;
    }
}
