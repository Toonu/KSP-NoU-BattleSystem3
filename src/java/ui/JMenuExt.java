package ui;

import ui.craftAnalyzer.CraftAnalyzer;
import utils.WriterReader;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private final JMenuItem save = new JMenuItem("Save File");
    private final JMenuItem craftAnalyzer = new JMenuItem("Craft Analyzer");

    public JMenuExt(int extended) {
        add(file);
        file.add(craftAnalyzer);
        file.add(load);

        if (extended == 1) {
            file.add(save);
        }

        load.addActionListener(e -> {
            JDialog frameLoad = new JDialog();
            JFileChooser jfc = new JFileChooser(new File("."));

            frameLoad.add(jfc);
            frameLoad.setLocation(Gui.getHalfPosition());
            frameLoad.setSize(Gui.SIZE);
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            jfc.setAcceptAllFileFilterUsed(false);

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
        save.addActionListener(e -> {
            JDialog frameSave = new JDialog();
            JFileChooser jfc = new JFileChooser(new File("."));

            frameSave.add(jfc);
            frameSave.setLocation(Gui.getHalfPosition());
            frameSave.setSize(Gui.getMonitorSize());
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            jfc.setAcceptAllFileFilterUsed(false);
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

    public JMenuItem getSave() {
        return save;
    }
}
