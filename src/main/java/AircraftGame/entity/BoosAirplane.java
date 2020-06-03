package main.java.AircraftGame.entity;

import AircraftGame.interF.Enemy;

/**
 * Created by lenovo on 2019/8/1.
 */
public class BoosAirplane extends FlyingObject implements Enemy {

    @Override
    public int getScore() {
        return 10;//boss机 10分
    }

    @Override
    public void step() {

    }

    @Override
    public boolean outOfBounds() {
        return false;
    }
}
