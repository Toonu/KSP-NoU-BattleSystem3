package com.NoU.Systems;

/**
 * @author Toonu
 */
public class CountermeasureSystem extends AbstractDefensiveSystem implements IDefensiveSystem {
    protected CountermeasureSystem(int craftProductionYear, String name, SystemType type, double protection,
                                   double minRange, double maxRange, Guidance guidance, boolean saturable,
                                   Years years) {
        super(craftProductionYear, name, type, protection, minRange, maxRange, guidance, saturable, years);
    }
}
