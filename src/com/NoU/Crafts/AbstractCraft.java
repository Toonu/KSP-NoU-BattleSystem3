package com.NoU.Crafts;

import com.NoU.Buildable;
import com.NoU.Systems.ElectronicSystem;
import com.NoU.Systems.WeaponSystem;
import com.NoU.Utils.Side;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @author Toonu
 */
public abstract class AbstractCraft implements Craft{
    private CClass cClass;
    private CSubclass CSubclass;
    private Map<CClass, List<WeaponSystem>> weapons;
    private List<ElectronicSystem> countermeasures;
    private int craftProductionYear;
    private String name;
    private double health;
    private Side side;

    private double distanceFromMiddle = 100;
    private boolean isWithdrawing = false;
    private int craftTurn;

    //        self.has_radar = has_radar(unit_type) - put it as new subclass with radar methods
    //        self.oversaturated = [] - put it to ciws object

    private AbstractCraft(CClass cClass, CSubclass cSubclass, Map<CClass, List<WeaponSystem>> weapons,
                  List<ElectronicSystem> countermeasures, int craftProductionYear, String name, double health,
                  Side side) {
        this.cClass = cClass;
        this.CSubclass = cSubclass;
        this.weapons = weapons;
        this.countermeasures = countermeasures;
        this.craftProductionYear = craftProductionYear;
        this.name = name;
        this.health = health;
        this.side = side;
    }

    public class Builder implements Buildable<Craft> {
        private CClass cClass;
        private CSubclass CSubclass;
        private Map<CClass, List<WeaponSystem>> weapons;
        private List<ElectronicSystem> countermeasures;
        private int craftProductionYear;
        private String name;
        private double health;
        private Side side;

        public Builder setcClass(CClass cClass) {
            this.cClass = cClass;
            return this;
        }

        public Builder setCSubclass(com.NoU.Crafts.CSubclass CSubclass) {
            this.CSubclass = CSubclass;
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

        public Builder setWeapons(Map<CClass, List<WeaponSystem>> weapons) {
            this.weapons = weapons;
            return this;
        }

        public Builder setCraftProductionYear(int craftProductionYear) {
            this.craftProductionYear = craftProductionYear;
            return this;
        }

        public Builder setCountermeasures(List<ElectronicSystem> countermeasures) {
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
         * Build an instance of {@link Craft}
         *
         * @return instance of {@link Craft}
         */
        @Override
        public Craft build() {
            return new GroundCraft();
        }
    }

    public Builder newBuilder() {
        return new Builder();
    }
}
