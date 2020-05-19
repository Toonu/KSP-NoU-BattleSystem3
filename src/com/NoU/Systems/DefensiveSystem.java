package com.NoU.Systems;

/**
 * @author Tomas Novotny
 */
public class DefensiveSystem extends AbstractDefensiveSystem implements IDefensiveSystem {
    protected DefensiveSystem(int craftProductionYear, String name, SystemType type, double protection) {
        super(craftProductionYear, name, type, protection);
    }
}
