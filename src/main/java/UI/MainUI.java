package UI;

import javax.swing.*;
import java.awt.*;

/**
 * @author Toonu
 */
public class MainUI {
    public static final String title = "Battle System 3.0";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final Color BACKGROUND = new Color(-16505734);
    public static final Color FOREGROUND = new Color(-14336);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new MainFrame(title);
            JFrame templates = new TemplateFrame(title);


            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();

            /**
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

    public static String convertToMultiline(String orig) {
        String newLine = "<html> <div style='text-align: center;'>" + orig.replaceAll("\n", "<br>");
        return newLine + "</div></html>";
    }
}

