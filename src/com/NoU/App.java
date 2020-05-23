package com.NoU;

import com.NoU.Crafts.Craft;
import com.NoU.Enum.Era;
import com.NoU.Enum.Side;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Main Application Class.
 */
public class App {
    public static boolean DEBUG = false;
    public static final Era DEFAULT_YEAR = Era.Era1950;
    public static final Vertex2D SPAWN_A = new Vertex2D(-100, 0);
    public static final Vertex2D SPAWN_B = new Vertex2D(100, 0);
    public static int GLOBAL_TIME = 0;
    public static Side WHITE = Side.WHITE;
    public static Side BLACK = Side.BLACK;


    public static void main(String[] args) {
        SortedSet<Craft> leftSide = new TreeSet<>();
        SortedSet<Craft> rightSide = new TreeSet<>();
        DEBUG = true;
    }
}
