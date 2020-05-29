package ui;


import ui.battleSystem.OOBFrame;
import ui.battleSystem.WelcomeFrame;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

/**
 * @author Toonu
 */
public class Gui {
    public static final String TITLE = "Battle System 3.0";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);
    public static final Color BACKGROUND = new Color(-16505734);
    public static final Color FOREGROUND = new Color(-14336);
    public static final JMenuExt MENU = new JMenuExt(0);
    public static final String PATH = System.getProperty("user.dir");
    public static OOBFrame oob;
    public static WelcomeFrame welcomeFrame;

    /**
     * Main class for User Interface of the whole application.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            oob = new OOBFrame(TITLE);
            welcomeFrame = new WelcomeFrame(TITLE, oob);
        });
    }

    /**
     * Method to convert multiline text to html to be shown by label.
     *
     * @param orig text to convert.
     * @return converted text.
     */
    public static String convertToMultiline(String orig) {
        String newLine = "<html> <div style='text-align: center;'>" + orig.replaceAll("\n", "<br>");
        return newLine + "</div></html>";
    }

    public static Point getCenterPosition() {
        return new Point((getMonitorSize().width/2)-(WIDTH/2), (getMonitorSize().height/2)-(HEIGHT/2));
    }

    public static Dimension getMonitorSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        return new Dimension(width, height);
    }
}

