package Impl;

import Crafts.Craft;
import Enum.Era;
import Enum.Side;
import Enum.Type;
import Systems.Countermeasure;
import Systems.Weapon;
import UI.MainUI;
import Utils.Vertex2D;
import Utils.WriterReader;

import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * @author Toonu
 * <p>
 * Class representing setting up battle from new weapons, systems and crafts.
 */
public class OOB {
    public static Side WHITE = Side.WHITE;
    public static Side BLACK = Side.BLACK;
    public static Side TEMPLATE = Side.TEMPLATE;
    public static LinkedList<Weapon> TEMPLATE_WEAPONS;
    public static LinkedList<Countermeasure> TEMPLATE_COUNTERMEASURES;

    public static void main(String[] args) {
        TEMPLATE.setCrafts(WriterReader.loadCSVFile(Paths.get("database.csv")));
        MainUI.main(args);
    }

    public static void oldMain(String[] args) {
        Craft testCraft = new Craft.Builder()
                .setCraftProductionYear(Era.Era1960)
                .setSide(Side.WHITE)
                .setType(Type.AFV)
                .setName("Test")
                .setSpeed(1000)
                .build();
        Craft test2Craft = new Craft.Builder()
                .setCraftProductionYear(Era.Era1960)
                .setSide(Side.WHITE)
                .setType(Type.LIGHTMULTIROLE)
                .setName("Test")
                .setSpeed(25)
                .build();

        OOB.WHITE.addCraft(testCraft);
        OOB.WHITE.addCraft(test2Craft);

        if (WriterReader.saveSituation(OOB.WHITE.getCrafts(), "save.txt")) {
            OOB.WHITE.setCrafts(WriterReader.loadSituation("save.txt"));
        }

        OOB.TEMPLATE.setCrafts(WriterReader.loadCSVFile(Paths.get("Database.csv")));
        TEMPLATE_COUNTERMEASURES = WriterReader.readCMFile(Paths.get("weapons.txt"));
        TEMPLATE_WEAPONS = WriterReader.readWeaponFile(Paths.get("weapons.txt"));

        testCraft.setPosition(new Vertex2D(10, 10));
        test2Craft.setPosition(new Vertex2D(10, 0));
        System.out.println(testCraft.getPosition());
        testCraft.setAngle(225);
        BattleSecond battleSecond = new BattleSecond(0);
        System.out.println(battleSecond.hitAngle(test2Craft, testCraft));
        while (testCraft.getPosition().getX() > 0) {
            testCraft.moveTowardCenter();
            System.out.println(testCraft.getPosition());
            System.out.println(battleSecond.hitAngle(test2Craft, testCraft));
        }
    }
}
