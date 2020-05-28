package ui.craftAnalyzer;

<<<<<<< HEAD
import ui.Gui;

=======
>>>>>>> master
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
<<<<<<< HEAD
=======
import java.awt.Color;
>>>>>>> master
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * @author Toonu
 * <p>
 * Class for simple analyse of .craft files for all parts.
 */
public class CraftAnalyzer {
<<<<<<< HEAD
    private static String path = null;
=======
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final Color BACKGROUND = new Color(-16505734);
    public static final Color FOREGROUND = new Color(-14336);
    private static File path = null;
>>>>>>> master

    /**
     * Main method.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Craft Analyser");
<<<<<<< HEAD
            JFileChooser jfc;

            try(BufferedReader br = new BufferedReader(new FileReader(new File("config.cfg")))) {
                path = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (path == null) {
                jfc = new JFileChooser(new File("."));
            } else {
                jfc = new JFileChooser(path);
            }

            jfc.setApproveButtonText("Analyse Craft");
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Craft Files", "craft"));
            jfc.setAcceptAllFileFilterUsed(false);
=======

            JFileChooser jfc = new JFileChooser();
            jfc.setBackground(BACKGROUND);
            jfc.setForeground(FOREGROUND);
            jfc.setApproveButtonText("Analyse Craft");

            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Craft Files", "craft"));
            jfc.setAcceptAllFileFilterUsed(false);

            if (path == null) {
                jfc.setCurrentDirectory(new File(("")));
            } else {
                jfc.setCurrentDirectory(path);
            }

            try (BufferedReader br = new BufferedReader(new FileReader("config.cfg"))) {
                path = new File(br.readLine());
            } catch (IOException | NullPointerException e) {
                path = null;
            }
>>>>>>> master

            jfc.addActionListener(e1 -> {
                if (e1.getActionCommand().equals("CancelSelection")) {
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                } else if (e1.getActionCommand().equals("ApproveSelection")) {
                    File file = jfc.getSelectedFile();
<<<<<<< HEAD
                    setPath(jfc.getSelectedFile().getPath());
=======
                    setPath(jfc.getSelectedFile());
>>>>>>> master
                    try {
                        FileWriter fileWriter = new FileWriter("config.cfg");
                        fileWriter.write(String.valueOf(path));
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    AnalyzedCraft newCraft = getParts(file);

                    Object[] options = {"Show Details", "Close"};

                    if (JOptionPane.showOptionDialog(frame, newCraft, "Analyzer",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == 0) {
                        JOptionPane.showMessageDialog(frame, newCraft.toPartString());
                    }
                }
            });

            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();

<<<<<<< HEAD
            frame.setLocation(Gui.getHalfPosition());
            frame.setSize(Gui.SIZE);
=======
            frame.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
            frame.setSize(WIDTH, HEIGHT);
>>>>>>> master

            frame.add(jfc);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    /**
     * Method makes String of parts from .craft file.
     *
     * @param file .craft file to analyse.
     * @return String to print.
     */
    private static AnalyzedCraft getParts(File file) {
        LinkedList<String> parts = new LinkedList<>();
        ArrayList<String> missiles = new ArrayList<>();
        ArrayList<String> weapons = new ArrayList<>();
        ArrayList<String> systems = new ArrayList<>();
        int hardpoints = 0;
        String craftName = null;
        String lastPart = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                if (Pattern.matches(".*part = .*", line)) {
                    line = line.trim();
                    line = line.substring(7, line.length() - 11);
                    parts.add(line);
                    if (parts.toString().length() - parts.toString().lastIndexOf("\n") > 60) {
                        parts.add("\n");
                    }
                    if (Pattern.matches(".*bahaAdjustableRail.*", line)) {
                        hardpoints += 3;
                    }
                    lastPart = line;
                }

                if (Pattern.matches(".*name = MissileLauncher.*", line)) {
                    missiles.add(lastPart);
                    if (missiles.toString().length() - missiles.toString().lastIndexOf("\n") > 60) {
                        parts.add("\n");
                    }
                } else if (Pattern.matches(".*name = ModuleWeapon.*", line)) {
                    weapons.add(lastPart);
                    if (weapons.toString().length() - weapons.toString().lastIndexOf("\n") > 60) {
                        parts.add("\n");
                    }
                } else if (Pattern.matches(".*name = ModuleRadar.*", line) ||
                        Pattern.matches("name = CM.....", line) ||
                        Pattern.matches(".*ModuleECMJammer.*", line) ||
                        Pattern.matches(".*ModuleTargetingCamera.*", line)) {
                    systems.add(lastPart);
                    if (systems.toString().length() - systems.toString().lastIndexOf("\n") > 60) {
                        parts.add("\n");
                    }
                }
                if (Pattern.matches(".*ship = .*", line)) {
                    line = line.trim();
                    craftName = line.substring(7);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            parts.add(String.format("[ERR %s] Error initializing stream. Exception: %s",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e));
        }
        return new AnalyzedCraft(parts, missiles, weapons, systems, hardpoints, craftName);
    }

<<<<<<< HEAD
    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
=======
    public static File getPath() {
        return path;
    }

    public static void setPath(File path) {
>>>>>>> master
        CraftAnalyzer.path = path;
    }
}
