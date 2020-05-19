package com.NoU;

import com.NoU.Crafts.CClass;
import com.NoU.Crafts.CSubclass;
import com.NoU.Crafts.Craft;
import com.NoU.Crafts.Vehicle;

public class Application {


    public static void main(String[] args) {
        System.out.println("Hello world!");

        Craft craft = new Vehicle.Builder().setSide(Side.LEFT).setCraftProductionYear(1950).
                setCClass(CClass.GROUND).setCSubclass(CSubclass.AFV).setHealth(10).buildGround();
    }
}
