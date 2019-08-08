package AircraftGame.entity;

import AircraftGame.interF.Award;

import java.util.Random;

/**
 * Created by lenovo on 2019/7/31.
 * @author yangwen-bo
 *
 * 蜜蜂
 */
public class Bee extends FlyingObject implements Award {
   //小蜜蜂是x和y都会移动的
    private int xSpeed = 1;     //x坐标走步步数
    private int ySpeed = 2;     //y坐标走步步数
    private int awardType;      //奖励类型 随机产生

    public Bee() {
        image = ShootGame.bee;
        width = image.getWidth();
        height = image.getHeight();
        Random rand = new Random();
        x = rand.nextInt(ShootGame.WIDTH - this.width + 1);
        y = -this.height; // y:蜜蜂初始y位置
        awardType=rand.nextInt(2);//0或1 0代表火力 1表示生命
    }

    @Override
    public int getType() {
        return awardType;
    }

    @Override
    public void step() {
        //小蜜蜂x和y都会移动 而且触碰到边缘会改变方向继续向下
        x+=xSpeed;
        y+=ySpeed;
        if(x>=ShootGame.WIDTH-this.width){//x向左走
            xSpeed=-1;
        }if(x<=0){//x<=0时 x向右走
            xSpeed=1;
        }
    }

    //判断是否越界
    @Override
    public boolean outOfBounds() {
        return this.y>=ShootGame.HEIGHT;
    }
}
