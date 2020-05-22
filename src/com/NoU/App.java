package com.NoU;

import com.NoU.Crafts.Craft;
import com.NoU.Enum.Era;
import com.NoU.Enum.GuidanceType;
import com.NoU.Enum.Sides;
import com.NoU.Enum.Theatre;
import com.NoU.Enum.Type;
import com.NoU.Systems.Ammunition;
import com.NoU.Systems.Gun;
import com.NoU.Systems.Missile;
import com.NoU.Systems.Weapon;
import com.NoU.Utils.WriterReader;

import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Main Application Class.
 */
public class App {
    public static boolean DEBUG = false;
    public static final Era DEFAULT_YEAR = Era.Era1950;
    public static final Vertex2D SPAWN_A = new Vertex2D(-100, 0);
    public static final Vertex2D SPAWN_B = new Vertex2D(100, 0);
    public static int GLOBAL_TIME = 0;

    BattleSecond b = new BattleSecond();


    public static void main(String[] args) {
        Set<Craft> leftSide = new HashSet<>();
        Set<Craft> rightSide = new HashSet<>();

        EnumSet<Theatre> gunSet, airGunSet, missileAA;

        gunSet = EnumSet.of(Theatre.GROUND, Theatre.NAVAL);
        airGunSet = EnumSet.of(Theatre.AERIAL, Theatre.GROUND);
        missileAA = EnumSet.of(Theatre.AERIAL);

        System.out.println("Hello world!");

        Weapon testGun = new Gun(40, 0, 2, gunSet, "TestGun", Era.Era1950, new Ammunition(1000, 10, 110));
        Weapon airGun = new Gun(20, 0, 2, airGunSet, "AircraftGun", Era.Era1950, new Ammunition(600, 0.02, 10));
        Weapon testMissile = new Missile(40, 0.2, 80, missileAA, "TestMissile", Era.Era1960, GuidanceType.RADAR);

        DEBUG = true;

        leftSide.add(new Craft.Builder()
                .setCraftProductionYear(Era.Era1960)
                .setSide(Sides.WHITE)
                .setType(Type.AFV)
                .setName("Test")
                .setSpeed(25)
                .addSystem(testGun).addSystem(airGun).addSystem(testMissile)
                .build());
        leftSide.add(new Craft.Builder()
                .setCraftProductionYear(Era.Era1960)
                .setSide(Sides.WHITE)
                .setType(Type.LIGHTMULTIROLE)
                .setName("Test")
                .setSpeed(25)
                .build());
        Set<Craft> newSet = new HashSet<>();
        if (WriterReader.save(leftSide)) {
            newSet = WriterReader.load();
        }


        System.out.println(newSet);

        newSet = WriterReader.loadFile(Paths.get("Database.csv"));
    }
}
