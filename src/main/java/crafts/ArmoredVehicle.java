package crafts;

import crafts.parts.Armor;

/**
 * @author Toonu
 * <p>
 * Interface to group all armored vehicles for penetration and damage dealing methods.
 */
public interface ArmoredVehicle {
    /**
     * Method returns armor layout Object from the vehicle.
     *
     * @return Armor object.
     * @throws NullPointerException if there is no armor present.
     */
    Armor getArmor() throws NullPointerException;

    /**
     * Method assign radar to the vehicle.
     *
     * @param armor Armor to add.
     */
    void addArmor(Armor armor);
}
