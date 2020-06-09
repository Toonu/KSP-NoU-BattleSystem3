package comparators;

import systems.Weapon;

import java.util.Comparator;

/**
 * @author Toonu
 */
public class WeaponComparator implements Comparator<Weapon> {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     *
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(Weapon o1, Weapon o2) {
        int range = Double.compare(o1.getMaxRange(), o2.getMaxRange());
        if (range == 0) {
            return Double.compare(o1.getStrength(), o2.getStrength());
        } else {
            return range;
        }
    }
}
