package ui.battleSystem;

import crafts.Craft;
import ui.Gui;
import ui.JMenuExt;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Toonu
 */
public class ConfigurationWindow extends JFrame {
    private final JTextArea textWhite = new JTextArea();
    private final JTextArea textBlack = new JTextArea();
    private final JScrollPane scrollerWhite = new JScrollPane(textWhite);
    private final JScrollPane scrollerBlack = new JScrollPane(textBlack);
    private final SortedMap<Craft, ArrayList<JButton>> templateButtons = new TreeMap<>();
    private final JButton nextScreen = new JButton("Finish");
    private final JButton editing = new JButton("Edit selected");

    public ConfigurationWindow(String title) {
        super(title);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        Container c = getContentPane();
        c.setBackground(Gui.BACKGROUND);
        c.setSize(Gui.SIZE);

        gc.gridx = 0;
        gc.gridy = 0;
        c.add(new JMenuExt(0), gc);

        textWhite.setBackground(Gui.BACKGROUND);
        textWhite.setForeground(Color.WHITE);
        textWhite.setLineWrap(true);
        textWhite.setWrapStyleWord(true);

        textBlack.setBackground(Gui.BACKGROUND);
        textBlack.setForeground(Color.WHITE);
        textBlack.setLineWrap(true);
        textBlack.setWrapStyleWord(true);


        nextScreen.addActionListener(e -> {
            setVisible(false);
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(false);
    }
}
