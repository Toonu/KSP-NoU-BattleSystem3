package com.NoU.Systems;

/**
 * @author Tomas Novotny
 */
public class RadarSystem extends AbstractDefensiveSystem implements IDefensiveSystem {
    private final double radarRange;

    protected RadarSystem(int craftProductionYear, String name, SystemType type, double protection, double range,
                          double radarRange) {
        super(craftProductionYear, name, type, protection, range);
        this.radarRange = radarRange;
    }
}
