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
public class BSMenu extends JMenuBar {
    private final JMenu file = new JMenu("File");
    private final JMenuItem load = new JMenuItem("Load File");
    private final JMenuItem craftAnalyzer = new JMenuItem("Craft Analyzer");


    public BSMenu() {
        this.add(file);
        file.add(craftAnalyzer);
        file.add(load);

        load.addActionListener(e -> {
            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();


            JDialog frameLoad = new JDialog();
            JFileChooser jfc = new JFileChooser();

            frameLoad.setLocation((width / 2) - (MainUI.WIDTH / 2), (height / 2) - (MainUI.HEIGHT / 2));
            frameLoad.add(jfc);
            frameLoad.setSize(MainUI.WIDTH, MainUI.HEIGHT);
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


            frameLoad.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frameLoad.setVisible(true);
        });
        craftAnalyzer.addActionListener(e -> {
            String[] str = new String[1];
            str[0] = "";
            CraftAnalyzer.main(str);
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
