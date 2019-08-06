import com.sun.org.apache.xpath.internal.SourceTree;
import sun.applet.Main;

import java.util.Scanner;

/**
 * Created by lenovo on 2019/7/30.
 * @author yagnwen-bo
 *
 * 数据类型练习
 */
public class DataTypeDemo {
    public static void main(String[] args) {
        //输入
        Scanner input=new Scanner(System.in);
        int a=input.nextInt(); //输入整型

        //闰年判断
        //(year%4==0 year%100!=0) || year%400==0

        //数组的遍历
        int[] arr={1,2,3,4};
        System.out.println(arr.length);
        System.out.println(arr[0]);


    }
}
