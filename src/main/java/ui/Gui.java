package ui;


import ui.battleSystem.BattleFrame;
import ui.battleSystem.OOBFrame;
import ui.battleSystem.WelcomeFrame;

import javax.swing.JFrame;
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
    private static OOBFrame oob;
    private static WelcomeFrame welcomeFrame;
    private static BattleFrame battleFrame;
    private static JFrame currentWindow;

    /**
     * Main class for User Interface of the whole application.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            oob = new OOBFrame(TITLE, battleFrame);
            welcomeFrame = new WelcomeFrame(TITLE, oob);
            setCurrentWindow(welcomeFrame);
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

    /**
     * Method returns center position on the monitor.
     *
     * @return Point.
     */
    public static Point getCenterPosition() {
        return new Point((getMonitorSize().width / 2) - (WIDTH / 2), (getMonitorSize().height / 2) - (HEIGHT / 2));
    }

    /**
     * Method returns center position on the monitor for sized window.
     *
     * @param width  size of window.
     * @param height size of window.
     * @return Point.
     */
    public static Point getCenterPosition(int width, int height) {
        return new Point((getMonitorSize().width / 2) - (width / 2), (getMonitorSize().height / 2) - (height / 2));
    }

    /**
     * Returns Dimension of monitor size.
     *
     * @return Dimension.
     */
    public static Dimension getMonitorSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        return new Dimension(width, height);
    }

    public static OOBFrame getOob() {
        return oob;
    }

    public static WelcomeFrame getWelcomeFrame() {
        return welcomeFrame;
    }

    public static JFrame getCurrentWindow() {
        return currentWindow;
    }

    public static void setCurrentWindow(JFrame currentWindow) {
        Gui.currentWindow = currentWindow;
    }
}

