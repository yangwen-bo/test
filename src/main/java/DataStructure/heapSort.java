package DataStructure;

/**
 * @Author yangwen-bo
 * @Date 2019/9/4.
 * @Version 1.0
 *
 * 堆排序
 * 以小顶堆示例：每个根节点，都小于它所有的子节点，通常在程序中，用数组来对其进行存储
 * 根节点的坐标为（i-1）/2     从0开始取
 * 左节点的坐标为：2*i+1
 * 右节点的坐标为：2*i+2
 *
 */
public class heapSort {

    public static void main(String[] args) {
        short s1= 1;
        short s2=1+1;

    }

    /**
     * 将数组排序（这里是排了一个小顶堆）
     * @param str
     */
    public void heapSort(int[] str){
        for (int i = 0; i < str.length; i++) {
            int j = (i-1)/2;//左节点下标
            int temp = str[i];//当前根节点（最小）

        }

    }

    public void addHeap(){

    }

    public void delHeap(){

    }
}
