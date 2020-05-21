package com.NoU.Systems;

import com.NoU.Vertex2D;

/**
 * @author Tomas Novotny
 */
public interface MovableWeapon extends IWeaponSystem {
    Vertex2D getPosition();

    Guidance getGuidance();
}
