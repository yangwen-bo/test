package main.java.AircraftGame.entity;

import java.awt.image.BufferedImage;

/**
 * Created by lenovo on 2019/7/31.
 * @author yangwen-bo
 *BufferedImage 存放图片的类
 * 飞行物 做父类
 */
public abstract class FlyingObject {
    protected BufferedImage image;
    protected int  width;
    protected int height;
    protected int x;
    protected int y;

    //定义声明一个走步方法没有实现，所以是抽象方法抽象类
    public abstract void step();

    //检查是否出界 返回true表示越界
    public abstract boolean outOfBounds();

    //判断敌人是否被子弹射击上
    public boolean shootBy(Bullet bullet){
        int x1=this.x;
        int x2=this.x+this.width;
        int y1=this.y;
        int y2=this.y+this.height;
        int x=bullet.x;
        int y=bullet.y;

        return x>x1 && x<x2 && y>y1 && y<y2;
    }

}
