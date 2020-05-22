package com.NoU.Systems;

import com.NoU.Enum.Age;
import com.NoU.Enum.GuidanceType;
import com.NoU.Enum.Theatre;
import com.NoU.Vertex2D;

import java.util.Set;

/**
 * @author Toonu
 */
public class Missile extends Weapon {
    private final Vertex2D position;
    private final GuidanceType guidanceType;

    public Missile(double damage, double minRange, double maxRange, Set<Theatre> targets, String name, Age age,
                   Vertex2D position, GuidanceType guidanceType) {
        super(damage, minRange, maxRange, targets, name, age);
        this.position = position;
        this.guidanceType = guidanceType;
    }

    public Vertex2D getPosition() {
        return position;
    }

    public GuidanceType getGuidanceType() {
        return guidanceType;
    }
}
