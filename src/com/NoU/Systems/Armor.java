package com.NoU.Systems;

import com.NoU.Enum.ArmorSide;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Toonu
 */
public class Armor {
    private final EnumMap<ArmorSide, Double> armor = new EnumMap<>(ArmorSide.class);
    private final EnumMap<ArmorSide, Integer> penetrated = new EnumMap<>(ArmorSide.class);

    protected Armor(String health) {
        String[] newArmor = health.split("/");
        armor.put(ArmorSide.FRONT, Double.parseDouble(newArmor[0]));
        armor.put(ArmorSide.SIDE, Double.parseDouble(newArmor[1]));
        armor.put(ArmorSide.REAR, Double.parseDouble(newArmor[2]));
    }

    public boolean isPenetrated(ArmorSide side, double penetration, double hitAngle) {
        if (penetrated.get(side) / Math.cos(Math.PI * hitAngle / 180.0) > 4) {
            return true;
        } else if (armor.get(side) - penetration < 0) {
            penetrated.put(side, penetrated.get(side) + 1);
            return true;
        }
        return false;
    }

    public Map<ArmorSide, Double> getArmor() {
        return Collections.unmodifiableMap(armor);
    }
}
