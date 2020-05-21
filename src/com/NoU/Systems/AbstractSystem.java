package com.NoU.Systems;

import com.NoU.Enum.Age;

/**
 * @author Toonu
 */
public class AbstractSystem {
    private final double strength;
    private final double maxRange;
    private final double minRange;
    private final Age age;


    public AbstractSystem(double strength, double maxRange, double minRange, Age age) {
        this.strength = strength;
        this.maxRange = maxRange;
        this.minRange = minRange;
        this.age = age;
    }

    public double getStrength() {
        return strength;
    }

    public Age getAge() {
        return age;
    }

    public double getMaxRange() {
        return maxRange;
    }

    public double getMinRange() {
        return minRange;
    }
}
