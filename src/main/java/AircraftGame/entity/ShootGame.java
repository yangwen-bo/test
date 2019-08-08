package AircraftGame.entity;

import AircraftGame.interF.Award;
import AircraftGame.interF.Enemy;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.Timer;

/**
 * Created by lenovo on 2019/8/1.
 * @author yangwen-bo
 *
 * 继承JPanel（窗口框） 出来窗口
 * 启动游戏类
 */
public class ShootGame extends JPanel{
//    private static final long serialVersionUID = 9158805858745581422L;
    public static final int WIDTH = 400; // 窗口的宽
    public static final int HEIGHT = 654; // 窗口的高
    // 静态资源
    public static BufferedImage background; // 背景图
    public static BufferedImage start; // 开始图
    public static BufferedImage pause; // 暂停图
    public static BufferedImage gameover; // 游戏结束图
    public static BufferedImage airplane; // 敌机图
    public static BufferedImage bee; // 蜜蜂图
    public static BufferedImage bullet; // 子弹图
    public static BufferedImage hero0; // 英雄机0图
    public static BufferedImage hero1; // 英雄机1图

    private Hero hero = new Hero(); // 英雄机
    private Bullet[] bullets = {}; // 子弹数组
    private FlyingObject[] flyings = {}; // 敌人数组 小蜜蜂+敌机

    //几种状态静态参数
    public static final int START = 0;//初始 启动
    public static final int RUNNING = 1;//运行
    public static final int PAUSE = 2;//暂停
    public static final int GAME_OVER = 3;//游戏结束
    private int state = START; // 当前状态

