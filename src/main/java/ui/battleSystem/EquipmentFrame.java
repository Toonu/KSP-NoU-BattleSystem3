package ui.battleSystem;

import crafts.Craft;
import impl.OOB;
import systems.Countermeasure;
import systems.Weapon;
import ui.Gui;
import ui.JCraftLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Toonu
 * <p>
 * Class supporting addition of system to group of crafts.
 */
public class EquipmentFrame extends JFrame {
    private final LinkedList<JButton> systemButtons = new LinkedList<>();
    private final ArrayList<Weapon> templateWeapons = new ArrayList<>();
    private final ArrayList<Countermeasure> templateSystems = new ArrayList<>();
    private final JCraftLabel details = new JCraftLabel("Info");
    private final ArrayList<Craft> craftsPresent;
    private final DefaultListModel<Craft> craftsPresentJList = new DefaultListModel<>();
    private final ArrayList<Craft> selectedCraftsFromList = new ArrayList<>();

    public EquipmentFrame(String title, ArrayList<Craft> selectedCrafts) {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        Container c = getContentPane();
        c.setBackground(Gui.BACKGROUND);

        this.craftsPresent = selectedCrafts;

        setBackground(Gui.BACKGROUND);
        setSize(Gui.WIDTH, Gui.HEIGHT);

        for (Craft craft : selectedCrafts) {
            craftsPresentJList.addElement(craft);
        }


        JList<Craft> craftSelectionList = new JList<>(craftsPresentJList);
        craftSelectionList.setBackground(new Color(0x1829B6));
        craftSelectionList.setForeground(Color.WHITE);
        c.add(craftSelectionList, gc);

        details.setForeground(Color.WHITE);
        details.setBackground(new Color(0x1829B6));
        c.add(details, gc);

        for (Countermeasure sys : OOB.TEMPLATE_COUNTERMEASURES) {
            systemButtons.add(new JButton(sys.toShortString()));
            systemButtons.getLast().setHorizontalAlignment(SwingConstants.RIGHT);
            systemButtons.getLast().addActionListener(e -> selectedCraftsFromList.forEach(craft -> craft.addSystem(sys)));
            try {
                details.updateUI();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        for (Weapon sys : OOB.TEMPLATE_WEAPONS) {
            systemButtons.add(new JButton(sys.toShortString()));
            systemButtons.getLast().setHorizontalAlignment(SwingConstants.RIGHT);
            systemButtons.getLast().addActionListener(e -> selectedCraftsFromList.forEach(craft -> craft.addSystem(sys)));
            try {
                details.updateUI();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        gc = new GridBagConstraints();
        gc.gridheight = 1;
        gc.weightx = 6;
        gc.anchor = GridBagConstraints.EAST;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridy = 0;
        gc.gridx = 2;
        for (JButton but : systemButtons) {
            ++gc.gridy;
            c.add(but, gc);
        }

        JButton confirm = new JButton("Finish");
        confirm.addActionListener(e -> {
            this.setVisible(false);
        });
        gc = new GridBagConstraints();
        gc.gridy = 1;
        add(confirm, gc);

        craftSelectionList.addListSelectionListener(e -> {
            selectedCraftsFromList.clear();
            if (craftSelectionList.getSelectedValuesList().size() == 1) {
                details.updateUI(craftSelectionList);
            } else {
                selectedCraftsFromList.addAll(craftSelectionList.getSelectedValuesList());
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}