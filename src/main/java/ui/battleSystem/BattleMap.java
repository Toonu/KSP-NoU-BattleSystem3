package ui.battleSystem;

import crafts.Craft;
import enums.Side;
import ui.Gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Toonu
 */
public class BattleMap extends JPanel {
    private Image map;
    private Graphics gr;
    private final JPanel centeringPanel = new JPanel(new BorderLayout());
    private double zoom;
    private final double zoomMax;

    /**
     * Constructor.
     */
    public BattleMap() {
        super();
        Dimension monitor = Gui.getMonitorSize();
        zoom = monitor.getHeight() / 1180;
        zoomMax = zoom;


        setPreferredSize(new Dimension((int) Math.round(1920 * zoom), (int) Math.round(1080 * zoom)));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paint(g);
    }

    /**
     * Method paints all elements to the map.
     *
     * @param g Graphics to paint on.
     */
    public void paint(Graphics g) {
        gr = g;

        map = new ImageIcon("map.png").getImage();
        gr.drawImage(map, 0, 0, (int) Math.round(1920 * zoom), (int) Math.round(1080 * zoom), null);

        gr.setColor(Color.WHITE);
        for (Craft craft : Side.WHITE.getCrafts()) {
            if (craft.isSelected()) {
                gr.drawRect((int) Math.round(craft.getPosition().getX() * zoom - 2),
                        (int) (Math.round(craft.getPosition().getY() * zoom - 2)), 5, 5);
                gr.drawString(craft.toLongString(), (int) Math.round(craft.getPosition().getX() * zoom + 5),
                        (int) Math.round(craft.getPosition().getY() * zoom) + 5);
            } else {
                gr.drawString("x", (int) Math.round(craft.getPosition().getX() * zoom),
                        (int) Math.round(craft.getPosition().getY() * zoom));
            }
        }
        for (Craft craft : Side.BLACK.getCrafts()) {
            if (craft.isSelected()) {
                gr.drawRect((int) Math.round(craft.getPosition().getX() * zoom - 2),
                        (int) (Math.round(craft.getPosition().getY() * zoom - 2)), 5, 5);
                gr.drawString(craft.toLongString(), (int) Math.round(craft.getPosition().getX() * zoom + 5),
                        (int) Math.round(craft.getPosition().getY() * zoom) + 5);
            } else {
                gr.drawString("y", (int) Math.round(craft.getPosition().getX() * zoom + 5),
                        (int) Math.round(craft.getPosition().getY() * zoom + 5));
            }
        }
    }

    /**
     * Method zooms into the map.
     */
    public void zoomIn() {
        zoom *= 2;
        setPreferredSize(new Dimension((int) Math.round(1920 * zoom), (int) Math.round(1080 * zoom)));
    }

    /**
     * Method zooms out of the map.
     *
     * @return double -1 if the picture gets bigger than maximal allowed size.
     */
    public double zoomOut() {
        if (1080 * (zoom / 2) < 1080 * zoomMax) {
            return -1;
        } else {
            zoom /= 2;
        }
        setPreferredSize(new Dimension((int) Math.round(1920 * zoom), (int) Math.round(1080 * zoom)));
        return zoom;
    }

    public Image getMap() {
        return map;
    }

    public double getZoom() {
        return zoom;
    }

    public Graphics getGr() {
        return gr;
    }
}
