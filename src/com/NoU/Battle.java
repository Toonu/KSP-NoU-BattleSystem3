package com.NoU;

import com.NoU.Crafts.Craft;
import com.NoU.Systems.IWeaponSystem;

/**
 * @author Tomas Novotny
 */
public class Battle {

    public static boolean attack(Craft aggressor, Craft victim, IWeaponSystem weaponSystem) {

        counter(victim);

        return true;
    }

    public static void counter(Craft victim) {
        victim.getCountermeasures();
    }

    public IWeaponSystem chooseWeapon() {
        return null;
    }
}
