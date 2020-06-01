package impl;

import crafts.Craft;
import enums.Era;
import utils.Vertex2D;

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
    private static int globalTime = 0;

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
}
