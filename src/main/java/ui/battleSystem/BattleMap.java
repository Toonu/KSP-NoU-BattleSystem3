package ui.battleSystem;

import crafts.Craft;
import impl.OOB;

import javax.swing.*;
import java.awt.*;

/**
 * @author Toonu
 */
public class BattleMap extends JPanel {
    private Image map;
    private Graphics gr;
    private final JPanel centeringPanel = new JPanel(new BorderLayout());
    private double zoom = 1;

    public BattleMap() {
        super();
        zoom = 2;
        setPreferredSize(new Dimension((int) Math.round(1700 * zoom), (int) Math.round(900 * zoom)));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paint(g);
    }

    public void paint(Graphics g) {
        gr = g;

        map = new ImageIcon("map.png").getImage();

        gr.drawImage(map, 0, 0, (int) Math.round(1700 * zoom), (int) Math.round(900 * zoom), null);
        gr.setColor(Color.WHITE);
        gr.drawString("Hello World", 100, 100);
        for (Craft craft : OOB.WHITE.getCrafts()) {
            gr.drawString("x",
                    (int) Math.round(craft.getPosition().getX() * zoom),
                    (int) (Math.round(craft.getPosition().getY() * zoom)));
        }
        for (Craft craft : OOB.BLACK.getCrafts()) {
            gr.drawString("y", (int) Math.round(craft.getPosition().getX() * zoom),
                    (int) Math.round(craft.getPosition().getY() * zoom));
        }
    }

    public void zoomIn() {
        zoom *= 2;
        System.out.println(zoom);
        setPreferredSize(new Dimension((int) Math.round(1700 * zoom), (int) Math.round(900 * zoom)));
    }

    public void zoomOut() {
        zoom /= 2;
        System.out.println(zoom);
        setPreferredSize(new Dimension((int) Math.round(1700 * zoom), (int) Math.round(900 * zoom)));
    }

    public Image getMap() {
        return map;
    }
}
