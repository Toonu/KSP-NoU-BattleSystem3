package crafts.parts;

import systems.KSPPart;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class simulates Recon suite.
 */
public class ReconSuite implements KSPPart, Serializable {
    private final String internalKSPPart;

    /**
     * Constructor.
     */
    public ReconSuite() {
        internalKSPPart = "Recon suite";
    }

    /**
     * Getter for InternalKSPName.
     *
     * @return Internal KSP part name.
     */
    @Override
    public String getInternalKSPName() {
        return internalKSPPart;
    }

    /**
     * Method makes copy of object with same values as attributes. Watch for modifiable lists!!
     *
     * @return Copy of Object.
     */
    @Override
    public KSPPart copy() {
        return new ReconSuite();
    }

    @Override
    public String toString() {
        return internalKSPPart;
    }
}
