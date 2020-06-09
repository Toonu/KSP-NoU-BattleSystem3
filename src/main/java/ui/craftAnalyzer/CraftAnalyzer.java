package ui.craftAnalyzer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
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
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * @author Toonu
 * <p>
 * Class for simple analyse of .craft files for all parts.
 */
public class CraftAnalyzer {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final Color BACKGROUND = new Color(-16505734);
    public static final Color FOREGROUND = new Color(-14336);
    private static File path = null;
    private static final JFrame FRAME = new JFrame("Craft Analyser");

    /**
     * Main method.
     */
    public static void main() {
        SwingUtilities.invokeLater(() -> {


            JFileChooser jfc = new JFileChooser();
            jfc.setBackground(BACKGROUND);
            jfc.setForeground(FOREGROUND);
            jfc.setApproveButtonText("Analyse Craft");

            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Craft Files", "craft"));
            jfc.setAcceptAllFileFilterUsed(false);

            if (path == null) {
                jfc.setCurrentDirectory(new File((".")));
            } else {
                jfc.setCurrentDirectory(path);
            }

            try (BufferedReader br = new BufferedReader(new FileReader("config.cfg"))) {
                path = new File(br.readLine());
            } catch (IOException | NullPointerException e) {
                path = null;
            }

            jfc.addActionListener(e1 -> {
                if (e1.getActionCommand().equals("CancelSelection")) {
                    FRAME.dispatchEvent(new WindowEvent(FRAME, WindowEvent.WINDOW_CLOSING));
                } else if (e1.getActionCommand().equals("ApproveSelection")) {
                    File file = jfc.getSelectedFile();
                    setPath(jfc.getSelectedFile());
                    try {
                        FileWriter fileWriter = new FileWriter("config.cfg");
                        fileWriter.write(String.valueOf(path));
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    AnalyzedCraft newCraft = getParts(file);

                    Object[] options = {"Show Details", "Close"};

                    if (JOptionPane.showOptionDialog(FRAME, newCraft +
                                    "\nNote that some parts may not be analyzed correctly. " +
                                    "Check details for further information.", "Analyzer",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == 0) {
                        JOptionPane.showMessageDialog(FRAME, newCraft.toPartString());
                    }
                }
            });

            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();

            FRAME.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
            FRAME.setSize(WIDTH, HEIGHT);

            FRAME.add(jfc);

            FRAME.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            FRAME.setVisible(true);
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
        int hardpoints = 0;
        String craftName = null;
        String lastPart = "";
        boolean hasBDAModule = false;
        boolean hasAIPilot = false;
        boolean checkBDAModule = false;
        boolean checkAIPilot = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                if (craftName == null && Pattern.matches(".*ship = .*", line)) {
                    line = line.trim();
                    craftName = line.substring(7);
                }

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
                }

                if (Pattern.matches(".*MissileFire.*", line)) {
                    hasBDAModule = true;
                    checkBDAModule = true;
                } else if (checkBDAModule && Pattern.matches(".*guardRange.*", line)) {
                    String[] guardRange = line.split("=");
                    if (Double.parseDouble(guardRange[1].trim()) <= 199000) {
                        JOptionPane.showMessageDialog(FRAME,
                                "Craft has not set AI visible distance properly (Under 200km) You should " +
                                        "warn the player that the craft might not see enemies at long ranges.",
                                "Wrong AI", JOptionPane.WARNING_MESSAGE);
                    }
                    checkBDAModule = false;
                }

                if (Pattern.matches(".*BDModulePilotAI.*", line)) {
                    hasAIPilot = true;
                    checkAIPilot = true;
                } else if (checkAIPilot && Pattern.matches(".*standbyMode.*", line)) {
                    String[] pilotLine = line.split("=");
                    if (pilotLine[1].trim().equals("False")) {
                        JOptionPane.showMessageDialog(FRAME,
                                "Craft has not set AI Pilot to standby mode! You should " +
                                        "warn the player that the craft might start unexpectedly in battles.",
                                "Wrong AI Pilot", JOptionPane.WARNING_MESSAGE);
                    }
                    checkAIPilot = false;
                }

                line = br.readLine();
            }
        } catch (IOException e) {
            parts.add(String.format("[ERR %s] Error initializing stream. Exception: %s",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e));
        }
        if (!hasBDAModule) {
            JOptionPane.showMessageDialog(FRAME,
                    "Craft is missing BDA Manager module!",
                    "Wrong AI", JOptionPane.WARNING_MESSAGE);
        }
        if (!hasAIPilot) {
            JOptionPane.showMessageDialog(FRAME,
                    "Craft is missing BDA AI Pilot module!",
                    "Wrong AI Pilot", JOptionPane.WARNING_MESSAGE);
        }

        return new AnalyzedCraft(parts, hardpoints, craftName, hasBDAModule);
    }

    public static File getPath() {
        return path;
    }

    public static void setPath(File path) {
        CraftAnalyzer.path = path;
    }
}
