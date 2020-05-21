package com.NoU.Systems;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toonu
 */
public class ArmorSystem extends AbstractDefensiveSystem implements IDefensiveSystem {
    private final Map<ArmorSide, Double> armor = new HashMap<>();
    private final Map<ArmorSide, Integer> penetration = new HashMap<>();


    protected ArmorSystem(int craftProductionYear, String name, SystemType type, double strength, double minRange,
                          double maxRange, Guidance protecting, boolean saturable, Years years, String health) {
        super(craftProductionYear, name, type, strength, minRange, maxRange, protecting, saturable, years);

        String[] newArmor = health.split("/");

        armor.put(ArmorSide.FRONT, Double.parseDouble(newArmor[0]));
        armor.put(ArmorSide.SIDE, Double.parseDouble(newArmor[1]));
        armor.put(ArmorSide.REAR, Double.parseDouble(newArmor[2]));
    }

    public boolean isPenetrated(ArmorSide side, double damage) {
        return armor.get(side) - damage < 0;
    }

    private enum ArmorSide {
        FRONT, SIDE, REAR, TOP
    }

}
