package simulation.crafts.systems;

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
     * @throws IllegalArgumentException when the numbers are negative.
     */
    public Ammunition(double speed, double bulletMass, double calibre) throws IllegalArgumentException {
        if (speed > 0 && bulletMass > 0 && calibre > 0) {
            this.penetration = (16f * speed * Math.sqrt(bulletMass / 1000) / Math.sqrt(calibre));
        } else {
            throw new IllegalArgumentException("Ammo speed, mass or calibre cannot be negative.");
        }
    }


    public double getPenetration() {
        return penetration;
    }
}
