package com.NoU.Systems;

/**
 * @author Toonu
 */
public class Ammunition {
    private final double penetration;

    public Ammunition(double speed, double bulletMass, double caliber) {
        this.penetration = (16f * speed * Math.sqrt(bulletMass / 1000) / Math.sqrt(caliber));
    }
}
