package impl;

import crafts.Craft;
import enums.Era;
import enums.Side;
import enums.Type;
import systems.Countermeasure;
import systems.Weapon;
import ui.Gui;
import utils.Vertex2D;
import utils.WriterReader;

import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * @author Toonu
 * <p>
 * Class representing setting up battle from new weapons, systems and crafts.
 */
public class OOB {
    public static final Side WHITE = Side.WHITE;
    public static final Side BLACK = Side.BLACK;
    public static final Side TEMPLATE = Side.TEMPLATE;
    public static final LinkedList<Weapon> TEMPLATE_WEAPONS = new LinkedList<>();
    public static final LinkedList<Countermeasure> TEMPLATE_COUNTERMEASURES = new LinkedList<>();

    /**
     * Main method.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        TEMPLATE.setCrafts(WriterReader.loadCSVFile(Paths.get("database.csv")));
        TEMPLATE_COUNTERMEASURES.addAll(WriterReader.readCMFile(Paths.get("countermeasures.txt")));
        TEMPLATE_WEAPONS.addAll(WriterReader.readWeaponFile(Paths.get("weapons.txt")));
        Gui.main(args);
    }

    /**
     * Old main method for tests.
     *
     * @param args args.
     */
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

        if (WriterReader.saveSituation("save.txt")) {
            WriterReader.loadSituation("save.txt");
        }

        OOB.TEMPLATE.setCrafts(WriterReader.loadCSVFile(Paths.get("Database.csv")));
        OOB.setCountermeasures(WriterReader.readCMFile(Paths.get("weapons.txt")));
        OOB.setWeapons(WriterReader.readWeaponFile(Paths.get("weapons.txt")));

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

    /**
     * Method sets public countermeasures.
     *
     * @param newCM linked list to replace them.
     */
    public static void setCountermeasures(LinkedList<Countermeasure> newCM) {
        TEMPLATE_COUNTERMEASURES.clear();
        TEMPLATE_COUNTERMEASURES.addAll(newCM);
    }

    /**
     * Method sets public weapons.
     *
     * @param newWEAP linked list to replace them.
     */
    public static void setWeapons(LinkedList<Weapon> newWEAP) {
        TEMPLATE_WEAPONS.clear();
        TEMPLATE_WEAPONS.addAll(newWEAP);
    }
}
