package impl;

import comparators.WeaponComparator;
import crafts.Craft;
import enums.GuidanceType;
import enums.Side;
import systems.Countermeasure;
import systems.Gun;
import systems.Missile;
import systems.Weapon;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

/**
 * @author Toonu
 * <p>
 * Class representing second of battle time.
 */
public class BattleSecond extends TimerTask {
    private final Set<Attack> attacks = new HashSet<>();

    //Following Final results, fired weapons, lost weapons, lost vehicles, retreated vehicles.

    /**
     * Constructor.
     */
    public BattleSecond() {
        run();
    }

    /**
     * Method returns all weapons with range against the target.
     *
     * @param craft    aggressor.
     * @param target   target.
     * @param distance to the target.
     * @return weapons in range by damage.
     */
    public static LinkedList<Weapon> getPossibleWeapons(Craft craft, Craft target, double distance) {
        LinkedList<Weapon> possibleWeapons = new LinkedList<>();

        for (Weapon wp : craft.getWeapons()) {
            if (wp instanceof Missile) {
                if (((Missile) wp).getGuidanceType() == GuidanceType.ANTIRAD && target.getRadar() != null) {
                    possibleWeapons.add(wp);
                } else if (wp.getMaxRange() > distance
                        && wp.getTargets().contains(target.getCraftClassification())) {
                    possibleWeapons.add(wp);
                }
            } else if (wp instanceof Gun) {
                if (wp.getMaxRange() > distance && wp.getTargets().contains(target.getCraftClassification())) {
                    possibleWeapons.add(wp);
                }
            }
        }
        possibleWeapons.sort(new WeaponComparator());
        return possibleWeapons;
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        System.out.println(App.returnRealTime());
        App.setGlobalTime(App.getGlobalTime() + 1);
        for (Craft craft : Side.WHITE.getCrafts()) {
            HashMap<Craft, LinkedList<Weapon>> target = findClosest(craft);
            for (Map.Entry<Craft, LinkedList<Weapon>> entry : target.entrySet()) {
                fire(entry.getValue().pollFirst(), craft, entry.getKey());
            }
        }
    }

    /**
     * Method finds closest craft and weapons to engage it.
     *
     * @param craft aggressor craft.
     * @return Target and weapons that can engage it.
     */
    private HashMap<Craft, LinkedList<Weapon>> findClosest(Craft craft) {
        double shortestDistance = 10000;
        Side targetSide = Side.BLACK;
        LinkedList<Craft> targetsByDistance = new LinkedList<>();

        Craft target = null;

        if (craft.getSide() == Side.WHITE) {
            targetSide = Side.WHITE;
        }

        targetsByDistance.add(targetSide.getCrafts().getFirst());

        for (Craft potentialTarget : targetSide.getCrafts()) {
            if (craft.getPosition().distance(potentialTarget.getPosition()) < shortestDistance) {
                targetsByDistance.addFirst(potentialTarget);
                shortestDistance = craft.getPosition().distance(potentialTarget.getPosition());
            }
        }

        LinkedList<Weapon> possibleWeapons = new LinkedList<>();

        while (possibleWeapons.isEmpty()) {
            target = targetsByDistance.pollFirst();
            possibleWeapons = getPossibleWeapons(craft, target, shortestDistance);

        }

        HashMap<Craft, LinkedList<Weapon>> result = new HashMap<>();
        result.put(target, possibleWeapons);

        return result;
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
        //Already chosen weapons and target, now just do attack either immidiately or via Attack object.
        //Add method of  countermeasure and defence
        //Maybe add attack per class due to armor on tanks
        if (weaponSystem instanceof Missile) {
            attacks.add(new Attack((Missile) weaponSystem, aggressor, target));
            aggressor.removeSystem(weaponSystem);
            aggressor.getSide().getLostWeapons().add(weaponSystem);
            return true;
        } else if (weaponSystem instanceof Gun) {
            aggressor.getWeapons().contains()
            aggressor
            return true;
        }
        return false;
    }

    public Set<Attack> getAttacks() {
        return Collections.unmodifiableSet(attacks);
    }
}
