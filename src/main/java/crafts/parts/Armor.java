package crafts.parts;

import enums.ArmorSide;

import java.io.Serializable;
import java.util.EnumMap;

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
    public Armor(String health) {
        String[] newArmor = health.split("/");
        armor.put(ArmorSide.FRONT, Double.parseDouble(newArmor[0]));
        armor.put(ArmorSide.SIDE, Double.parseDouble(newArmor[1]));
        armor.put(ArmorSide.REAR, Double.parseDouble(newArmor[2]));
        armor.put(ArmorSide.TOP, 20d);
        penetrated.put(ArmorSide.FRONT, 0);
        penetrated.put(ArmorSide.SIDE, 0);
        penetrated.put(ArmorSide.REAR, 0);
        penetrated.put(ArmorSide.TOP, 0);
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

    public EnumMap<ArmorSide, Integer> getPenetrated() {
        return penetrated;
    }

    /**
     * Method returns string representation of armor object.
     *
     * @return String of armor values per side and penetration.
     */
    @Override
    public String toString() {
        return String.format("\nArmor Layout [%6s;%3s] [%6s;%3s] [%6s;%3s]",
                armor.get(ArmorSide.FRONT), penetrated.get(ArmorSide.FRONT),
                armor.get(ArmorSide.SIDE), penetrated.get(ArmorSide.SIDE),
                armor.get(ArmorSide.REAR), penetrated.get(ArmorSide.REAR));
    }
}
