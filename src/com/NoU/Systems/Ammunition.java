package com.NoU.Systems;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class representing ammunition type for gun objects and their penetration value.
 */
public class Ammunition implements Serializable {
    private final double penetration;

    /**
     * Constructor.
     *
     * @param speed      double speed of the projectile.
     * @param bulletMass double mass of the projectile.
     * @param calibre    double calibre of the projectile.
     */
    public Ammunition(double speed, double bulletMass, double calibre) {
        this.penetration = (16f * speed * Math.sqrt(bulletMass / 1000) / Math.sqrt(calibre));
    }

    public double getPenetration() {
        return penetration;
    }
}
