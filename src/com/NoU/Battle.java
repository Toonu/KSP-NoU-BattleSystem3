package com.NoU;

import com.NoU.Crafts.Attack;
import com.NoU.Crafts.Craft;
import com.NoU.Systems.IWeaponSystem;
import com.NoU.Systems.MovableWeapon;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Tomas Novotny
 */
public class Battle {
    private final Set<Attack> attacks = new HashSet<>();

    public Battle() {
    }

    public static boolean counter(Craft victim) {

        victim.getCountermeasures();

        return false;
    }

    public boolean fire(MovableWeapon weaponSystem, Craft aggressor, Craft target) {
        try {
            attacks.add(new Attack(weaponSystem, aggressor, target));
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public boolean fire(IWeaponSystem weaponSystem, Craft aggressor, Craft target) {
        //Attacks instantly
        return false;
    }

    public IWeaponSystem chooseWeapon() {
        return null;
    }

    public Set<Attack> getAttacks() {
        return Collections.unmodifiableSet(attacks);
    }
}
