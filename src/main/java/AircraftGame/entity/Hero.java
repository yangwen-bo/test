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

    @Override
    public void step() {
        //两张图片切换 10毫秒走一次 每100毫秒切换一次图片 0 和 1 切换
        image=images[index++/10%images.length];
    }

    //英雄机发射子弹 单倍 双倍
    public Bullet[] shoot(){
        int xStep=this.width/4; //四分之一英雄机的宽，用于计算子弹的位置
        int yStep=20;//y坐标在英雄机上面20  控制-20
        if(doubleFire>0){//双倍
            Bullet[] bs=new Bullet[2];
            bs[0]=new Bullet( this.x+1*xStep,this.y-yStep );//双倍火力两个对象
            bs[1]=new Bullet( this.x+3*xStep,this.y-yStep );//
            //打蜜蜂奖励双倍火力给40分 但每发射一次双倍火力 -2
            doubleFire-=2;
            return bs;
        }else{//单倍
            Bullet[] bs=new Bullet[1];
            bs[0]=new Bullet( this.x+2*xStep,this.y-yStep );//单倍
            return bs;
        }
    }

    //英雄机移动方法 x,y鼠标位置 鼠标在英雄机的中心点 根据鼠标位置计算英雄机的位置
    public void moveTo(int x,int y){
        this.x=x-this.width/2;
        this.y=y-this.height/2;

    }

    //判断是否越界
    @Override
    public boolean outOfBounds() {
        return false;//英雄机永不越界
    }

    //两种可能声明加1 或者火力值增加
    public void addLife(){
        life++;
    }

    public void addDoubleFire(){
        doubleFire+=40;
    }

    public int getLife(){
        return this.life;
    }

    public void subtractLife(){
        life--;
    }

    public  void clearDoubleFire(){
        doubleFire=0;
    }

    //英雄机撞敌人 用鼠标的坐标来判断
    public boolean hit(FlyingObject obj){
        int x1=obj.x-this.width/2;
        int x2=obj.x+obj.width+this.width/2;
        int y1=obj.y-this.height/2;
        int y2=obj.y+obj.height+this.height/2;
        int x=this.x+this.width/2;
        int y=this.y+this.height/2;
        return x>x1 && x<x2 && y>y1 && y<y2;
    }
}
