package simulation.enums;

/**
 * @author Toonu
 * <p>
 * GuidanceType battleGui.enums represents missile guidance type.
 */
public enum GuidanceType {
    IR, RADAR, GPS, BEAM, FALL, ANTIRAD;

    @Override
    public String toString() {
        return String.format("%6s", name());
    }
}
