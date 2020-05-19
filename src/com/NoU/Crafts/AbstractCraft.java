package com.NoU.Crafts;

import com.NoU.Side;
import com.NoU.Systems.IDefensiveSystem;
import com.NoU.Systems.IWeaponSystem;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author Toonu
 */
public abstract class AbstractCraft implements Craft {
    private final CClass cClass;
    private final CSubclass cSubclass;
    private final SortedMap<Double, List<IWeaponSystem>> weapons;
    private final List<IDefensiveSystem> countermeasures;
    private final int craftProductionYear;
    private final String name;
    private final double health;
    private final Side side;
    private final double speed;

    private final double distanceFromMiddle = 5;
    private final boolean isWithdrawing = false;
    private int time;

    protected AbstractCraft(CClass cClass, CSubclass cSubclass, SortedMap<Double, List<IWeaponSystem>> weapons,
                            List<IDefensiveSystem> countermeasures, int craftProductionYear, String name, double health,
                            Side side, double speed) {
        this.cClass = cClass;
        this.cSubclass = cSubclass;
        this.weapons = weapons;
        this.countermeasures = countermeasures;
        this.craftProductionYear = craftProductionYear;
        this.name = name;
        this.health = health;
        this.side = side;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public CClass getcClass() {
        return cClass;
    }

    public CSubclass getcSubclass() {
        return cSubclass;
    }

    public double getDistanceFromMiddle() {
        return distanceFromMiddle;
    }

    public double getHealth() {
        return health;
    }

    public int getCraftProductionYear() {
        return craftProductionYear;
    }

    public int getTime() {
        return time;
    }

    public Side getSide() {
        return side;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return String.format("%s-%s %s: %s %s", cClass, cSubclass, name, health, distanceFromMiddle);
    }

    public List<IDefensiveSystem> getCountermeasures() {
        return Collections.unmodifiableList(countermeasures);
    }

    public Map<Double, List<IWeaponSystem>> getWeapons() {
        return Collections.unmodifiableSortedMap(weapons);
    }

    public static class Builder {
        private CClass cClass;
        private CSubclass cSubclass;
        private SortedMap<Double, List<IWeaponSystem>> weapons;
        private List<IDefensiveSystem> countermeasures;
        private int craftProductionYear;
        private String name;
        private double health;
        private Side side;
        private double speed;

        public Builder setCClass(CClass cClass) {
            this.cClass = cClass;
            return this;
        }

        public Builder setCSubclass(CSubclass cSubclass) {
            this.cSubclass = cSubclass;
            return this;
        }

        public Builder setHealth(double health) {
            this.health = health;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSide(Side side) {
            this.side = side;
            return this;
        }

        public Builder setSpeed(double speed) {
            this.speed = speed;
            return this;
        }

        public Builder setCraftProductionYear(int craftProductionYear) {
            this.craftProductionYear = craftProductionYear;
            return this;
        }

        public Builder addCountermeasures(List<IDefensiveSystem> countermeasures) {
            this.countermeasures = countermeasures;
            return this;
        }

        public Builder addWeapons(SortedMap<Double, List<IWeaponSystem>> weapons) {
            this.weapons = weapons;
            return this;
        }

        public Builder addWeapon(IWeaponSystem weapon) {
            weapons.get(weapon.getRange()).add(weapon);
            return this;
        }

        public Builder addSystem(IDefensiveSystem system) {
            countermeasures.add(system);
            return this;
        }

        public Builder readWeapons(Path path) {
            return this;
        }

        public Builder readCountermeasures(Path path) {
            return this;
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Craft buildGround() {
            return new Vehicle(cClass, cSubclass, weapons, countermeasures, craftProductionYear, name,
                    health, side, speed);
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Craft buildAerial() {
            return new Aircraft(cClass, cSubclass, weapons, countermeasures, craftProductionYear, name,
                    health, side, speed);
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Craft buildNaval() {
            return new Vessel(cClass, cSubclass, weapons, countermeasures, craftProductionYear, name,
                    health, side, speed);
        }
    }
    public Builder newBuilder() {
        return new Builder();
    }

}
