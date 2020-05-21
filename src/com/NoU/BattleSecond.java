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
import java.util.TreeMap;

/**
 * @author Toonu
 */
public class BattleSecond {
    private final Set<Attack> attacks = new HashSet<>();

    //Previous - OOB or loading from save file the setup
    //Check every X seconds if crafts can fire or undergoing attacks, then perform those attacks and go to next second
    //Following Final results, fired weapons, lost weapons, lost vehicles, retreated vehicles.

    //Maybe return each second the results of the second ?

    public BattleSecond() {
    }

    public static boolean counter(Attack attack) {
        Craft victim = attack.getTarget();
        Missile weaponSystem = attack.getWeapon();
        SortedMap<Double, List<Countermeasure>> countermeasures = new TreeMap<>(victim.getCountermeasures());
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

    public boolean fire(Weapon weaponSystem, Craft aggressor, Craft target) {
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
        return null;
    }

    public Set<Attack> getAttacks() {
        return Collections.unmodifiableSet(attacks);
    }
}
