package DataStructure;

/**
 * @Author yangwen-bo
 * @Date 2019/9/2.
 * @Version 1.0
 *
 * 冒泡排序  时间复杂度n^2(平均 最坏)  n（最好）  空间复杂度 1  稳定性 稳定
 *
 * 比较相邻两个数据，大的往后移（升序），每次都从头开始，共走n-1次
 */
public class bubbleSort {
    public static void main(String[] args) {
        int aa[]={5, 2, 5, 7, 1, -3, 99, 56};
        //使用for循环
        int bb[] = sortFor(aa);
        //使用while循环+for
        int cc[] = sortWhile(aa);
        System.out.println("for升序排序:");
        System.out.println(java.util.Arrays.toString(bb));
        System.out.println("while+for升序排序:");
        System.out.println(java.util.Arrays.toString(cc));

        String value="";
        value=String.format( "%-"+1000+"s","hhh" );//按格式替换，不足位置右补空位，1000表示总共位数
        System.out.println("value="+value);
        System.out.println("value.length"+value.length());

    }

    private static int[] sortFor(int aa[]){
        for (int i=0;i<aa.length-1;i++){
            // if (aa[j] < aa[j + 1])  从大到小排序（降序）
            //升序排序  每走一遍最后一个位置确定，下一遍循环就不对比这个数
            for(int j=0;j<aa.length-i-1;j++){
                if (aa[j] > aa[j + 1])
                {
                    int temp = aa[j + 1];
                    aa[j + 1] = aa[j];
                    aa[j] = temp;
                }
            }
        }
        return aa;
    }

    private static int[] sortWhile(int aa[]){
        boolean swapped = false;//用这个来控制最外层循环
        int length = aa.length;
        while (!swapped)
        {
            swapped = true;
            for (int i = 0; i < length - 1; ++i)
            {
                // 从大到小排序
//                if (aa[i] < aa[i + 1])
                //两两相邻元素比较大小，从小到大排序
                if (aa[i] > aa[i + 1])
                {
                    int temp = aa[i + 1];
                    aa[i + 1] = aa[i];
                    aa[i] = temp;
                }
                swapped = false;
            }
            length--;//用以控制内层循环，每次循环完一次，再从头开始第二次循环，直至length=1
        }
        return aa;
    }

}
