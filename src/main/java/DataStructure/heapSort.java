package main.java.DataStructure;


import java.util.Arrays;

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
        int a[]={21,14,56,44,3,23};
        a = heapSort(a,a.length);
        System.out.println( Arrays.toString(a));
    }

    /**
     * 将数组排序（）
     * @param a
     * @param n
     */
    public static int[] heapSort(int a[], int n){
        for (int i = n - 1; i >= 1; i--)
        {
//            swap(a[i], a[0]);
            int c = a[i];
            a[i] = a[0];
            a[0] = c;
            delHeap(a, 0, i);
        }
        return a;
    }


    /**
     * 新加入i结点 其父结点为(i - 1) / 2
     * @param str
     * @param i
     */
    public static void addHeap(int[] str,int i){
        int temp = str[i];
        int j = (i - 1) / 2; //父结点
        while (j >= 0 && i != 0)
        {
            if (str[j] <= temp)
                break;
            str[i] = str[j]; //把较大的子结点往下移动,替换它的子结点
            i = j;
            j = (i - 1) / 2;
        }
        str[i] = temp;
    }


    /**
     * 删除节点
     *  从i节点开始调整,n为节点总数 从0开始计算 i节点的子节点为 2*i+1, 2*i+2
     * @param a
     * @param i
     * @param n
     */
    public static void delHeap(int a[], int i, int n){
        int j, temp;
        temp = a[i];
        j = 2 * i + 1;
        while (j < n)
        {
            if (j + 1 < n && a[j + 1] < a[j]) //在左右孩子中找最小的
                j++;
            if (a[j] >= temp)
                break;
            a[i] = a[j]; //把较小的子结点往上移动,替换它的父结点
            i = j;
            j = 2 * i + 1;
        }
        a[i] = temp;
    }

}
