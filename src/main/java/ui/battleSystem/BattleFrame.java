package ui.battleSystem;

import crafts.Craft;
import impl.OOB;
import ui.Gui;
import ui.JCraftList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        JScrollPane mapScroll = new JScrollPane(map);
        mapScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        mapScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        crafts.add(whiteListedCrafts, BorderLayout.NORTH);
        crafts.add(blackListedCrafts, BorderLayout.SOUTH);
        crafts.setBackground(Gui.BACKGROUND);
        crafts.setForeground(Gui.FOREGROUND);

        whiteListedCrafts.updateUI(OOB.WHITE.getCrafts());
        blackListedCrafts.updateUI(OOB.BLACK.getCrafts());

        GridBagConstraints gbcc = new GridBagConstraints();
        gbcc.anchor = GridBagConstraints.CENTER;
        BattleMap battleMap = new BattleMap();
        map.add(battleMap, gbcc);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.gridheight = 2;

        map.setAutoscrolls(true);

        MouseAdapter dragging = new MouseAdapter() {

            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, map);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        map.scrollRectToVisible(view);
                    }
                }
            }

        };

        map.addMouseListener(dragging);
        map.addMouseMotionListener(dragging);

        c.add(mapScroll, gbc);

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
            bm.invalidate();
            bm.updateUI();

            double oldZoom = bm.getZoom();
            double newZoom = oldZoom * 2;
            Rectangle oldView = mapScroll.getViewport().getViewRect();

            Point newViewPos = new Point();
            newViewPos.x = (int) Math.max(0, (oldView.x + oldView.width * 2) * newZoom / oldZoom - oldView.width * 2);
            newViewPos.y = (int) Math.max(0, (oldView.y + oldView.height * 2) * newZoom / oldZoom - oldView.height * 2);
            mapScroll.getViewport().setViewPosition(newViewPos);
        });
        c.add(zoomIn, gbc);

        gbc.gridx = 2;
        JButton zoomOut = new JButton("Zoom Out");
        zoomOut.addActionListener(e -> {
            BattleMap bm = (BattleMap) map.getComponent(0);
            bm.zoomOut();
            bm.invalidate();
            bm.updateUI();

            double oldZoom = bm.getZoom();
            double newZoom = oldZoom/2;
            Rectangle oldView = mapScroll.getViewport().getViewRect();

            Point newViewPos = new Point();
            newViewPos.x = (int) Math.max(0, (oldView.x + oldView.width / 2) * newZoom / oldZoom - oldView.width / 2);
            newViewPos.y = (int) Math.max(0, (oldView.y + oldView.height / 2) * newZoom / oldZoom - oldView.height / 2);
            mapScroll.getViewport().setViewPosition(newViewPos);
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
