package main.java.AircraftGame.interF;

/**
 * Created by lenovo on 2019/8/1.
 * @author yangwen-bo
 *
 * 小蜜蜂大黄蜂 奖励
 */
public interface Award {
    public int DOUBLE_FIRE=0;   //火力值
    public int LIFE=1;          //生命
    public int getType();       //获取奖励类型返回0为火力 返回1的是命
}
