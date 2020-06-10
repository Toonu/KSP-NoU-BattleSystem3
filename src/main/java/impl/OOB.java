package impl;

import simulation.crafts.parts.FuelTank;
import simulation.crafts.parts.Radar;
import simulation.crafts.parts.ReconSuite;
import simulation.crafts.systems.AbstractSystem;
import simulation.crafts.systems.Countermeasure;
import simulation.crafts.systems.KSPPart;
import simulation.crafts.systems.Weapon;
import simulation.enums.Side;
import simulation.utils.WriterReader;
import ui.Gui;

import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;

/**
 * @author Toonu
 * <p>
 * Class representing setting up battle from new weapons, battleGui.crafts.systems and battleGui.crafts.
 */
public class OOB {
    private static final Side TEMPLATE = Side.TEMPLATE;
    private static final LinkedList<Weapon> WEAPONS = new LinkedList<>();
    private static final LinkedList<Countermeasure> COUNTERMEASURES = new LinkedList<>();
    private static final LinkedList<Radar> RADARS = new LinkedList<>();
    private static final LinkedList<KSPPart> OTHERS = new LinkedList<>();
    private static final Queue<BattleSecond> STAGES = new ArrayDeque<>();
    private static final BattleBackground battle = new BattleBackground();
    private static final Set<Attack> ATTACKS = new HashSet<>();
    private static final Timer t = new Timer();

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
        //new Thread(battle).start();
        //TODO Implement battle mechanics
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
        return battle;
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

    public static Queue<BattleSecond> getSTAGES() {
        return STAGES;
    }

    /**
     * Method adds bs BattleSecond to database.
     *
     * @param bs BattleSecond to add.
     */
    public static void addStage(BattleSecond bs) {
        STAGES.add(bs);
    }

    public static Timer getT() {
        return t;
    }
}
