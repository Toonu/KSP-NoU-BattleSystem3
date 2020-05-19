package com.NoU.Systems;

/**
 * @author Tomas Novotny
 */
public class RadarSystem extends AbstractDefensiveSystem implements IDefensiveSystem {
    private double radarRange;

    protected RadarSystem(int craftProductionYear, String name, SystemType type, double protection) {
        super(craftProductionYear, name, type, protection);
    }
}
