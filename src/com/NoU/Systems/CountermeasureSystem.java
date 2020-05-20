package com.NoU.Systems;

/**
 * @author Tomas Novotny
 */
public class CountermeasureSystem extends AbstractDefensiveSystem implements IDefensiveSystem {
    protected CountermeasureSystem(int craftProductionYear, String name, SystemType type, double protection, double range) {
        super(craftProductionYear, name, type, protection, range);
    }
}
