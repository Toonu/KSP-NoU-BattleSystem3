package com.NoU.Crafts;

import com.NoU.Side;

/**
 * @author Tomas Novotny
 */
public interface Craft {

    //Getters

    String getName();

    CClass getcClass();

    CSubclass getcSubclass();

    double getDistanceFromMiddle();

    double getHealth();

    int getCraftProductionYear();

    int getTime();

    Side getSide();

    double getSpeed();

    String toString();


}
