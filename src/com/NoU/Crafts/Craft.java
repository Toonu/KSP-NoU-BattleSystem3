package com.NoU.Crafts;

import com.NoU.Side;
import com.NoU.Systems.IDefensiveSystem;
import com.NoU.Systems.IWeaponSystem;
import com.NoU.Vertex2D;

import java.util.List;
import java.util.SortedMap;

/**
 * @author Tomas Novotny
 */
public interface Craft {

    //Getters

    String getName();

    Theatre getCraftClassification();

    Type getType();

    double getHealth();

    int getCraftProductionYear();

    int getTime();

    Side getSide();

    double getSpeed();

    Vertex2D getPosition();

    SortedMap<Double, List<IWeaponSystem>> getWeapons();

    SortedMap<Double, List<IDefensiveSystem>> getCountermeasures();

    String toString();

    String toShortString();

    String toWeaponList();

    String toCountermeasuresList();

    boolean removeWeapon(IWeaponSystem weaponSystem);

    boolean removeDefense(IDefensiveSystem defensiveSystem);

    boolean absorbDamage(double damage);

    void travelDistance(double distance);
}
