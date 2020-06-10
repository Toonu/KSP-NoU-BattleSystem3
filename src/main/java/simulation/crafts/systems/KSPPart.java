package simulation.crafts.systems;

/**
 * @author Toonu
 * <p>
 * Interface marks internal KSP parts.
 */
public interface KSPPart {
    /**
     * Getter for InternalKSPName.
     *
     * @return Internal KSP part name.
     */
    String getInternalKSPName();

    /**
     * Method makes copy of object with same values as attributes. Watch for modifiable lists!!
     *
     * @return Copy of Object.
     */
    KSPPart copy();
}
