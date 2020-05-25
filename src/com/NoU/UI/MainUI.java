package com.NoU.UI;

import javax.swing.*;
import java.awt.*;

/**
 * @author Toonu
 */
public class MainUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new MainFrame("Battle System 3.0");


                //Monitor size
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                int height = gd.getDisplayMode().getHeight();

                frame.setLocation((width / 2) - (MainFrame.WIDTH / 2), (height / 2) - (MainFrame.HEIGHT / 2));
                frame.setSize(MainFrame.WIDTH, MainFrame.HEIGHT);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    public static String convertToMultiline(String orig) {
        String newLine = "<html> <div style='text-align: center;'>" + orig.replaceAll("\n", "<br>");
        return newLine + "</div></html>";
    }
}
