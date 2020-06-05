package AircraftGame.entity;

import java.util.Random;

/**
 * Created by lenovo on 2019/7/31.
 * @author yangwen-bo
 *
 * 子弹
 */
public class Bullet extends FlyingObject {
    private int speed = 3;  //子弹走步步数，只有y坐标在变 x不变 y变化

    public Bullet(int x,int y) {
        image = ShootGame.bullet;
        width = image.getWidth();
        height = image.getHeight();
        this.x=x;//根据英雄机的位置来决定
        this.y=y;
    }

    @Override
    public void step() {
        y-=speed;//子弹向上移动
    }

    //子弹判断是否越界
    @Override
    public boolean outOfBounds() {
        return this.y<=-this.height;
    }

}
