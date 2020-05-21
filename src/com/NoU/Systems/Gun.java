package com.NoU.Systems;

import com.NoU.Enum.Age;
import com.NoU.Enum.Theatre;

import java.util.Set;

/**
 * @author Toonu
 */
public class Gun extends Weapon {
    private final Ammunition ammunition;
    private int ammoBox = 36;

    public Gun(double damage, double minRange, double maxRange, Set<Theatre> targets, Age age,
               Ammunition ammunition) {
        super(damage, minRange, maxRange, targets, age);
        this.ammunition = ammunition;
    }

    public Gun(double damage, double minRange, double maxRange, Set<Theatre> targets, Age age,
               Ammunition ammunition, int ammo) {
        this(damage, minRange, maxRange, targets, age, ammunition);
        ammoBox = ammo;
    }

    public Ammunition getAmmunition() {
        return ammunition;
    }

    public void fireGun() {
        ammoBox -= 1;
    }
}
