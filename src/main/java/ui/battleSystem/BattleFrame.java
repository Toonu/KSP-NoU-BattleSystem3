package ui.battleSystem;

import crafts.Craft;
import enums.Side;
import ui.Gui;
import ui.JCraftList;
import ui.JMenuExt;

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

    /**
     * Constructor.
     *
     * @param title title of the frame.
     */
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

        whiteListedCrafts.updateUI(Side.WHITE.getCrafts());
        whiteListedCrafts.addListSelectionListener(e -> {
            try {
                Side.WHITE.getCrafts().get(Side.WHITE.getCrafts()
                        .indexOf(whiteListedCrafts.getSelectedValue())).select();
                map.updateUI();
            } catch (IndexOutOfBoundsException ex) {
                ex.getMessage();
            }
            whiteListedCrafts.clearSelection();
        });
        blackListedCrafts.updateUI(Side.BLACK.getCrafts());
        blackListedCrafts.addListSelectionListener(e -> {
            try {
                Side.BLACK.getCrafts().get(Side.BLACK.getCrafts()
                        .indexOf(blackListedCrafts.getSelectedValue())).select();
                map.updateUI();
            } catch (IndexOutOfBoundsException ex) {
                ex.getMessage();
            }
            blackListedCrafts.clearSelection();
        });

        GridBagConstraints gbcInternal = new GridBagConstraints();
        gbcInternal.anchor = GridBagConstraints.CENTER;
        BattleMap battleMap = new BattleMap();
        map.add(battleMap, gbcInternal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weighty = 0.005;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JMenuExt menu = new JMenuExt(1);
        c.add(menu, gbc);


        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.997;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.995;
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

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.003;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        c.add(crafts, gbc);

        gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.weighty = 0.05;
        gbc.weightx = 0.001;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 2;
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

            newViewPos.x = Math.max(0, (oldView.x + oldView.width / 2) * 2 - oldView.width / 2);
            newViewPos.y = Math.max(0, (oldView.y + oldView.height / 2) * 2 - oldView.height / 2);
            mapScroll.getViewport().setViewPosition(newViewPos);
        });
        c.add(zoomIn, gbc);

        gbc.gridx = 2;
        JButton zoomOut = new JButton("Zoom Out");
        zoomOut.addActionListener(e -> {
            BattleMap bm = (BattleMap) map.getComponent(0);
            double factor = bm.zoomOut();
            bm.invalidate();
            bm.updateUI();

            if (factor == -1) {
                return;
            }

            double oldZoom = bm.getZoom();
            double newZoom = oldZoom / 2;
            Rectangle oldView = mapScroll.getViewport().getViewRect();

            Point newViewPos = new Point();
            newViewPos.x = (int) Math.max(0, (oldView.x + oldView.width / 2) * 0.5 - oldView.width / 2);
            newViewPos.y = (int) Math.max(0, (oldView.y + oldView.height / 2) * 0.5 - oldView.height / 2);
            mapScroll.getViewport().setViewPosition(newViewPos);
        });
        c.add(zoomOut, gbc);

        gbc.gridx = 3;
        JButton step = new JButton("Next step");
        c.add(step, gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public Container getC() {
        return c;
    }
}
