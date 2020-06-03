package main.java.DataStructure;

/**
 * @Author yangwen-bo
 * @Date 2019/9/3.
 * @Version 1.0
 *
 * 插入排序：又称直接插入排序（staright insertion sort），其是将未排序元素一个个插入到已排序列表中。
 * 对于未排序元素，在已排序序列中从后向前扫描，找到相应位置把它插进去；在从后向前扫描过程中，
 * 需要反复把已排序元素逐步向后挪，为新元素提供插入空间。
 *
 * 时间复杂度：最坏O(n2)、平均O(n2)、最好O(n)
 * 空间复杂度：O(n1)
 * 稳定性：稳定
 */
public class insertionSort {
    public static void main(String[] args) {
        int  arr[]={14,6,23,9,14,56,2,89,3};
        arr = insertSort(arr);
        System.out.println(java.util.Arrays.toString(arr));
    }

    private static int[] insertSort(int[] arr){
        int length = arr.length;
        // 从索引为1的位置开始遍历
        for (int i = 1; i < length; ++i)
        {
            int currentValue = arr[i];	// 保存当前值
            int preIndex = i - 1;	// 前一个索引值
            // 循环条件: 前一个索引值对应元素值大于当前值 && 前一个索引值大于等于0
            while (arr[preIndex] > currentValue && preIndex >= 0){
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
                if(preIndex<0){//防止下标越界
                    break;
                }
            }
            arr[preIndex + 1] = currentValue;
        }
        return arr;
    }

}
