package ui.battleSystemOld;

import crafts.Craft;
import impl.OOB;
import ui.JMenuExt;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Toonu
 */
public class TemplateFrame extends JPanel {
    private final JTextArea textWhite = new JTextArea();
    private final JTextArea textBlack = new JTextArea();
    private final JScrollPane scrollerWhite = new JScrollPane(textWhite);
    private final JScrollPane scrollerBlack = new JScrollPane(textBlack);
    private final SortedMap<Craft, ArrayList<JButton>> templateButtons = new TreeMap<>();
    private final JButton nextScreen = new JButton("Finish");
    private final JButton editing = new JButton("Edit selected");

    /**
     * Constructor.
     *
     * @param title title of the frame.
     */
    public TemplateFrame(String title, EquipingFrame eq) {
        super(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();


        JScrollPane sp = new JScrollPane();

        setBackground(MainUI.BACKGROUND);
        setSize(MainUI.WIDTH, MainUI.HEIGHT);

        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = 5;
        add(new JMenuExt(), gc);

        gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;

        textWhite.setBackground(MainUI.BACKGROUND);
        textWhite.setForeground(Color.WHITE);
        textWhite.setLineWrap(true);
        textWhite.setWrapStyleWord(true);

        textBlack.setBackground(MainUI.BACKGROUND);
        textBlack.setForeground(Color.WHITE);
        textBlack.setLineWrap(true);
        textBlack.setWrapStyleWord(true);

        gc.gridheight = OOB.TEMPLATE.getCrafts().size();
        gc.weighty = 2;
        gc.weightx = 2;
        gc.gridx = 0;
        gc.gridy = 1;
        add(scrollerWhite, gc);

        gc.gridx = 4;
        add(scrollerBlack, gc);

        gc.gridheight = 1;
        gc.gridy = 0;
        gc.gridx = 1;
        gc.weightx = 0.5;

        for (Craft craft : OOB.TEMPLATE.getCrafts()) {
            ++gc.gridy;
            templateButtons.put(craft, new ArrayList<>());
            templateButtons.get(craft).add(new JButton("+"));
            templateButtons.get(craft).add(new JButton("+"));

            add(templateButtons.get(craft).get(0), gc);
            ++gc.gridx;
            gc.weightx = 8;

            JLabel newLabel = new JLabel(craft.getName());
            newLabel.setForeground(MainUI.FOREGROUND);
            add(newLabel, gc);

            //TODO Functionality of removing template crafts from loaded file in the program.

            gc.weightx = 0.5;
            ++gc.gridx;
            add(templateButtons.get(craft).get(1), gc);
            gc.gridx -= 2;
            
            templateButtons.get(craft).get(0).addActionListener(e -> {
                textWhite.setText(textWhite.getText() + String.format(" [%s]", craft.getName()));
                OOB.WHITE.addCraft(craft.copy());
            });
            templateButtons.get(craft).get(1).addActionListener(e -> {
                textBlack.setText(textBlack.getText() + String.format(" [%s]", craft.getName()));
                OOB.BLACK.addCraft(craft.copy());
            });
        }

        ++gc.gridy;
        gc.gridx = 0;
        gc.weightx = 5;
        gc.anchor = GridBagConstraints.SOUTH;
        gc.fill = GridBagConstraints.BOTH;
        add(nextScreen, gc);

        gc.gridx = 2;
        add(editing, gc);


        nextScreen.addActionListener(e -> {
            setVisible(false);
            for (Craft craft : OOB.WHITE.getCrafts()) {
                eq.getListWhite().addElement(craft);
            }
            for (Craft craft : OOB.BLACK.getCrafts()) {
                eq.getListBlack().addElement(craft);
            }
            eq.setVisible(true);
        });
    }

    public JScrollPane getScrollerBlack() {
        return scrollerBlack;
    }

    public JScrollPane getScrollerWhite() {
        return scrollerWhite;
    }

    public JTextArea getTextBlack() {
        return textBlack;
    }

    public JTextArea getTextWhite() {
        return textWhite;
    }

    public SortedMap<Craft, ArrayList<JButton>> getTemplateButtons() {
        return templateButtons;
    }

    public JButton getNextScreen() {
        return nextScreen;
    }
}
