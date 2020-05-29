package ui.battleSystem;

import crafts.Craft;
import impl.OOB;
import systems.Countermeasure;
import systems.Weapon;
import ui.Gui;
import ui.JCraftPanel;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    private final ArrayList<Craft> selectedCraftsFromList = new ArrayList<>();
    private final JCraftPanel details = new JCraftPanel("Info", selectedCraftsFromList, true);
    private final ArrayList<Craft> craftsPresent;
    private final DefaultListModel<Craft> craftsPresentJList = new DefaultListModel<>();


    public EquipmentFrame(String title, ArrayList<Craft> selectedCrafts) {
        super();
        setLayout(new GridBagLayout());
        Container c = getContentPane();
        c.setBackground(Gui.BACKGROUND);

        this.craftsPresent = selectedCrafts;

        setBackground(Gui.BACKGROUND);
        setSize(Gui.WIDTH, Gui.HEIGHT);

        for (Craft craft : selectedCrafts) {
            craftsPresentJList.addElement(craft);
        }

        JPanel panel = new JPanel(new GridBagLayout());
        JScrollPane panelScroll = new JScrollPane(panel);
        //panelScroll.setMinimumSize(new Dimension(200, 200));
        panelScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setMinimumSize(panelScroll.getMinimumSize());
        panel.setBackground(Gui.BACKGROUND);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 2;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        c.add(panelScroll, gc);

        JList<Craft> craftSelectionList = new JList<>(craftsPresentJList);
        craftSelectionList.setBackground(new Color(0x1829B6));
        craftSelectionList.setForeground(Color.WHITE);

        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        details.setBackground(new Color(0x1829B6));
        c.add(details, gc);


        JScrollPane craftScroll = new JScrollPane(craftSelectionList);
        //craftScroll.setMinimumSize(new Dimension(200, 200));
        craftScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        craftScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.NORTH;
        c.add(craftScroll, gc);

        gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
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
        for (JButton but : systemButtons) {
            ++gc.gridy;
            panel.add(but, gc);
        }

        JButton confirm = new JButton("Finish");
        confirm.addActionListener(e -> {
            this.setVisible(false);
        });

        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 3;
        gc.fill = GridBagConstraints.HORIZONTAL;
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