package com.NoU;

import com.NoU.Crafts.Craft;
import com.NoU.Enum.Age;
import com.NoU.Enum.Sides;
import com.NoU.Enum.Type;
import com.NoU.Utils.WriterReader;

import java.util.HashSet;
import java.util.Set;

/**
 * Main Application Class.
 */
public class App {
    public static boolean DEBUG = false;
    public static Age DEFAULT_YEAR = Age.X1950;
    public static Vertex2D SPAWN_A = new Vertex2D(-100, 0);
    public static Vertex2D SPAWN_B = new Vertex2D(100, 0);
    public static int GLOBAL_TIME = 0;

    BattleSecond b = new BattleSecond();


    public static void main(String[] args) {
        Set<Craft> leftSide = new HashSet<>();
        Set<Craft> rightSide = new HashSet<>();


        System.out.println("Hello world!");

        leftSide.add(new Craft.Builder()
                .setCraftProductionYear(Age.X1960)
                .setSide(Sides.WHITE)
                .setType(Type.AFV)
                .setName("Test")
                .setSpeed(25)
                .build());
        leftSide.add(new Craft.Builder()
                .setCraftProductionYear(Age.X1960)
                .setSide(Sides.WHITE)
                .setType(Type.LIGHTMULTIROLE)
                .setName("Test")
                .setSpeed(25)
                .build());

        WriterReader.save(leftSide);
        Set<Craft> newSet = WriterReader.load();

        System.out.println(newSet);
    }
}
