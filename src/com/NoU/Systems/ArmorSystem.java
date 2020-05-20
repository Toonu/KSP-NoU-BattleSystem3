package com.NoU.Systems;

/**
 * @author Tomas Novotny
 */
public class ArmorSystem extends AbstractDefensiveSystem implements IDefensiveSystem {
    private final double health;

    protected ArmorSystem(int craftProductionYear, String name, SystemType type, double protection, double range,
                          double health) {
        super(craftProductionYear, name, type, protection, range);
        this.health = health;
    }
}
