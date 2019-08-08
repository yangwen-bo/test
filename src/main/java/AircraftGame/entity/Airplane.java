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

    @Override
    public void step() {
        //敌机x不变，y向下移动
        y+=speed;//y+表示向下
    }

    //判断是否越界 敌机只可能y越界，因为x给限制了范围。出屏幕则越界
    @Override
    public boolean outOfBounds() {
        return this.y>=ShootGame.HEIGHT;
    }
}
