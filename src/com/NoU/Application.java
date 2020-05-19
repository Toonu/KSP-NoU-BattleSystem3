package com.NoU;

import com.NoU.Crafts.AbstractCraft;
import com.NoU.Crafts.CClass;
import com.NoU.Crafts.CSubclass;
import com.NoU.Crafts.Craft;

import java.util.SortedSet;

public class Application {
    public static int DEFAULT_YEAR;
    public int GLOBAL_TIME = 0;
    private SortedSet<Craft> leftSide;
    private SortedSet<Craft> rightSide;


    public static void main(String[] args) {
        System.out.println("Hello world!");

        Craft groundCraft = new AbstractCraft.Builder().setSide(Side.LEFT).setCraftProductionYear(1950).
                setCClass(CClass.GROUND).setCSubclass(CSubclass.AFV).setHealth(10).buildGround();
        Craft aircraft = new AbstractCraft.Builder().setCClass(CClass.AERIAL).setCSubclass(CSubclass.LIGHTMULTIROLE)
                .setSide(Side.LEFT).setName("F-18").setCraftProductionYear(1990).setHealth(20).setSpeed(700)
                .buildAerial();
        Craft naval = new AbstractCraft.Builder().setCClass(CClass.NAVAL).setCSubclass(CSubclass.CARRIER)
                .setSide(Side.LEFT).setName("F-18").setCraftProductionYear(1990).setHealth(200).setSpeed(20)
                .buildAerial();
    }
}
