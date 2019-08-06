package AircraftGame.entity;

import AircraftGame.interF.Enemy;

import java.util.Random;

/**
 * Created by lenovo on 2019/7/31.
 * @author yangwen-bo
 *
 * 敌人
 */
public class Airplane extends FlyingObject implements Enemy {
    private int speed = 2;// 敌机走步的步数 往下走x不变 y变化 数值越大走的越快

    public Airplane() {
        image = ShootGame.airplane;
        width = image.getWidth();
        height = image.getHeight();
        Random rand = new Random();
        x = rand.nextInt(ShootGame.WIDTH - this.width + 1);
        y = -this.height; // y:负的敌机的高 初始位置
    }

    @Override
    public int getScore() {
        return 5;//普通敌机 5分
    }
}
