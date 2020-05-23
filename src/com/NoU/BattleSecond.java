package com.NoU;

import com.NoU.Crafts.Craft;
import com.NoU.Enum.GuidanceType;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Missile;
import com.NoU.Systems.Weapon;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

/**
 * @author Toonu
 *
 * Class representing second of battle time.
 */
public class BattleSecond {
    private final Set<Attack> attacks = new HashSet<>();

    //Previous - OOB or loading from save file the setup
    //Check every X seconds if crafts can fire or undergoing attacks, then perform those attacks and go to next second
    //Following Final results, fired weapons, lost weapons, lost vehicles, retreated vehicles.

    //Maybe return each second the results of the second ?

    /**
     * Constructor.
     */
    public BattleSecond(int Time) {
    }

    /**
     * Method to simulate defense against attack.
     *
     * @param attack Attack object to perform.
     * @return true if the attack was finished.
     */
    public static boolean counter(Attack attack) {
        if (attack.distanceOfWeapon() < 2) {
            Craft victim = attack.getTarget();
            Missile weaponSystem = attack.getWeapon();
            SortedMap<Double, List<Countermeasure>> countermeasures = victim.getCountermeasures();

            GuidanceType type = weaponSystem.getGuidanceType();

            for (Double distance : countermeasures.keySet()) {
                if (attack.distanceOfWeapon() < distance) {
                    for (Countermeasure countermeasure : countermeasures.get(distance)) {
                        if (countermeasure.getMinRange() < attack.distanceOfWeapon() &&
                                countermeasure.getAgainst().contains(weaponSystem.getGuidanceType())) {
                            //TODO Add
                            System.out.println("x");
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Method representing firing weapon against aggressor from target.
     *
     * @param weaponSystem Weapon object to fire. If movable, it creates new Attack object.
     * @param aggressor    Craft object to fire from.
     * @param target       Craft object targetted.
     * @return true if the weapon fired.
     */
    public boolean fire(Weapon weaponSystem, Craft aggressor, Craft target) {
        //TODO Add failure to launch missile here.

        if (weaponSystem instanceof Missile) {
            attacks.add(new Attack((Missile) weaponSystem, aggressor, target));
            aggressor.removeWeapon(weaponSystem);
            return true;
        } else if (weaponSystem != null) {
            aggressor.removeWeapon(weaponSystem);
            return true;
        }
        return false;
    }

    public Weapon chooseWeapon() {
        //TODO Add choosing weapon to fire here? Probably...
        return null;
    }

    public Set<Attack> getAttacks() {
        return Collections.unmodifiableSet(attacks);
    }
}
