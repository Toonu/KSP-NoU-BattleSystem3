package com.NoU.Crafts;

import com.NoU.Side;
import com.NoU.Systems.IDefensiveSystem;
import com.NoU.Systems.IWeaponSystem;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @author Toonu
 */
public abstract class AbstractCraft implements Craft{
    private final CClass cClass;
    private final CSubclass cSubclass;
    private final Map<CClass, List<IWeaponSystem>> weapons;
    private final List<IDefensiveSystem> countermeasures;
    private final int craftProductionYear;
    private final String name;
    private final double health;
    private final Side side;

    private final double distanceFromMiddle = 100;
    private final boolean isWithdrawing = false;
    private int craftTurn;

    protected AbstractCraft(CClass cClass, CSubclass cSubclass, Map<CClass, List<IWeaponSystem>> weapons,
                            List<IDefensiveSystem> countermeasures, int craftProductionYear, String name, double health,
                            Side side) {
        this.cClass = cClass;
        this.cSubclass = cSubclass;
        this.weapons = weapons;
        this.countermeasures = countermeasures;
        this.craftProductionYear = craftProductionYear;
        this.name = name;
        this.health = health;
        this.side = side;
    }

    public static class Builder {
        private CClass cClass;
        private CSubclass cSubclass;
        private Map<CClass, List<IWeaponSystem>> weapons;
        private List<IDefensiveSystem> countermeasures;
        private int craftProductionYear;
        private String name;
        private double health;
        private Side side;

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

        public Builder setWeapons(Map<CClass, List<IWeaponSystem>> weapons) {
            this.weapons = weapons;
            return this;
        }

        public Builder setCraftProductionYear(int craftProductionYear) {
            this.craftProductionYear = craftProductionYear;
            return this;
        }

        public Builder setCountermeasures(List<IDefensiveSystem> countermeasures) {
            this.countermeasures = countermeasures;
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
            return new Vehicle(cClass, cSubclass, weapons, countermeasures, craftProductionYear, name,
                    health, side);
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Vehicle buildAerial() {
            return new Vehicle(cClass, cSubclass, weapons, countermeasures, craftProductionYear, name,
                    health, side);
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Vehicle buildNaval() {
            return new Vehicle(cClass, cSubclass, weapons, countermeasures, craftProductionYear, name,
                    health, side);
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Vehicle buildSpace() {
            return new Vehicle(cClass, cSubclass, weapons, countermeasures, craftProductionYear, name,
                    health, side);
        }
    }
    public Builder newBuilder() {
        return new Builder();
    }

}
