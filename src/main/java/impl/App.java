package impl;

import crafts.Craft;
import enums.Era;
import ui.Gui;
import utils.Vertex2D;

import javax.swing.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Gui Application Class.
 *
 * @author Toonu
 */
public class App {
    public static final Vertex2D SPAWN_A = new Vertex2D(750, 700);
    public static final Vertex2D SPAWN_B = new Vertex2D(950, 700);
    public static final Era DEFAULT_YEAR = Era.Era1950;

    private static boolean debug = true;
    private static boolean finished = false;
    private static int globalTime = 0;
    private static boolean runnable = false;

    /**
     * Main method for the whole project and application.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        SortedSet<Craft> leftSide = new TreeSet<>();
        SortedSet<Craft> rightSide = new TreeSet<>();
        debug = true;
        OOB.main(args);
    }

    /**
     * Method generating and logging messages.
     *
     * @param text    message text.
     * @param isError if error message.
     * @param gui     if gui popup is shown.
     */
    public static void err(String text, boolean isError, boolean gui) {
        if (isError) {
            System.err.println(String.format("[ERR %s] %s.",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), text));
            if (gui) {
                JOptionPane.showMessageDialog(Gui.getCurrentWindow(), text, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println(String.format("[LOG %s] %s",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), text));
            if (gui && debug) {
                JOptionPane.showMessageDialog(Gui.getCurrentWindow(), text,
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Method returns real time value from seconds int.
     *
     * @return String of H:M:S
     */
    public static String returnRealTime() {
        int hours = globalTime / 3600;
        int minutes = globalTime / 60 % 60;
        int seconds = globalTime % 60;
        return String.format("%s:%s:%s", hours, minutes, seconds);
    }

    public static int getGlobalTime() {
        return globalTime;
    }

    public static void setGlobalTime(int globalTime) {
        App.globalTime = globalTime;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        App.debug = debug;
    }

    public static boolean isFinished() {
        return finished;
    }

    public static void setFinished(boolean finished) {
        App.finished = finished;
    }

    public static void setRunnable(boolean runnable) {
        App.runnable = runnable;
    }

    public static boolean isRunnable() {
        return runnable;
    }
}
