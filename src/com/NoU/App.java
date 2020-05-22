package com.NoU;

import com.NoU.Crafts.Craft;
import com.NoU.Enum.Age;

import java.util.SortedSet;

public class App {
    public static final boolean DEBUG = false;
    public static Age DEFAULT_YEAR = Age.X1950;
    public static Vertex2D SPAWN_A = new Vertex2D(-100, 0);
    public static Vertex2D SPAWN_B = new Vertex2D(100, 0);
    public int GLOBAL_TIME = 0;
    private SortedSet<Craft> leftSide;
    private SortedSet<Craft> rightSide;

    BattleSecond b = new BattleSecond();

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
