package ui.battleSystem;

import crafts.Craft;
import impl.OOB;
import ui.Gui;
import ui.JCraftList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Toonu
 */
public class BattleFrame extends JFrame {
    private final Container c = getContentPane();
    private final DefaultListModel<Craft> textForWhite = new DefaultListModel<>();
    private final DefaultListModel<Craft> textForBlack = new DefaultListModel<>();
    private final JCraftList<Craft> whiteListedCrafts = new JCraftList<>(textForWhite);
    private final JCraftList<Craft> blackListedCrafts = new JCraftList<>(textForBlack);
    private final JPanel crafts = new JPanel(new BorderLayout(5, 0));
    private final BattleMap map = new BattleMap();

    public BattleFrame(String title) {
        super(title);

        setLayout(new GridBagLayout());

        c.setBackground(Gui.BACKGROUND);
        c.setLayout(new GridBagLayout());
        setPreferredSize(Gui.getMonitorSize());
        setMinimumSize(Gui.SIZE);
        ScrollPane mapScroll = new ScrollPane();

        crafts.add(whiteListedCrafts, BorderLayout.NORTH);
        crafts.add(blackListedCrafts, BorderLayout.SOUTH);
        crafts.setBackground(Gui.BACKGROUND);
        crafts.setForeground(Gui.FOREGROUND);

        whiteListedCrafts.updateUI(OOB.WHITE.getCrafts());
        blackListedCrafts.updateUI(OOB.BLACK.getCrafts());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        mapScroll.add(map);
        c.add(mapScroll, gbc);
        map.repaint();

        ++gbc.gridx;
        JButton zoom = new JButton("Zoom");
        zoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.zoom(map.getMap());
            }
        });
        c.add(zoom, gbc);

        ++gbc.gridx;
        gbc.weightx = 0.1;
        c.add(crafts, gbc);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        validate();
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }


    public JCraftList<Craft> getBlackListedCrafts() {
        return blackListedCrafts;
    }

    public JCraftList<Craft> getWhiteListedCrafts() {
        return whiteListedCrafts;
    }

    public JPanel getCrafts() {
        return crafts;
    }

    public JPanel getMap() {
        return map;
    }
}
