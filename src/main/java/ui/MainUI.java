package ui;


import ui.battleSystem.EquipingFrame;
import ui.battleSystem.MainFrame;
import ui.battleSystem.TemplateFrame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * @author Toonu
 */
public class MainUI {
    public static final String TITLE = "Battle System 3.0";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final Color BACKGROUND = new Color(-16505734);
    public static final Color FOREGROUND = new Color(-14336);
    public static final BSMenu MENU = new BSMenu();

    /**
     * Main class for User Interface of the whole application.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();


            EquipingFrame equipingFrame = new EquipingFrame(TITLE);
            TemplateFrame templates = new TemplateFrame(TITLE, equipingFrame);
            MainFrame frame = new MainFrame(TITLE, templates);

            frame.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
            frame.setSize(WIDTH, HEIGHT);
            templates.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
            templates.setSize(WIDTH, HEIGHT);
            equipingFrame.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
            equipingFrame.setSize(WIDTH, HEIGHT);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
            templates.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            templates.setVisible(false);
            equipingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            equipingFrame.setVisible(false);
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

