package com.NoU.Crafts;

import com.NoU.Side;
import com.NoU.Systems.IDefensiveSystem;
import com.NoU.Systems.IWeaponSystem;
import com.NoU.Systems.Years;
import com.NoU.Vertex2D;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 * @author Toonu
 */
public abstract class AbstractCraft implements Craft {
    private final Type type;
    private final SortedMap<Double, List<IWeaponSystem>> weapons;
    private final SortedMap<Double, List<IDefensiveSystem>> countermeasures;
    private final Years craftProductionYear;
    private final String name;
    private double health;
    private final Side side;
    private final double speed;

    private final Vertex2D position;
    private final boolean isWithdrawing = false;
    private int time;
    private final int delay = 30; // delay before firing another weapon

    protected AbstractCraft(Type type, SortedMap<Double, List<IWeaponSystem>> weapons,
                            SortedMap<Double, List<IDefensiveSystem>> countermeasures, Years craftProductionYear,
                            String name, Side side, double speed) {
        this.type = type;
        this.weapons = weapons;
        this.countermeasures = countermeasures;
        this.craftProductionYear = craftProductionYear;
        this.name = name;
        this.health = type.getHealth();
        this.side = side;
        this.speed = speed;

        if (side == Side.LEFT) {
            this.position = new Vertex2D(-100, 0);
        } else {
            this.position = new Vertex2D(100, 0);
        }
    }

    @Override
    public String toString() {
        return String.format("%s-%s %s: %s Pos: %s", type.getTheatre(), type,
                name, health, position);
    }

    public String toShortString() {
        return String.format("%s %s", type, name);
    }

    public String toWeaponList() {
        return weapons.toString();
    }

    public String toCountermeasuresList() {
        return countermeasures.toString();
    }

    public boolean removeWeapon(IWeaponSystem weaponSystem) {
        if (weapons.get(weaponSystem.getRange()).contains(weaponSystem)) {
            weapons.get(weaponSystem.getRange()).remove(weaponSystem);
            return true;
        }
        return false;
    }

    public boolean removeDefense(IDefensiveSystem defensiveSystem) {
        if (countermeasures.get(defensiveSystem.getMaxRange()).contains(defensiveSystem)) {
            countermeasures.get(defensiveSystem.getMaxRange()).remove(defensiveSystem);
            return true;
        }
        return false;
    }

    public boolean absorbDamage(double damage) {
        if (health - damage > 0) {
            health -= damage;
            return true;
        }
        return false;
    }

    public void travelDistance(double distance) {
        position.setX(position.getX() + distance);
    }

    //Getters

    public String getName() {
        return name;
    }

    public Theatre getCraftClassification() {
        return type.getTheatre();
    }

    public Type getType() {
        return type;
    }

    public double getHealth() {
        return health;
    }

    public Years getCraftProductionYear() {
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

    public Vertex2D getPosition() {
        return position;
    }

    public SortedMap<Double, List<IDefensiveSystem>> getCountermeasures() {
        return Collections.unmodifiableSortedMap(countermeasures);
    }

    public SortedMap<Double, List<IWeaponSystem>> getWeapons() {
        return Collections.unmodifiableSortedMap(weapons);
    }

    public boolean isWithdrawing() {
        return isWithdrawing;
    }

    public static class Builder {
        private Type type;
        private SortedMap<Double, List<IWeaponSystem>> weapons;
        private SortedMap<Double, List<IDefensiveSystem>> countermeasures;
        private Years craftProductionYear;
        private String name;
        private Side side;
        private double speed;

        public Builder setCSubclass(Type type) {
            this.type = type;
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

        public Builder setCraftProductionYear(Years craftProductionYear) {
            this.craftProductionYear = craftProductionYear;
            return this;
        }

        public Builder addCountermeasures(SortedMap<Double, List<IDefensiveSystem>> countermeasures) {
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

        public Builder addCountermeasure(IDefensiveSystem system) {
            countermeasures.get(system.getMaxRange()).add(system);
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
            return new Vehicle(type, weapons, countermeasures, craftProductionYear, name,
                    side, speed);
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Craft buildAerial() {
            return new Aircraft(type, weapons, countermeasures, craftProductionYear, name,
                    side, speed);
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Craft buildNaval() {
            return new Vessel(type, weapons, countermeasures, craftProductionYear, name,
                    side, speed);
        }
    }
    public Builder newBuilder() {
        return new Builder();
    }
}
