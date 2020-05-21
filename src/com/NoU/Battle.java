package com.NoU;

import com.NoU.Crafts.Attack;
import com.NoU.Crafts.Craft;
import com.NoU.Systems.Guidance;
import com.NoU.Systems.IDefensiveSystem;
import com.NoU.Systems.IWeaponSystem;
import com.NoU.Systems.MovableWeapon;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Toonu
 */
public class Battle {
    private final Set<Attack> attacks = new HashSet<>();

    public Battle() {
    }

    public static boolean counter(Attack attack) {
        Craft victim = attack.getTarget();
        MovableWeapon weaponSystem = attack.getWeapon();
        SortedMap<Double, List<IDefensiveSystem>> countermeasures = new TreeMap<>(victim.getCountermeasures());
        Guidance type = weaponSystem.getGuidance();

        for (Double distance : countermeasures.keySet()) {
            if (attack.distanceOfWeapon() < distance) {
                for (IDefensiveSystem countermeasure : countermeasures.get(distance)) {
                    if (countermeasure.getMinRange() < attack.distanceOfWeapon() &&
                            countermeasure.getProtecting() == weaponSystem.getGuidance()) {
                        //TODO Add
                        System.out.println("x");
                    }
                }
            }
        }


        return false;
    }

    public boolean fire(IWeaponSystem weaponSystem, Craft aggressor, Craft target) {
        if (weaponSystem != null && weaponSystem instanceof MovableWeapon) {
            attacks.add(new Attack((MovableWeapon) weaponSystem, aggressor, target));
            aggressor.removeWeapon(weaponSystem);
            return true;
        } else if (weaponSystem != null) {
            aggressor.removeWeapon(weaponSystem);
            return true;
        }
        return false;
    }

    public IWeaponSystem chooseWeapon() {
        return null;
    }

    public Set<Attack> getAttacks() {
        return Collections.unmodifiableSet(attacks);
    }
}
