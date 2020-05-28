package ui.battleSystem;

import crafts.Craft;
import ui.BSSMenu;
import ui.MainUI;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

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

        teamWhite.setBackground(MainUI.BACKGROUND);
        teamWhite.setForeground(MainUI.FOREGROUND);

        teamBlack.setBackground(MainUI.BACKGROUND);
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
        gc.gridwidth = 5;
        c.add(confirm, gc);

        gc.gridwidth = 1;
        gc.gridx = 1;
        gc.gridy = 1;
        JButton edit = new JButton("Edit");
        c.add(edit, gc);

        edit.addActionListener(e -> {
            ArrayList<Craft> crafts = new ArrayList<>(teamWhite.getSelectedValuesList());
            for (Craft craft : crafts) {
                if (craft.getType() != crafts.get(0).getType()) {
                    JOptionPane.showMessageDialog(edit.getParent(), "Select crafts of same type!", "Warning",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            SystemsFrame systemsFrame = new SystemsFrame(MainUI.TITLE, crafts);

            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();

            systemsFrame.setLocation((width / 2) - (MainUI.WIDTH / 2), (height / 2) - (MainUI.HEIGHT / 2));
            systemsFrame.setSize(MainUI.WIDTH, MainUI.HEIGHT);
            systemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            systemsFrame.setBackground(MainUI.BACKGROUND);
            systemsFrame.setVisible(true);
            //TODO Systems editation frame, but first add weapons to it
        });

        gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.NORTH;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 5;
        c.add(new BSSMenu(), gc);
    }

    public DefaultListModel<Craft> getListBlack() {
        return listBlack;
    }

    public DefaultListModel<Craft> getListWhite() {
        return listWhite;
    }
}
