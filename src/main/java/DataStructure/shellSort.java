package main.java.DataStructure;

/**
 * @Author yangwen-bo
 * @Date 2019/9/4.
 * @Version 1.0
 *
 * 希尔排序：设待排序元素序列有n个元素，首先取一个整数increment（小于n）作为间隔将全部元素分为increment个子序列，
 * 所有距离为increment的元素放在同一个子序列中，在每一个子序列中分别实行直接插入。
 * 1.选择一个增量序列t1，t2，…，tk
 * 2.按增量序列个数k，对序列进行k 趟排序；
 * 3.每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。
 * 仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
 *
 * 希尔排序是直接插入排序的一种改进，减少了其复制的次数，速度要快很多。原因是，当n值很大时，
 * 数据项每一趟排序需要移动的个数很少，但数据项的距离很长；当n值减小时，每一趟需要移动的数据增多。
 *
 * 时间复杂度：平均：O(n^1.3)  最差：O(n2)  最好：O(n)
 * 空间复杂度：O(1)
 * 稳定性：不稳定
 *
 */
public class shellSort {
    public static void main(String[] args) {
        int  arr[]={14,6,23,9,14,56,2,89,3};
        arr = shellsort(arr);
        System.out.println(java.util.Arrays.toString(arr));
    }

    private static int[] shellsort(int[] list){
        int n = list.length;
        // 设置增量：以n/2为初始增量，然后逐渐减小增量（每次缩小为上次增量的一半）
        for (int gap = n / 2; gap > 0; gap /= 2){
            // 遍历当前趟，对每个子序列进行插入排序
            for (int i = gap; i < n; i++){
                int temp = list[i];
                int j = 0;
                // 遍历子序列
                for (j = i; j >= gap && list[j - gap]>temp; j -= gap)
                    list[j] = list[j - gap];

                list[j] = temp;
            }
        }
        return list;
    }
}
