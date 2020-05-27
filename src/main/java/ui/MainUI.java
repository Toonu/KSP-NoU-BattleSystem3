package ui;


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

    /**
     * Main class for User Interface of the whole application.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new MainFrame(TITLE);
            JFrame templates = new TemplateFrame(TITLE);


            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();

/*
            frame.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
            frame.setSize(WIDTH, HEIGHT);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
**/

         templates.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
         templates.setSize(WIDTH, HEIGHT);

         templates.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         templates.setVisible(true);

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

