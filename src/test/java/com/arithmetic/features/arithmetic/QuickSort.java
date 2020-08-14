package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 * 快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下:
 *  从数列中挑出一个元素，称为 “基准”（pivot）；
 *  重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
 *  递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
 *
 *  快速排序是现将大数组基本有序，然后通过基准点再递归的将小数组分区排序（通过基准点分为小数组并排序）
 *  归并排序是通过将大数组分为2个小数组，一直分到最小的单个元素，然后在排序小数组，最后排序最大的两个小数组达到整体有序
 * @author alan.chen
 * @date 2020/4/28 10:53 PM
 */
public class QuickSort {

    private static int[] array = {3, 45, 58, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    @Test
    public void quickSort() {

        int left = 0;
        int right = array.length - 1;
        sort(array,left, right);

        Arrays.stream(array).forEach(System.out::println);
    }

    private void sort(int[] array, int left, int right) {
        if(left >= right) {
            return;
        }
        // 返回基准点
        int pivot = partition(array, left, right);
        sort(array, left, pivot - 1);
        sort(array, pivot + 1, right);
    }

    private int partition(int[] array, int left, int right) {
        // 获取当前分区的基准点
        int key = array[left];
        // 将大于基准的放在右边，小于基准的放在左边

        while (left < right) {
            // 基准点在左边，先从右边开始（因为左边第一位已经被记录下来作为基准了）
            while (right > left && array[right] >= key) {
                --right;
            }
            if(right > left) {
                // 交换
                array[left] = array[right];
            }

            // 一次右边换到左边之后，开始从左边开始比较
            while(left < right && array[left] <= key) {
                ++left;
            }
            if(left < right) {
                // 交换
                array[right] = array[left];
            }
        }
        // 当小于基准的都在左边了，大于基准的都在右边了，将基准放在左右指针重合的地方，现在的left >= right, 并返回该基准点，继续递归分区
        array[left] = key;
        return left;
    }


    @Test
    public void test() {
        quickSort2(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }

    private void quickSort2(int[] array, int L, int R) {
        if(array == null || array.length < 2) {
            return;
        }
        if(L >= R) {
            return;
        }
        swap(array, L + (int)(Math.random()*(R-L+1)), R);
        int[] p = partition2(array, L, R);
        quickSort2(array, L, p[0] - 1);
        quickSort2(array, p[1] + 1, R);
    }

    private int[] partition2(int[] array, int l, int r) {
        int less = l - 1;
        int more = r;
        while(l < more) {
            if(array[l] > array[r]) {
                swap(array, l, --more);
            } else if(array[l] < array[r]) {
                swap(array, l++, ++less);
            } else {
                l++;
            }
        }
        swap(array, more, r);

        return new int[]{less+1, more};
    }

    private void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }


    @Test
    public void test3() {
        quickSort3(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }

    public void quickSort5(int[] array, int left, int right) {
        if(left > right) {
            return;
        }
        int[] p = partition5(array, left, right);
        quickSort5(array, left, p[0] - 1);
        quickSort5(array, p[1] + 1, right);
    }

    private int[] partition5(int[] array, int left, int right) {
        int less = left - 1;
        int more = right;

        while(left < more) {
            if(array[left] > array[right]) {
                swap(array, left, --more);
            } else if(array[left] < array[right]) {
                swap(array, left++, ++less);
            } else {
                left++;
            }
        }
        swap(array, right, more);
        return new int[]{less + 1, more};
    }


    private void quickSort3(int[] array, int left, int right) {
        if(left > right) {
            return;
        }
        int[] p = partition3(array, left, right);
        quickSort3(array, left, p[0] - 1);
        quickSort3(array, p[1] + 1, right);
    }

    private int[] partition3(int[] array, int left, int right) {
        int less = left - 1;
        int more = right;

        while(left < more) {
            if(array[left] > array[right]) {
                swap(array, left, --more);
            } else if(array[left] < array[right]) {
                swap(array, left++, ++less);
            } else {
                left++;
            }
        }
        swap(array, more, right);
        return new int[]{less + 1, more};
    }

}
