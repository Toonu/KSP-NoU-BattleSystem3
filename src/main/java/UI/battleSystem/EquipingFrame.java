package ui.battleSystem;

import crafts.Craft;
import ui.MainUI;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Tomas Novotny
 */
public class EquipingFrame extends JFrame {
    private final DefaultListModel<Craft> listWhite;
    private final DefaultListModel<Craft> listBlack;

    public EquipingFrame(String title) {
        super(title);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        Container c = getContentPane();
        c.setBackground(MainUI.BACKGROUND);
        c.setSize(MainUI.WIDTH, MainUI.HEIGHT);

        this.listWhite = new DefaultListModel<>();
        this.listBlack = new DefaultListModel<>();

        JList<Craft> teamWhite = new JList<>(listWhite);
        JList<Craft> teamBlack = new JList<>(listBlack);

        teamWhite.setBackground(new Color(0x1F2E7B));
        teamWhite.setForeground(MainUI.FOREGROUND);

        teamBlack.setBackground(new Color(0x1F2E7B));
        teamBlack.setForeground(MainUI.FOREGROUND);

        JScrollPane spW = new JScrollPane(teamWhite);
        JScrollPane spB = new JScrollPane(teamBlack);

        gc.weightx = 5;
        gc.weighty = 9;
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 1;
        c.add(spW, gc);

        gc.gridx = 3;
        c.add(spB, gc);

        gc.gridy = 2;
        gc.anchor = GridBagConstraints.SOUTH;
        JButton confirm = new JButton("Finish");
        confirm.addActionListener(e -> {
            SwingUtilities.updateComponentTreeUI(this);
            //TODO Battle afterwards
        });

        gc.weightx = 2;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 6;
        c.add(confirm, gc);

        gc.gridwidth = 1;
        gc.gridx = 1;
        gc.gridy = 1;
        JButton edit = new JButton("Edit");
        c.add(edit, gc);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO Check if edited crafts are of same type.
                // Button working to add new weapons and systems. Optionally configure crafts in different way.
            }
        });
    }

    public DefaultListModel<Craft> getListBlack() {
        return listBlack;
    }

    public DefaultListModel<Craft> getListWhite() {
        return listWhite;
    }
}
