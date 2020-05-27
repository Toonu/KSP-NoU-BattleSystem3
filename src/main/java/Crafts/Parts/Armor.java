package Crafts.Parts;

import Enum.ArmorSide;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Toonu
 * <p>
 * Armor is representing Armor layout of craft.
 */
public class Armor implements Serializable {
    private final EnumMap<ArmorSide, Double> armor = new EnumMap<>(ArmorSide.class);
    private final EnumMap<ArmorSide, Integer> penetrated = new EnumMap<>(ArmorSide.class);

    /**
     * Constructor.
     *
     * @param health String in format of F/S/R/T of front, side, rear and top armor of the vehicle.
     */
    protected Armor(String health) {
        String[] newArmor = health.split("/");
        armor.put(ArmorSide.FRONT, Double.parseDouble(newArmor[0]));
        armor.put(ArmorSide.SIDE, Double.parseDouble(newArmor[1]));
        armor.put(ArmorSide.REAR, Double.parseDouble(newArmor[2]));
    }

    /**
     * Method checks if the projectile penetrated the armor and returns false if not.
     * Five times subsequently penetrated armor is considered destroyed and always penetrate.
     *
     * @param side        ArmorSide enum side to penetrate.
     * @param penetration double penetration value in mm.
     * @param hitAngle    angle of hitting the armor panel.
     * @return True if the projectile penetrated. False if not.
     */
    public boolean isPenetrated(ArmorSide side, double penetration, double hitAngle) {
        if (hitAngle == 0) {
            hitAngle = 90;
        }
        if (penetrated.get(side) / Math.cos(Math.PI * hitAngle / 180.0) > 4) {
            return true;
        } else if (armor.get(side) - penetration < 0) {
            penetrated.put(side, penetrated.get(side) + 1);
            return true;
        }
        return false;
    }

    public Map<ArmorSide, Double> getArmor() {
        return Collections.unmodifiableMap(armor);
    }
}
