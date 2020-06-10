package simulation.crafts.parts;

import simulation.enums.ArmorSide;

import java.io.Serializable;
import java.util.EnumMap;

/**
 * @author Toonu
 * <p>
 * Armor is representing Armor layout of craft.
 */
public class Armor implements Serializable {
    private final EnumMap<ArmorSide, Double> armorThickness = new EnumMap<>(ArmorSide.class);
    private final EnumMap<ArmorSide, Integer> armorPenetrations = new EnumMap<>(ArmorSide.class);

    /**
     * Constructor which sets up armor thickness on all sides. It also sets up penetration values to 0.
     *
     * @param health String in format of F/S/R/T of front, side, rear and top armor of the vehicle.
     */
    public Armor(String health) {
        String[] newArmor = health.split("/");
        armorThickness.put(ArmorSide.FRONT, Double.parseDouble(newArmor[0]));
        armorThickness.put(ArmorSide.SIDE, Double.parseDouble(newArmor[1]));
        armorThickness.put(ArmorSide.REAR, Double.parseDouble(newArmor[2]));
        armorThickness.put(ArmorSide.TOP, 20d);
        armorPenetrations.put(ArmorSide.FRONT, 0);
        armorPenetrations.put(ArmorSide.SIDE, 0);
        armorPenetrations.put(ArmorSide.REAR, 0);
        armorPenetrations.put(ArmorSide.TOP, 0);
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
    public boolean penetrateArmor(ArmorSide side, double penetration, double hitAngle) {
        if (armorPenetrations.get(side) / Math.cos(Math.PI * hitAngle / 180.0) > 4) {
            return true;
        } else if (armorThickness.get(side) - penetration < 0) {
            armorPenetrations.put(side, armorPenetrations.get(side) + 1);
            return true;
        }
        return false;
    }

    /**
     * Setter for penetration values of specific side of armor.
     *
     * @param side   to set.
     * @param change how much to change amount of penetrations.
     */
    public void setPenetration(ArmorSide side, int change) {
        armorPenetrations.put(side, armorPenetrations.get(side) + change);
    }

    /**
     * Method returns string representation of armor object.
     *
     * @return String of armor values per side and penetration.
     */
    @Override
    public String toString() {
        return String.format("\nArmor Layout [%6s;%3s] [%6s;%3s] [%6s;%3s]",
                armorThickness.get(ArmorSide.FRONT), armorPenetrations.get(ArmorSide.FRONT),
                armorThickness.get(ArmorSide.SIDE), armorPenetrations.get(ArmorSide.SIDE),
                armorThickness.get(ArmorSide.REAR), armorPenetrations.get(ArmorSide.REAR));
    }
}
