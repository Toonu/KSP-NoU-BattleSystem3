package com.NoU;

import com.NoU.Crafts.AbstractCraft;
import com.NoU.Crafts.Craft;
import com.NoU.Crafts.Type;

import java.util.SortedSet;

public class Application {
    public static int DEFAULT_YEAR;
    public int GLOBAL_TIME = 0;
    private SortedSet<Craft> leftSide;
    private SortedSet<Craft> rightSide;

    Battle b = new Battle();


    public static void main(String[] args) {
        System.out.println("Hello world!");

        Craft groundCraft = new AbstractCraft.Builder().setSide(Side.LEFT).setCraftProductionYear(1950).
                setCSubclass(Type.AFV).buildGround();
        Craft aircraft = new AbstractCraft.Builder().setCSubclass(Type.LIGHTMULTIROLE)
                .setSide(Side.LEFT).setName("F-18").setCraftProductionYear(1990).setSpeed(700)
                .buildAerial();
        Craft naval = new AbstractCraft.Builder().setCSubclass(Type.CARRIER)
                .setSide(Side.LEFT).setName("F-18").setCraftProductionYear(1990).setSpeed(20)
                .buildAerial();
    }
}
