package com.arithmetic.features.arithmetic;

import org.junit.Test;

import javax.lang.model.type.ArrayType;
import java.util.Arrays;

/**
 * @author alan.chen
 * @date 2020/5/8 5:13 PM
 */
public class ClassicalQuickSort {

    private static int[] array = {3, 45, 58, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    /**
     * 经典快排，经典快排是将固定的一位数作为固定值，然后将大于的放在左边，小于的放在右边，相等的在中间
     * 然后再次根据相等的数据边界，进行排序，将大于的放在左边，小于的放在右边，相等的在中间，达到最终的有序
     *
     * 经典快排的问题：如果该数组是有序的，则每次排序一位数，时间复杂度O(N^2)
     *
     */
    @Test
    public void classicQuickSortTest() {
        quickSort(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }

    private void quickSort(int[] array, int L, int R) {
        if(L < R) {
            int[] p = partition(array, L, R);
            quickSort(array, L, p[0] - 1);
            quickSort(array, p[1] + 1, R);
        }
    }

    // 返回的数组是等于第一个基准数的区域数组的开始边界和结束边界的索引
    private int[] partition(int[] array, int L, int R) {
        int less = L - 1;
        int more = R; // 将最右边的数作为基准，所以在比较完成的最后需要将less和L交换
        while(L < more) {
            if(array[L] > array[R]) {
                swap(array, L, --more);
            } else if(array[L] < array[R]) {
                swap(array, L++,  ++less); // 较L和小于R的数交换，less记录的总是小于R的数，如果没有小于R的数，就和自身交换
            } else {
                L++;
            }
        }
        // 因为最右边的一个数作为基准，所以在进行比较交换的时候，没有计算在内，在最后比较完成，因为more记录的
        // 总是小于R的第一位数，所以和R交换
        swap(array, more, R);
        return new int[]{less + 1, more};
    }


    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }


    @Test
    public void quickSort() {
        sort3(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }

    private void sort3(int[] array, int L, int R) {
        if(L > R) {
            return;
        }
        swap(array, R, L + (int)Math.random()*(R - L + 1));
        int[] p = partition3(array, L, R);
        sort3(array, L, p[0] - 1);
        sort3(array, p[1] + 1, R);
    }

    private int[] partition3(int[] array, int l, int r) {
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
        return new int[]{less + 1, more};
    }


    @Test
    public void test5() {
        quickSort5(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }

    private void quickSort5(int[] array, int left, int right) {
        if(left > right) {
            return;
        }
        swap(array, right, left + (int)(Math.random()*(right - left + 1)));
        int[] p = partition5(array, left, right);
        quickSort5(array, left, p[0] - 1);
        quickSort5(array, p[1] + 1, right);
    }

    private int[] partition5(int[] array, int left, int right) {
        int less = left - 1;
        int more = right;

        while(left < more) {
            if(array[left] > array[right]) {
                swap2(array, left, --more);
            } else if(array[left] < array[right]) {
                swap2(array, ++less, left++);
            } else {
                left++;
            }
        }
        swap(array, more, right);
        return new int[]{less + 1, more};
    }

    private void swap2(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