    // 静态块 初始化静态资源（图片）
    static {
        try {
            //用这种方式读取图片必须在一个路径下
//            background = ImageIO.read(ShootGame.class.getResource("background.png"));
//            start = ImageIO.read(ShootGame.class.getResource("start.png"));
//            pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
//            gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
//            airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
//            bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
//            bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
//            hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
//            hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));

            background = ImageIO.read( new File("D:\\IDEAWorkspace\\test\\src\\main\\java\\AircraftGame\\image\\background.png") );
            start = ImageIO.read(new File("D:\\IDEAWorkspace\\test\\src\\main\\java\\AircraftGame\\image\\start.png"));
            pause = ImageIO.read(new File("D:\\IDEAWorkspace\\test\\src\\main\\java\\AircraftGame\\image\\pause.png"));
            gameover = ImageIO.read(new File("D:\\IDEAWorkspace\\test\\src\\main\\java\\AircraftGame\\image\\gameover.png"));
            airplane = ImageIO.read(new File("D:\\IDEAWorkspace\\test\\src\\main\\java\\AircraftGame\\image\\airplane.png"));
            bee = ImageIO.read(new File("D:\\IDEAWorkspace\\test\\src\\main\\java\\AircraftGame\\image\\bee.png"));
            bullet = ImageIO.read(new File("D:\\IDEAWorkspace\\test\\src\\main\\java\\AircraftGame\\image\\bullet.png"));
            hero0 = ImageIO.read(new File("D:\\IDEAWorkspace\\test\\src\\main\\java\\AircraftGame\\image\\hero0.png"));
            hero1 = ImageIO.read(new File("D:\\IDEAWorkspace\\test\\src\\main\\java\\AircraftGame\\image\\hero1.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //下一个产生对象敌机，小蜜蜂
    public FlyingObject nextOne(){
        Random rand=new Random(  );
        int type=rand.nextInt(20);
        if(type==0){
            return new Bee();
        }else{
            return new Airplane();
        }
    }

    int flyEnteredIndex=0;

    //敌机和小蜜蜂入场的方法
    public void enterAction(){
        flyEnteredIndex++;
        if(flyEnteredIndex%40==0){//每400毫秒新生成一个敌人对象
            FlyingObject one=nextOne();
            flyings=Arrays.copyOf( flyings,flyings.length+1 );//给flyings原始数据不变基础上扩容+1
            flyings[flyings.length-1]=one;//将产生的敌人加到数组最后一个元素
        }
    }

    //飞行物走步方法10 毫秒走一次
    private void stepAction() {
        hero.step();//英雄机走一步 两张图片切换
        for(int i=0;i<flyings.length;i++){
            flyings[i].step();//敌人走一步
        }
        for(int i=0;i<bullets.length;i++){
            bullets[i].step();//子弹走一步
        }
    }

    int shootIndex=0;
    //英雄机发射子弹 控制子弹入场 在子弹类中控制单双倍火力以及产生
    //子弹入场
    private void ShootAction() {
        //创建子弹对象加入到bulleats对象数组中 但是每10毫米 速度依然太快，同理加一个控制
        shootIndex++;
        if (shootIndex % 30 == 0) {//每300毫秒走一次  shoot设计方法，有可能是单倍 有可能是双倍
            //
            Bullet[] bs=hero.shoot();//获取英雄机发射的子弹对象
            bullets=Arrays.copyOf( bullets,bullets.length+bs.length );//给子弹数组扩容
            //遍历bs 将其放入bullets数组中
            //将bs数组从0下标开始复制元素，到bullets数组中的第bullets.length-bs.length个下标位置开始复制，复制bs.length的长度的数据
            System.arraycopy( bs,0,bullets,bullets.length-bs.length,bs.length );

        }
    }

    //删除越界对象
    private void outOfBoundsAction() {
        int index=0;//做新建这个数组的下标
        FlyingObject[] flyingLives=new FlyingObject[flyings.length];//存放所有不越界的元素
        for (int i = 0; i <flyings.length ; i++) {
            if(!flyings[i].outOfBounds()){//所有不越界的元素放到这个数组中
                flyingLives[index]=flyings[i];
                index++;
            }
        }
        flyings=Arrays.copyOf( flyingLives,index );//将所有不越界的元素数组赋值给flyings数组 且给其长度

        int bulletsIndex=0;
        Bullet[] bulletsLives=new Bullet[bullets.length];
        for (int i = 0; i <bullets.length ; i++) {
            if(!bullets[i].outOfBounds()){
                bulletsLives[bulletsIndex]=bullets[i];
                bulletsIndex++;
            }
        }
        bullets=Arrays.copyOf( bulletsLives,bulletsIndex );
    }

    //子弹与敌人的碰撞
    public void bangAction() {
        for (int i = 0; i <bullets.length ; i++) {
            Bullet b=bullets[i];
            bang( b );
        }
    }

    //得分
    int score=0;

    //将遍历出来的单个子弹拿出来 和所有敌人碰撞
    public void bang(Bullet bullet){
        int index=-1;//存储被撞敌人下标
        for (int i = 0; i <flyings.length ; i++) {
            if(flyings[i].shootBy( bullet )){//判断是否被撞上l
                index=i;
                break;
            }
        }
        if(index!=-1){//表示有撞上
            FlyingObject one=flyings[index];//获取被撞的敌人对象 还要判断是敌机还是小蜜蜂 小蜜蜂是命还是火力
            if(one instanceof Enemy){//敌机 得分
                Enemy e=(Enemy)one;
                score+=e.getScore();
            }
            if(one instanceof Award){//奖励 分得分 或 生命
                Award a=(Award)one;
                int type=a.getType();//获取奖励类型
                switch (type){
                    case Award.DOUBLE_FIRE:hero.addDoubleFire();
                        break;
                    case Award.LIFE:hero.addLife();
                        break;
                }
            }
            //交换 将被撞敌机与最后一个元素交换
            FlyingObject t=flyings[index];
            flyings[index]=flyings[flyings.length-1];
            flyings[flyings.length-1]=t;
            //去掉最后一个元素 即将被撞元素删掉
            flyings=Arrays.copyOf( flyings,flyings.length-1 );
        }
    }


    //检查游戏是否结束
    private void checkGameOverAction() {
        if(isGameOver()){
            state=GAME_OVER;//修改当前状态
        }
    }

    //判断是否结束 返回true 表示结束
    public boolean isGameOver(){
        //判断是否撞上了 生命-- 清火力
        for (int i = 0; i <flyings.length ; i++) {
            if(hero.hit( flyings[i] )){
                hero.subtractLife();
                hero.clearDoubleFire();
                //清除敌机
                FlyingObject t=flyings[i];
                flyings[i]=flyings[flyings.length-1];
                flyings[flyings.length-1]=t;
                flyings=Arrays.copyOf( flyings,flyings.length-1 );
            }
        }

        if(hero.getLife()<=0){
            return true;
        }
        return false;
    }

    //启动程序的执行
    public void action() {
        //声明监听器对象 创建
        MouseAdapter l =new MouseAdapter() {
            //重写鼠标移动事件 英雄机随着运动
            public void mouseMoved(MouseEvent e){
                //只有在运行状态才可以动
                if(state==RUNNING) {
                    int x = e.getX();//获取鼠标的x位置
                    int y = e.getY();//获取鼠标的y位置
                    //英雄机随着运动 调用英雄机移动方法
                    hero.moveTo( x, y );
                }
            }
            //写点击 开始事件
            public void mouseClicked(MouseEvent e){
                //根据当前状态变更
                switch (state){
                    case START:state=RUNNING;//启动状态 变运行
                    break;
                    case GAME_OVER:
                        score=0;//数据清零 变为初始状态
                        hero=new Hero();
                        flyings=new FlyingObject[0];
                        bullets=new Bullet[0];
                        state=START;//游戏结束时点击变为启动状态 清零
                    break;
                }
            }
            //鼠标移出面板事件
            public void mouseExited(MouseEvent e){
                if(state==RUNNING){
                    state=PAUSE;
                }
            }
            //鼠标移入面板事件
            public void mouseEntered(MouseEvent e){
                if(state==PAUSE){
                    state=RUNNING;
                }
            }
        };
        //将监听器添加到面板上
        this.addMouseListener( l );//鼠标操作时间
        this.addMouseMotionListener( l );//鼠标滑动时间

        Timer timer =new Timer(  );
        int intervel=10;//时间间隔 毫秒

        timer.schedule(new TimerTask(){
            public void run(){//定时执行的任务 每10毫秒执行的事
               //只有在运行状态才可以动
                if(state==RUNNING){
                    enterAction();//敌人小蜜蜂入场
                    stepAction();//飞行物走步方法
                    ShootAction();//子弹入场
                    outOfBoundsAction();//删除越界的对象
                    bangAction();//子弹与敌人 下蜜蜂的碰撞
                    checkGameOverAction();//检查游戏是否结束

                }
                repaint(  );//每次移动之后都要再次重新将对象重新显示 面板刷新
            }
        },intervel, intervel );

    }


    //重写paint方法 g:当做一个画笔
    //paint方法只能系统调用
    public void  paint(Graphics g){
        //画背景图 0,0,表示窗口最顶点
        g.drawImage( background,0,0,null );
        paintHero( g );//画英雄机
        paintFlyingObjects( g );//画敌人
        paintBullets( g );//画子弹
        paintScoreAndLife(g);//画分数和命数
        paintState(g);//画状态
    }

    //画状态
    private void paintState(Graphics g) {
        //判断当前状态 画不同的图片
        switch (state){
            case START:g.drawImage( start,0,0,null );
            break;
            case PAUSE:g.drawImage( pause,0,0,null );
            break;
            case GAME_OVER:g.drawImage( gameover,0,0,null );
            break;
        }
    }

    //画分数和命数
    private void paintScoreAndLife(Graphics g) {
        //给画笔以样式
        g.setColor( new Color( 255, 94, 31 ) );
        g.setFont( new Font( Font.SANS_SERIF,Font.BOLD,24 ) );
        g.drawString( "score:"+score,10,25 );//画字符串
        g.drawString( "life:"+hero.getLife(),10,50 );
    }

    //画英雄机对象
    public void paintHero(Graphics g){
        g.drawImage( hero.image,hero.x,hero.y,null );
    }
    //画敌人对象
    public void paintFlyingObjects(Graphics g){
        for (int i=0;i<flyings.length;i++){
            FlyingObject f=flyings[i];
            g.drawImage( f.image,f.x,f.y,null );
        }
    }
    //画子弹对象
    public void paintBullets(Graphics g){
        for (int i=0;i<bullets.length;i++){
            g.drawImage( bullets[i].image,bullets[i].x,bullets[i].y,null );
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("飞机大战"); // 窗口对象
        ShootGame game = new ShootGame(); // 面板
        frame.add(game); // 将面板添加到窗口中

        frame.setSize(WIDTH, HEIGHT); // 设置窗口的宽和高
        frame.setAlwaysOnTop(true); // 设置一直在最上面
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭的操作：窗口关闭时退出程序
        frame.setLocationRelativeTo(null); // 设置窗口初始位置（居中)
        frame.setVisible(true); // 设置窗体可见 尽快调用paint（）方法（JPanel中有paint方法）重写paint方法，将图片画到面板上

        game.action(  );//启动程序执行，自己重写的
    }
}
