package com.NoU;

import com.NoU.Crafts.Craft;

import java.util.SortedSet;

public class Application {
    public static int DEFAULT_YEAR;
    public int GLOBAL_TIME = 0;
    private SortedSet<Craft> leftSide;
    private SortedSet<Craft> rightSide;

    BattleSecond b = new BattleSecond();


    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
