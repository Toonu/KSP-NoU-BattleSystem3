package ui.battleSystem;

import crafts.Craft;
import impl.OOB;
import ui.Gui;
import ui.JCraftLabel;
import ui.JMenuExt;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Toonu
 */
public class TemplateFrame extends JFrame {
    private final Container c = getContentPane();
    private final DefaultListModel<Craft> textWhiteList = new DefaultListModel<>();
    private final DefaultListModel<Craft> textBlackList = new DefaultListModel<>();
    private final JList<Craft> textWhite = new JList<>(textWhiteList);
    private final JList<Craft> textBlack = new JList<>(textBlackList);
    private final JScrollPane scrollerWhite = new JScrollPane(textWhite);
    private final JScrollPane scrollerBlack = new JScrollPane(textBlack);
    private final SortedMap<Craft, ArrayList<JButton>> templateButtons = new TreeMap<>();
    private final JButton nextScreen = new JButton("Finish");
    private final JButton editing = new JButton("Edit selected");
    private final ArrayList<Craft> selectedCraftsFromList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param title title of the frame.
     */
    public TemplateFrame(String title, EquipingFrame eq) {
        super(title);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        setLocation(Gui.getCenterPosition());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Container c = getContentPane();
        c.setBackground(Gui.BACKGROUND);
        c.setSize(Gui.WIDTH, Gui.HEIGHT);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = 8;
        c.add(new JMenuExt(0), gc);

        gc = new GridBagConstraints();

        textWhite.setBackground(Gui.BACKGROUND);
        textWhite.setForeground(Color.WHITE);

        textBlack.setBackground(Gui.BACKGROUND);
        textBlack.setForeground(Color.WHITE);

        gc.gridheight = OOB.TEMPLATE.getCrafts().size();
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;
        gc.weightx = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        c.add(scrollerWhite, gc);

        gc.gridx = 4;
        c.add(scrollerBlack, gc);


        gc.fill = GridBagConstraints.HORIZONTAL;
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

            //TODO Functionality of removing template crafts from loaded file in the program.

            gc.weightx = 0.1;
            ++gc.gridx;
            c.add(templateButtons.get(craft).get(1), gc);
            gc.gridx -= 2;
            
            templateButtons.get(craft).get(0).addActionListener(e -> {
                OOB.WHITE.addCraft(craft.copy());
                textWhiteList.addElement(craft.copy());
                textWhite.updateUI();
            });
            templateButtons.get(craft).get(1).addActionListener(e -> {
                OOB.BLACK.addCraft(craft.copy());
                textBlackList.addElement(craft.copy());
                textBlack.updateUI();
            });
        }

        ++gc.gridy;
        gc.gridx = 4;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.SOUTH;
        gc.fill = GridBagConstraints.NONE;
        c.add(nextScreen, gc);

        gc.gridx = 5;
        c.add(editing, gc);
        gc.gridy = 0;
        gc.gridx = 6;
        gc.weightx = 4;
        JCraftLabel details = new JCraftLabel("");
        c.add(details);

        textWhite.addListSelectionListener(e -> {
            selectedCraftsFromList.clear();
            if (textWhite.getSelectedValuesList().size() == 1) {
                details.updateUI();
                /*details.setText(Gui.convertToMultiline(
                        String.format("Details:\n%s\n%s\n%s", textWhite.getSelectedValue().toLongString(),
                                textWhite.getSelectedValue().toWeaponsList(),
                                textWhite.getSelectedValue().toCountermeasuresList())));*/
            } else {
                selectedCraftsFromList.addAll(textWhite.getSelectedValuesList());
            }
        });
        nextScreen.addActionListener(e -> {
            setVisible(false);
            dispose();
            for (Craft craft : OOB.WHITE.getCrafts()) {
                eq.getListWhite().addElement(craft);
            }
            for (Craft craft : OOB.BLACK.getCrafts()) {
                eq.getListBlack().addElement(craft);
            }
            eq.setVisible(true);
        });
        editing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Craft> crafts = new ArrayList<>(textWhite.getSelectedValuesList());
                for (Craft craft : crafts) {
                    if (craft.getType() != crafts.get(0).getType()) {
                        JOptionPane.showMessageDialog(this, "Select crafts of same type!", "Warning",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                //TODO sysFrame
            }
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
}
