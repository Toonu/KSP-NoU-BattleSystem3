package ui.battleSystem;

import crafts.Craft;
import impl.OOB;
import ui.Gui;
import ui.JCraftList;

import javax.swing.*;
import java.awt.*;

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
    private final JPanel map = new JPanel(new GridBagLayout());

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
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.gridheight = 2;
        mapScroll.add(map);

        c.add(mapScroll, gbc);
        map.repaint();
        GridBagConstraints gbcc = new GridBagConstraints();
        gbcc.anchor = GridBagConstraints.CENTER;

        map.add(new BattleMap(), gbcc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.95;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        c.add(crafts, gbc);

        gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.weighty = 0.05;
        gbc.gridy = 1;
        JButton zoomIn = new JButton("Zoom In ");
        zoomIn.addActionListener(e -> {
            BattleMap bm = (BattleMap) map.getComponent(0);
            bm.zoomIn();
            bm.updateUI();
        });
        c.add(zoomIn, gbc);

        gbc.gridx = 2;
        JButton zoomOut = new JButton("Zoom Out");
        zoomOut.addActionListener(e -> {
            BattleMap bm = (BattleMap) map.getComponent(0);
            bm.zoomOut();
            bm.updateUI();
        });
        c.add(zoomOut, gbc);

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
