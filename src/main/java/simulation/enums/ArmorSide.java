package simulation.enums;

/**
 * @author Toonu
 * <p>
 * ArmorSide enum to represent armor side values.
 */
public enum ArmorSide {
    FRONT(0, 45), SIDE(45, 135), REAR(135, 225), TOP(0, 0);

    private final double minAngle;
    private final double maxAngle;

    ArmorSide(double minAngle, double maxAngle) {
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    public double getMaxAngle() {
        return maxAngle;
    }

    public double getMinAngle() {
        return minAngle;
    }
}