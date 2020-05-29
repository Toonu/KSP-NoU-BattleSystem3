package ui;

import ui.craftAnalyzer.CraftAnalyzer;
import utils.WriterReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Tomas Novotny
 */
public class JMenuExt extends JMenuBar {
    private final JMenu file = new JMenu("File");
    private final JMenuItem load = new JMenuItem("Load File");
    private final JMenuItem craftAnalyzer = new JMenuItem("Craft Analyzer");
    private final JMenuItem save = new JMenuItem("Save File");

    public JMenuExt(int level) {
        this.add(file);
        file.add(craftAnalyzer);
        file.add(load);
        if (level == 1) {
            file.add(save);
        }


        load.addActionListener(e -> {
            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();


            JDialog frameLoad = new JDialog();
            JFileChooser jfc = new JFileChooser();

            frameLoad.setLocation((width / 2) - (Gui.WIDTH / 2), (height / 2) - (Gui.HEIGHT / 2));
            frameLoad.add(jfc);
            frameLoad.setSize(Gui.WIDTH, Gui.HEIGHT);
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            jfc.setAcceptAllFileFilterUsed(false);
            jfc.setCurrentDirectory(new File(""));


            jfc.addActionListener(e1 -> {
                if (e1.getActionCommand().equals("CancelSelection")) {
                    frameLoad.dispatchEvent(new WindowEvent(frameLoad, WindowEvent.WINDOW_CLOSING));
                } else if (e1.getActionCommand().equals("ApproveSelection")) {
                    File file1 = jfc.getSelectedFile();
                    if (!WriterReader.loadSituationFile(file1)) {
                        System.err.println(String.format("[ERR %s] Error initializing stream. Exception: %s",
                                LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e1));
                    }
                    frameLoad.dispatchEvent(new WindowEvent(frameLoad, WindowEvent.WINDOW_CLOSING));
                    //TODO Add teleport here to battle directly.
                    //TODO Add setup for save to catch if the saved file is from battle or setup.
                }
            });



        craftAnalyzer.addActionListener(ex -> {
            String[] str = new String[1];
            str[0] = "";
            CraftAnalyzer.main(str);
        });
    });
        save.addActionListener(e -> {
            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();


            JDialog frameSave = new JDialog();
            JFileChooser jfc = new JFileChooser();

            frameSave.setLocation((width / 2) - (Gui.WIDTH / 2), (height / 2) - (Gui.HEIGHT / 2));
            frameSave.add(jfc);
            frameSave.setSize(Gui.WIDTH, Gui.HEIGHT);
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            jfc.setAcceptAllFileFilterUsed(false);
            jfc.setCurrentDirectory(new File("."));
            jfc.setApproveButtonText("Save");

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

            frameSave.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frameSave.setVisible(true);
        });
    }
    @Override
    public String getName() {
        return super.getName();
    }

    public JMenu getFile() {
        return file;
    }

    public JMenuItem getCraftAnalyzer() {
        return craftAnalyzer;
    }

    public JMenuItem getLoad() {
        return load;
    }
}
