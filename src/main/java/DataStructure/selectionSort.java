package DataStructure;

/**
 * @Author yangwen-bo
 * @Date 2019/9/3.
 * @Version 1.0
 *
 * 选择排序：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
 * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
 * 与冒泡排序的区别：在冒泡排序上做了优化，减少了交换次数，在首轮选择最小的数放在第一项，一轮之后第一项是有序的了，
 * 第二轮从第二项开始选择最小的数放在第二项，以此类推，直到整个数组完全有序。
 * 时间复杂度：O(n2)无论什么数据进去，选择都是O(n2)的时间复杂度，所以若要使用它，建议数据规模越小越好。
 * 空间复杂度：O(1)
 */
public class selectionSort {
    public static void main(String[] args) {
        int  arr[]={14,6,23,9,14,56,2,89,3};
        arr = selectSort(arr);
        System.out.println(java.util.Arrays.toString(arr));
    }

    private static int[] selectSort(int[] arr){
        int length = arr.length;
        // 外循环: length-1次，因为当length-1个元素排序好后，第length个元素位置不再变化
        for (int i = 0; i < length-1; ++i)
        {
            int minIndex = i;
            // 从i的位置，进行遍历，因为前i-1个元素已经排序好
            for (int j = i; j < length; ++j)
            {
                // 每次从未排序的数组中选出最小的值放入已排序的数组中，即从小到大排序
                if (arr [j] < arr[minIndex])
                {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

}
