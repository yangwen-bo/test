package AircraftGame.entity;

import java.awt.image.BufferedImage;

/**
 * Created by lenovo on 2019/7/31.
 * @author yangwen-bo
 *
 * 英雄机
 */
public class Hero extends FlyingObject {
    //没有固定步数，随鼠标移动
    private int life;                 //命
    private int doubleFire;           //火力值
    //尾气 图片的切换
    private BufferedImage[] images;   //图片数组
    private int index;                //协助图片切换

    public Hero() {
        image = ShootGame.hero0;
        width = image.getWidth();
        height = image.getHeight();
        //初始化时位置是固定的
        this.x=150;
        this.y=400;
        life=3;//默认3条命
        doubleFire=0;//火力默认为0，单倍火力
        images=new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
        index=0;//图片切换 协助切换


    }
}
