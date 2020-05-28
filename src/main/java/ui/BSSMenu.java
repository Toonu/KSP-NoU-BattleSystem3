package ui;

import utils.WriterReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Toonu
 */
public class BSSMenu extends BSMenu {
    private final JMenuItem save = new JMenuItem("Save File");

    public BSSMenu() {
        super();
        super.getFile().add(save);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Monitor size
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                int height = gd.getDisplayMode().getHeight();


                JDialog frameSave = new JDialog();
                JFileChooser jfc = new JFileChooser();

                frameSave.setLocation((width / 2) - (MainUI.WIDTH / 2), (height / 2) - (MainUI.HEIGHT / 2));
                frameSave.add(jfc);
                frameSave.setSize(MainUI.WIDTH, MainUI.HEIGHT);
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                jfc.setAcceptAllFileFilterUsed(false);
                jfc.setCurrentDirectory(new File(""));


                jfc.addActionListener(e1 -> {
                    if (e1.getActionCommand().equals("CancelSelection")) {
                        frameSave.dispatchEvent(new WindowEvent(frameSave, WindowEvent.WINDOW_CLOSING));
                    } else if (e1.getActionCommand().equals("ApproveSelection")) {
                        File file1 = jfc.getSelectedFile();

                        if (!WriterReader.saveSituationFile(file1)) {
                            System.err.println(String.format(
                                    "[ERR %s] Error initializing stream and saving the crafts. " +
                                            "Exception: %s", LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e1));
                        }
                        frameSave.dispatchEvent(new WindowEvent(frameSave, WindowEvent.WINDOW_CLOSING));
                    }
                });

                frameSave.setVisible(true);
            }
        });
    }
}
