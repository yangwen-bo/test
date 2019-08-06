package AircraftGame.entity;

import AircraftGame.interF.Award;

/**
 * Created by lenovo on 2019/8/1.
 * @author yangwen-bo
 *
 * 大黄蜂
 */
public class BigYellowBee extends FlyingObject implements Award {
    @Override
    public int getType() {
        return 0;
    }
}
