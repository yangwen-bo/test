package AircraftGame.entity;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

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
//
    ShootGame(){
        flyings= new FlyingObject[2];
        flyings[0]=new Airplane();
        flyings[1]=new Bee();
        bullets=new Bullet[1];
        bullets[0]=new Bullet( 100,200 );
    }

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

    //重写paint方法 g:当做一个画笔
    //paint方法只能系统调用
    public void  paint(Graphics g){
        //画背景图 0,0,表示窗口最顶点
        g.drawImage( background,0,0,null );
        paintHero( g );//画英雄机
        paintFlyingObjects( g );//画敌人
        paintBullets( g );//画子弹

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


    }
}
