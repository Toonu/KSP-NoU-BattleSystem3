package com.NoU;

import com.NoU.Crafts.Craft;
import com.NoU.Enum.Era;
import com.NoU.Enum.GuidanceType;
import com.NoU.Enum.Side;
import com.NoU.Enum.Theatre;
import com.NoU.Enum.Type;
import com.NoU.Systems.Ammunition;
import com.NoU.Systems.Gun;
import com.NoU.Systems.Missile;
import com.NoU.Systems.Weapon;
import com.NoU.Utils.WriterReader;

import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Toonu
 * <p>
 * Class representing setting up battle from new weapons, systems and crafts.
 */
public class OrderOfBattle {
    public static void main(String[] args) {
        EnumSet<Theatre> gunSet, airGunSet, missileAA;

        gunSet = EnumSet.of(Theatre.GROUND, Theatre.NAVAL);
        airGunSet = EnumSet.of(Theatre.AERIAL, Theatre.GROUND);
        missileAA = EnumSet.of(Theatre.AERIAL);

        Weapon testGun = new Gun(40, 0, 2, gunSet, "TestGun", Era.Era1950, new Ammunition(1000, 10, 110));
        Weapon airGun = new Gun(20, 0, 2, airGunSet, "AircraftGun", Era.Era1950, new Ammunition(600, 0.02, 10));
        Weapon testMissile = new Missile(40, 0.2, 80, missileAA, "TestMissile", Era.Era1960, GuidanceType.RADAR);

        SortedSet<Weapon> weapons = new TreeSet<>();
        weapons.add(testMissile);
        weapons.add(airGun);
        weapons.add(testGun);

        App.WHITE.addCrafts(new Craft.Builder()
                .setCraftProductionYear(Era.Era1960)
                .setSide(Side.WHITE)
                .setType(Type.AFV)
                .setName("Test")
                .setSpeed(25)
                .build());
        App.BLACK.addCrafts(new Craft.Builder()
                .setCraftProductionYear(Era.Era1960)
                .setSide(Side.WHITE)
                .setType(Type.LIGHTMULTIROLE)
                .setName("Test")
                .setSpeed(25)
                .build());

        LinkedList<Craft> templates = new LinkedList<>();
        if (WriterReader.save(App.WHITE.getCrafts(), "save.txt")) {
            App.WHITE.setCrafts(WriterReader.load("save.txt"));
            System.out.println(templates);
        }

        templates.addAll(WriterReader.loadFile(Paths.get("Database.csv")));
        System.out.println(templates);

        weapons.addAll(WriterReader.readWeaponFile(Paths.get("weapons.txt")));
        System.out.println(weapons);

        App.WHITE.getCrafts().peekFirst().addSystem(airGun);
        App.WHITE.getCrafts().peekFirst().addSystem(airGun);

        System.out.println(App.WHITE.getCrafts().peekFirst().toWeaponList());
    }
}
