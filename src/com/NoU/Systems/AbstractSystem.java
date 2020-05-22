package com.NoU.Systems;

import com.NoU.Enum.Age;

/**
 * @author Toonu
 *
 * Class representing System basic values extended in subclasses.
 */
public class AbstractSystem {
    private final double strength;
    private final double maxRange;
    private final double minRange;
    private final Age age;
    private final String name;

    /**
     * Constructor.
     *
     * @param strength double strength value of system for either defense or attack damage.
     * @param maxRange double maximal range of system.
     * @param minRange double minimal range of system.
     * @param name     String name of the system.
     * @param age      Age enum of era of the system.
     */
    public AbstractSystem(double strength, double maxRange, double minRange, String name, Age age) {
        this.strength = strength;
        this.maxRange = maxRange;
        this.minRange = minRange;
        this.age = age;
        this.name = name;
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

    public String getName() {
        return name;
    }
}
