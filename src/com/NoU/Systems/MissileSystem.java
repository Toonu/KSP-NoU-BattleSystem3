package com.NoU.Systems;

import com.NoU.Crafts.Theatre;
import com.NoU.Vertex2D;

import java.util.Set;

/**
 * @author Tomas Novotny
 */
public class MissileSystem extends AbstractWeaponSystem implements IWeaponSystem, MovableWeapon {
    private final Vertex2D position;

    protected MissileSystem(int craftProductionYear, String name, Set<Theatre> targets, double damage, double range
            , Vertex2D position) {
        super(craftProductionYear, name, targets, damage, range);
        this.position = position;
    }

    public Vertex2D getPosition() {
        return position;
    }
}
