package ui.battleSystem;

import crafts.Craft;
import impl.OOB;
import ui.Gui;
import ui.JCraftLabel;
import ui.JMenuExt;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Toonu
 */
public class OOBFrame extends JFrame {
    private final Container c = getContentPane();
    private final DefaultListModel<Craft> textForWhite = new DefaultListModel<>();
    private final DefaultListModel<Craft> textForBlack = new DefaultListModel<>();
    private final JList<Craft> whiteListedCrafts = new JList<>(textForWhite);
    private final JList<Craft> blackListedCrafts = new JList<>(textForBlack);
    private final JScrollPane scrollerWhite = new JScrollPane(whiteListedCrafts);
    private final JScrollPane scrollerBlack = new JScrollPane(blackListedCrafts);
    private final SortedMap<Craft, ArrayList<JButton>> templateButtons = new TreeMap<>();
    private final JButton nextScreen = new JButton("Finish");
    private final JButton editing = new JButton("Edit selected");
    private final ArrayList<Craft> selectedCraftsFromList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param title title of the frame.
     */
    public OOBFrame(String title) {
        super(title);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        setLocation(Gui.getCenterPosition());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        c.setBackground(Gui.BACKGROUND);
        c.setSize(Gui.WIDTH, Gui.HEIGHT);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridwidth = 6;
        c.add(new JMenuExt(0), gc);

        whiteListedCrafts.setBackground(Gui.BACKGROUND);
        whiteListedCrafts.setForeground(Color.WHITE);

        blackListedCrafts.setBackground(Gui.BACKGROUND);
        blackListedCrafts.setForeground(Color.WHITE);

        gc = new GridBagConstraints();
        gc.gridheight = OOB.TEMPLATE.getCrafts().size();
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 4;
        gc.weightx = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        c.add(scrollerWhite, gc);

        gc.gridx = 4;
        gc.gridwidth = 2;
        c.add(scrollerBlack, gc);


        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridy = 0;
        gc.gridx = 1;
        gc.weightx = 0.1;

        for (Craft craft : OOB.TEMPLATE.getCrafts()) {
            ++gc.gridy;
            templateButtons.put(craft, new ArrayList<>());
            templateButtons.get(craft).add(new JButton("+"));
            templateButtons.get(craft).add(new JButton("+"));

            c.add(templateButtons.get(craft).get(0), gc);
            ++gc.gridx;
            gc.weightx = 8;

            JLabel newLabel = new JLabel(craft.getName());
            newLabel.setForeground(Gui.FOREGROUND);
            newLabel.setHorizontalAlignment(SwingConstants.CENTER);
            c.add(newLabel, gc);

            gc.weightx = 0.1;
            ++gc.gridx;
            c.add(templateButtons.get(craft).get(1), gc);
            gc.gridx -= 2;

            templateButtons.get(craft).get(0).addActionListener(e -> {
                OOB.WHITE.addCraft(craft.copy());
                textForWhite.addElement(craft.copy());
                whiteListedCrafts.updateUI();
            });
            templateButtons.get(craft).get(1).addActionListener(e -> {
                OOB.BLACK.addCraft(craft.copy());
                textForBlack.addElement(craft.copy());
                blackListedCrafts.updateUI();
            });
        }

        ++gc.gridy;


        gc.gridx = 4;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.SOUTH;
        gc.fill = GridBagConstraints.VERTICAL;
        c.add(editing, gc);

        gc.gridx = 5;
        c.add(nextScreen, gc);

        JCraftLabel details = new JCraftLabel(" ");
        gc.gridx = 0;
        gc.gridwidth = 4;
        gc.fill = GridBagConstraints.BOTH;
        add(details, gc);

        whiteListedCrafts.addListSelectionListener(e -> {
            selectedCraftsFromList.clear();
            if (whiteListedCrafts.getSelectedValuesList().size() == 1) {
                details.updateUI(whiteListedCrafts);
            } else {
                selectedCraftsFromList.addAll(whiteListedCrafts.getSelectedValuesList());
            }
        });
        blackListedCrafts.addListSelectionListener(e -> {
            selectedCraftsFromList.clear();
            if (blackListedCrafts.getSelectedValuesList().size() == 1) {
                details.updateUI(blackListedCrafts);
            } else {
                selectedCraftsFromList.addAll(blackListedCrafts.getSelectedValuesList());
            }
        });
        nextScreen.addActionListener(e -> {
            setVisible(false);
            dispose();
            //TODO Battle Commence
        });
        editing.addActionListener(e -> {
            for (Craft craft : whiteListedCrafts.getSelectedValuesList()) {
                if (craft.getType() != whiteListedCrafts.getSelectedValuesList().get(0).getType()) {
                    JOptionPane.showMessageDialog(c, "Select crafts of same type!", "Warning",
                            JOptionPane.ERROR_MESSAGE);
                    whiteListedCrafts.clearSelection();
                    return;
                }
            }
            for (Craft craft : blackListedCrafts.getSelectedValuesList()) {
                if (craft.getType() != whiteListedCrafts.getSelectedValuesList().get(0).getType()) {
                    JOptionPane.showMessageDialog(c, "Select crafts of same type!", "Warning",
                            JOptionPane.ERROR_MESSAGE);
                    blackListedCrafts.clearSelection();
                    return;
                }
            }
            ArrayList<Craft> selected = new ArrayList<>(whiteListedCrafts.getSelectedValuesList());
            selected.addAll(blackListedCrafts.getSelectedValuesList());

            EquipmentFrame sf = new EquipmentFrame(Gui.TITLE, selected);
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    public Container getC() {
        return c;
    }

    public JScrollPane getScrollerBlack() {
        return scrollerBlack;
    }

    public JScrollPane getScrollerWhite() {
        return scrollerWhite;
    }

    public SortedMap<Craft, ArrayList<JButton>> getTemplateButtons() {
        return templateButtons;
    }

    public JButton getNextScreen() {
        return nextScreen;
    }

    public ArrayList<Craft> getSelectedCraftsFromList() {
        return selectedCraftsFromList;
    }

    public DefaultListModel<Craft> getTextForBlack() {
        return textForBlack;
    }

    public DefaultListModel<Craft> getTextForWhite() {
        return textForWhite;
    }

    public JList<Craft> getBlackListedCrafts() {
        return blackListedCrafts;
    }

    public JList<Craft> getWhiteListedCrafts() {
        return whiteListedCrafts;
    }

    public JButton getEditing() {
        return editing;
    }
}
