package impl;

import crafts.parts.Radar;
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
    private static final Side TEMPLATE = Side.TEMPLATE;
    private static final LinkedList<Weapon> WEAPONS = new LinkedList<>();
    private static final LinkedList<Countermeasure> COUNTERMEASURES = new LinkedList<>();
    private static final LinkedList<Radar> RADARS = new LinkedList<>();


    /**
     * Main method.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        LinkedList<AbstractSystem> systemsDatabase = new LinkedList<>(
                WriterReader.loadSystemsFile(Paths.get("NoU Database - DatabaseForBS.csv")));
        TEMPLATE.setCrafts(WriterReader.loadTemplatesFile(Paths.get("database.csv")));
        WriterReader.filterSystems(systemsDatabase, WEAPONS, COUNTERMEASURES);
        WriterReader.saveSetupFile(new File("save.txt"), true);
        Gui.main(args);
        //TODO Add select module option after clicking continue on welcoming window allowing you to choose modules
        // to load to the database instead of loading everything
        new Thread(new BattleBackground()).start();
    }

    /**
     * Method adds radar to radars.
     *
     * @param r radar.
     */
    public static void addRadar(Radar r) {
        RADARS.add(r);
    }

    /**
     * Method add countermeasure to countermeasures.
     *
     * @param c countermeasure.
     */
    public static void addCountermeasure(Countermeasure c) {
        COUNTERMEASURES.add(c);
    }

    /**
     * Method adds weapon to weapons.
     *
     * @param w weapon.
     */
    public static void addWeapon(Weapon w) {
        WEAPONS.add(w);
    }

    public static LinkedList<Countermeasure> getCountermeasures() {
        return COUNTERMEASURES;
    }

    /**
     * Method sets public countermeasures.
     *
     * @param newCM linked list to replace them.
     */
    public static void setCountermeasures(LinkedList<Countermeasure> newCM) {
        COUNTERMEASURES.clear();
        COUNTERMEASURES.addAll(newCM);
    }

    public static LinkedList<Radar> getRadars() {
        return RADARS;
    }

    public static LinkedList<Weapon> getWeapons() {
        return WEAPONS;
    }

    /**
     * Method sets public weapons.
     *
     * @param weapon linked list to replace them.
     */
    public static void setWeapons(LinkedList<Weapon> weapon) {
        WEAPONS.clear();
        WEAPONS.addAll(weapon);
    }
}
