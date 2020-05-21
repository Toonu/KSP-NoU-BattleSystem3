package com.NoU.Crafts;

import com.NoU.Enum.Age;
import com.NoU.Enum.Colors;
import com.NoU.Enum.Theatre;
import com.NoU.Enum.Type;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Weapon;
import com.NoU.Vertex2D;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 * @author Toonu
 */
public class Craft {
    public static final int DELAY = 30;
    private final double speed;
    private final String name;
    private final SortedMap<Double, List<Weapon>> weapons;
    private final SortedMap<Double, List<Countermeasure>> countermeasures;
    private final Type type;
    private final Age craftProductionYear;
    private final Colors side;
    private final Vertex2D position;
    private double HP;
    private boolean isWithdrawing = false;
    private int time;

    protected Craft(double speed, String name, SortedMap<Double,
            List<Weapon>> weapons, SortedMap<Double, List<Countermeasure>> countermeasures,
                    Type type, Age craftProductionYear, Colors side, Vertex2D position) {
        this.HP = type.getHealth();
        this.speed = speed;
        this.name = name;

        this.weapons = weapons;
        this.countermeasures = countermeasures;

        this.type = type;
        this.craftProductionYear = craftProductionYear;
        this.side = side;

        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("%s-%s %s: %s Pos: %s", type.getTheatre(), type,
                name, HP, position);
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

    public boolean removeWeapon(Weapon weaponSystem) {
        if (weapons.get(weaponSystem.getMaxRange()).contains(weaponSystem)) {
            weapons.get(weaponSystem.getMaxRange()).remove(weaponSystem);
            return true;
        }
        return false;
    }

    public boolean removeDefense(Countermeasure cm) {
        if (countermeasures.get(cm.getMaxRange()).contains(cm)) {
            countermeasures.get(cm.getMaxRange()).remove(cm);
            return true;
        }
        return false;
    }

    public boolean absorbDamage(double damage) {
        if (HP - damage > 0) {
            HP -= damage;
            return true;
        }
        return false;
    }

    public void travelDistance(double distance) {
        position.setX(position.getX() + distance);
    }

    public void withdraw() {
        isWithdrawing = !isWithdrawing;
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

    public double getHP() {
        return HP;
    }

    public Age getCraftProductionYear() {
        return craftProductionYear;
    }

    public int getTime() {
        return time;
    }

    public Colors getSide() {
        return side;
    }

    public double getSpeed() {
        return speed;
    }

    public Vertex2D getPosition() {
        return position;
    }

    public SortedMap<Double, List<Countermeasure>> getCountermeasures() {
        return Collections.unmodifiableSortedMap(countermeasures);
    }

    public SortedMap<Double, List<Weapon>> getWeapons() {
        return Collections.unmodifiableSortedMap(weapons);
    }

    public boolean isWithdrawing() {
        return isWithdrawing;
    }

    public Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private double HP;
        private double speed;
        private String name;

        private SortedMap<Double, List<Weapon>> weapons;
        private SortedMap<Double, List<Countermeasure>> countermeasures;

        private Type type;
        private Age craftProductionYear;
        private Colors side;

        private Vertex2D position;

        public Builder setCSubclass(Type type) {
            this.type = type;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setColors(Colors side) {
            this.side = side;
            return this;
        }

        public Builder setSpeed(double speed) {
            this.speed = speed;
            return this;
        }

        public Builder setCraftProductionYear(Age craftProductionYear) {
            this.craftProductionYear = craftProductionYear;
            return this;
        }

        public Builder addCountermeasures(SortedMap<Double, List<Countermeasure>> countermeasures) {
            this.countermeasures = countermeasures;
            return this;
        }

        public Builder addWeapons(SortedMap<Double, List<Weapon>> weapons) {
            this.weapons = weapons;
            return this;
        }

        public Builder addWeapon(Weapon weapon) {
            weapons.get(weapon.getMaxRange()).add(weapon);
            return this;
        }

        public Builder addCountermeasure(Countermeasure system) {
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
        public Vehicle buildGround() {
            return new Vehicle(speed, name, weapons, countermeasures, type, craftProductionYear, side, position);
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Aircraft buildAerial() {
            return new Aircraft(speed, name, weapons, countermeasures, type, craftProductionYear, side, position);
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Vessel buildNaval() {
            return new Vessel(speed, name, weapons, countermeasures, type, craftProductionYear, side, position);
        }
    }
}
