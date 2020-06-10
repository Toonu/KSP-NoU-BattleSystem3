package impl;

import crafts.parts.FuelTank;
import crafts.parts.Radar;
import crafts.parts.ReconSuite;
import enums.Side;
import systems.AbstractSystem;
import systems.Countermeasure;
import systems.KSPPart;
import systems.Weapon;
import ui.Gui;
import utils.WriterReader;

import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

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
    private static final LinkedList<KSPPart> OTHERS = new LinkedList<>();
    private static final Queue<BattleSecond> STAGES = new ArrayDeque<>();
    private static BattleBackground battleBackground;
    private static final Set<Attack> ATTACKS = new HashSet<>();

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
        OTHERS.add(new ReconSuite());
        OTHERS.add(new FuelTank());

        Gui.main(args);
        //BattleBackground battle = new BattleBackground();
        //new Thread(battle).start();
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

    public static LinkedList<KSPPart> getOTHERS() {
        return OTHERS;
    }

    public static BattleBackground getBattleBackground() {
        return battleBackground;
    }

    public static Set<Attack> getAttacks() {
        return ATTACKS;
    }

    /**
     * Method adds attack to the attacks.
     *
     * @param attack to add.
     */
    public static void addAttack(Attack attack) {
        ATTACKS.add(attack);
    }


}
