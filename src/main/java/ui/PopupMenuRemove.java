package ui;

import simulation.crafts.Craft;
import simulation.enums.Side;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author Toonu
 */
public class PopupMenuRemove {
    /**
     * Constructor for popup menu.
     *
     * @param selected Which list should be updated with removal.
     */
    public PopupMenuRemove(List<Craft> selected) {
        JFrame f = new JFrame("PopupMenu Example");
        JMenuItem remove = new JMenuItem("Remove");
        remove.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                for (Craft craft : selected) {
                    if (craft.getSide() == Side.WHITE) {
                        Side.WHITE.removeCraft(craft);
                        Gui.getOob().getWhiteListedCrafts().updateUI(Side.WHITE.getCrafts());
                    } else {
                        Side.BLACK.removeCraft(craft);
                        Gui.getOob().getBlackListedCrafts().updateUI(Side.BLACK.getCrafts());
                    }
                }
                f.setVisible(false);
            }
        });
        f.getContentPane().add(remove);
        f.setLocation(MouseInfo.getPointerInfo().getLocation());
        f.setUndecorated(true);
        f.setSize(60, 26);
        f.setVisible(true);
    }
}
