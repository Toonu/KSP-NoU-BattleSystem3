package ui.battleSystem;

import crafts.Craft;
import impl.OOB;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @author Toonu
 */
public class BattleMap extends JPanel {
    private Image map;
    private Graphics gr;

    public BattleMap() {
        super();
        setPreferredSize(new Dimension(1700, 900));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        gr = g;

        map = new ImageIcon("map.png").getImage();

        gr.drawImage(map, 0, 0, null);
        gr.setColor(Color.WHITE);
        gr.drawString("Hello World", 100, 100);
        for (Craft craft : OOB.WHITE.getCrafts()) {
            gr.drawString("x", (int) Math.round(craft.getPosition().getX()), (int) Math.round(craft.getPosition().getY()));
        }
        for (Craft craft : OOB.BLACK.getCrafts()) {
            gr.drawString("y", (int) Math.round(craft.getPosition().getX()), (int) Math.round(craft.getPosition().getY()));
        }
    }

    public void zoom(Image image) {
        int newImageWidth = 1920 * 2;
        int newImageHeight = 1080 * 2;
        BufferedImage resizedImage = new BufferedImage(newImageWidth, newImageHeight, 1);
        gr = resizedImage.createGraphics();
        gr.drawImage(image, 0, 0, newImageWidth, newImageHeight, null);
        gr.dispose();
        this.repaint();
    }

    public Image getMap() {
        return map;
    }
}
