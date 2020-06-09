package crafts.parts;

import systems.KSPPart;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class simulates fuel tank for extended duration of fligth.
 */
public class FuelTank implements KSPPart, Serializable {
    private final String internalKSPName;

    /**
     * Constructor.
     */
    public FuelTank() {
        internalKSPName = "FuelTank tank";
    }

    /**
     * Getter for InternalKSPName.
     *
     * @return Internal KSP part name.
     */
    @Override
    public String getInternalKSPName() {
        return internalKSPName;
    }

    /**
     * Method makes copy of object with same values as attributes. Watch for modifiable lists!!
     *
     * @return Copy of Object.
     */
    @Override
    public KSPPart copy() {
        return new FuelTank();
    }

    @Override
    public String toString() {
        return internalKSPName;
    }
}
