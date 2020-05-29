package impl;

import enums.Side;
import systems.Countermeasure;
import systems.Weapon;
import ui.Gui;
import utils.WriterReader;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
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


        TEMPLATE.setCrafts(WriterReader.loadCSVFile(importResource("database.csv").getPath()));
        TEMPLATE_COUNTERMEASURES.addAll(WriterReader.readCMFile(importResource("countermeasures.txt").getPath()));
        TEMPLATE_WEAPONS.addAll(WriterReader.readWeaponFile(importResource("weapons.txt").getPath()));
        WriterReader.saveSetupFile(new File("save.txt"), true);
        Gui.main(args);
    }

    public static File importResource(String name) {
        URL res = OOB.class.getClassLoader().getResource(name);
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
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
