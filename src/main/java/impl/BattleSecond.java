package impl;

import crafts.Craft;
import enums.GuidanceType;
import systems.Countermeasure;
import systems.Missile;
import systems.Weapon;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TimerTask;

/**
 * @author Toonu
 * <p>
 * Class representing second of battle time.
 */
public class BattleSecond extends TimerTask {
    private final Set<Attack> attacks = new HashSet<>();

    //Check every X seconds if crafts can fire or undergoing attacks, then perform those attacks and go to next second
    //Following Final results, fired weapons, lost weapons, lost vehicles, retreated vehicles.

    //Maybe return each second the results of the second ?

    /**
     * Constructor.
     */
    public BattleSecond() {
        run();
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        System.out.println(App.returnRealTime());
        App.setGlobalTime(App.getGlobalTime() + 1);
    }

    /**
     * Method to simulate defense against attack.
     *
     * @param attack impl.Attack object to perform.
     * @return true if the attack was finished.
     */
    public static boolean counter(Attack attack) {
        if (attack.distanceOfWeapon() < 2) {
            Craft victim = attack.getTarget();
            Missile weaponSystem = attack.getWeapon();
            LinkedList<Countermeasure> countermeasures = victim.getCountermeasures();

            GuidanceType type = weaponSystem.getGuidanceType();

            double distance = countermeasures.peekFirst().getMaxRange();
            if (attack.distanceOfWeapon() < distance) {
                for (Countermeasure countermeasure : countermeasures) {
                    if (countermeasure.getMinRange() < attack.distanceOfWeapon() &&
                            countermeasure.getTargets().contains(weaponSystem.getGuidanceType())) {
                        //TODO Add
                        System.out.println("x");
                    }
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Method calculates impact angle between the targets.
     *
     * @param aggressor vehicle that fired.
     * @param target    vehicle that are hit and calculated.
     * @return angle of hit.
     */
    public double hitAngle(Craft aggressor, Craft target) {
        double angle = (Math.toDegrees(Math.atan2(aggressor.getPosition().getY() - target.getPosition().getY(),
                aggressor.getPosition().getX() - target.getPosition().getX())));
        angle -= target.getAngle();
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    /**
     * Method representing firing weapon against aggressor from target.
     *
     * @param weaponSystem Weapon object to fire. If movable, it creates new impl.Attack object.
     * @param aggressor    Craft object to fire from.
     * @param target       Craft object targeted.
     * @return true if the weapon fired.
     */
    public boolean fire(Weapon weaponSystem, Craft aggressor, Craft target) {
        //TODO Add failure to launch missile here.

        if (weaponSystem instanceof Missile) {
            attacks.add(new Attack((Missile) weaponSystem, aggressor, target));
            aggressor.removeSystem(weaponSystem);
            return true;
        } else if (weaponSystem != null) {
            aggressor.removeSystem(weaponSystem);
            return true;
        }
        return false;
    }

    /**
     * Choose a weapon.
     *
     * @return Weapon.
     */
    public Weapon chooseWeapon() {
        //TODO Add choosing weapon to fire here? Probably...
        return null;
    }

    public Set<Attack> getAttacks() {
        return Collections.unmodifiableSet(attacks);
    }
}
