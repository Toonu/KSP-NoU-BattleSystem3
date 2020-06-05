package ui;

import enums.Side;
import impl.App;
import ui.battleSystem.BattleFrame;
import ui.craftAnalyzer.CraftAnalyzer;
import utils.WriterReader;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * @author Tomas Novotny
 */
public class JMenuExt extends JMenuBar {
    private final JMenu file = new JMenu("File");
    private final JMenuItem load = new JMenuItem("Load File");
    private final JMenuItem craftAnalyzer = new JMenuItem("Craft Analyzer");
    private final JMenuItem save = new JMenuItem("Save File");

    /**
     * Constructor for main Menu.
     *
     * @param level sets which buttons are shown.
     */
    public JMenuExt(int level) {
        this.add(file);
        file.add(craftAnalyzer);
        if (level == 1) {
            file.add(save);
        }
        file.add(load);

        load.addActionListener(e -> {
            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException |
                    IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            JDialog frameLoad = new JDialog();
            JFileChooser jfc = new JFileChooser();

            frameLoad.setLocation((width / 2) - (Gui.WIDTH / 2), (height / 2) - (Gui.HEIGHT / 2));
            frameLoad.add(jfc);
            frameLoad.setSize(Gui.WIDTH, Gui.HEIGHT);
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
            jfc.setAcceptAllFileFilterUsed(false);
            jfc.setCurrentDirectory(new File("."));

            jfc.addActionListener(e1 -> {
                if (e1.getActionCommand().equals("CancelSelection")) {
                    frameLoad.dispatchEvent(new WindowEvent(frameLoad, WindowEvent.WINDOW_CLOSING));
                } else if (e1.getActionCommand().equals("ApproveSelection")) {
                    File file = jfc.getSelectedFile();

                    if (!WriterReader.loadSetupFile(file)) {
                        App.err("Error loading the file.", true, true);
                    }
                    frameLoad.dispatchEvent(new WindowEvent(frameLoad, WindowEvent.WINDOW_CLOSING));

                    if (Gui.getWelcomeFrame().isVisible()) {
                        Gui.getWelcomeFrame().dispatchEvent(new WindowEvent(
                                Gui.getWelcomeFrame(), WindowEvent.WINDOW_CLOSING));
                        Gui.getOob().setVisible(true);
                    }
                    Gui.getOob().getWhiteListedCrafts().updateUI(Side.WHITE.getCrafts());
                    Gui.getOob().getBlackListedCrafts().updateUI(Side.BLACK.getCrafts());

                }
            });

            UIManager.getLookAndFeelDefaults();
            frameLoad.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frameLoad.setVisible(true);
            if (App.isRunnable()) {
                Gui.setCurrentWindow(new BattleFrame(Gui.TITLE));
            }
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
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
            jfc.setAcceptAllFileFilterUsed(false);
            jfc.setCurrentDirectory(new File("."));
            jfc.setApproveButtonText("Save");

            jfc.addActionListener(e1 -> {
                if (e1.getActionCommand().equals("CancelSelection")) {
                    frameSave.dispatchEvent(new WindowEvent(frameSave, WindowEvent.WINDOW_CLOSING));
                } else if (e1.getActionCommand().equals("ApproveSelection")) {
                    File file1 = jfc.getSelectedFile();
                    if (!WriterReader.saveSetupFile(file1, true)) {
                        App.err("Error saving the file.", true, true);
                    }

                    frameSave.dispatchEvent(new WindowEvent(frameSave, WindowEvent.WINDOW_CLOSING));
                }
            });

            frameSave.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            UIManager.getLookAndFeelDefaults();
            frameSave.setVisible(true);
        });
        craftAnalyzer.addActionListener(ex -> CraftAnalyzer.main());
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
