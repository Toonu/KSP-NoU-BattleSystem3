package impl;

import enums.Side;
import systems.AbstractSystem;
import systems.Countermeasure;
import systems.Weapon;
import ui.Gui;
import utils.WriterReader;

import java.io.File;
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
        LinkedList<AbstractSystem> systemsDatabase = new LinkedList<>(
                WriterReader.loadSystemsFile(Paths.get("NoU Database - DatabaseForBS.csv")));
        TEMPLATE.setCrafts(WriterReader.loadCSVFile(Paths.get("database.csv")));
        WriterReader.filterSystems(systemsDatabase, TEMPLATE_WEAPONS, TEMPLATE_COUNTERMEASURES);
        WriterReader.saveSetupFile(new File("save.txt"), true);
        Gui.main(args);
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
