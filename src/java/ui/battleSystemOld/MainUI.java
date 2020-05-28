package ui.battleSystemOld;


import ui.JMenuExt;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * @author Toonu
 */
public class MainUI extends JFrame {
    public static final String TITLE = "Battle System 3.0";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final Color BACKGROUND = new Color(-16505734);
    public static final Color FOREGROUND = new Color(-14336);
    public static final JMenuExt MENU = new JMenuExt(0);
    public static final String PATH = System.getProperty("user.dir");

    public MainUI() {
        super();
        //Monitor size
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        EquipingFrame equipingFrame = new EquipingFrame(TITLE);
        TemplateFrame templates = new TemplateFrame(TITLE, equipingFrame);
        MainFrame mainFrame = new MainFrame(templates);

        mainFrame.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
        mainFrame.setSize(WIDTH, HEIGHT);
        templates.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
        templates.setSize(WIDTH, HEIGHT);
        equipingFrame.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
        equipingFrame.setSize(WIDTH, HEIGHT);

        add(mainFrame);

        mainFrame.setVisible(true);
        templates.setVisible(false);
        equipingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        equipingFrame.setVisible(false);
    }

    /**
     * Main class for User Interface of the whole application.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI mainUI = new MainUI();
            mainUI.pack();
            mainUI.setVisible(true);
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
}

