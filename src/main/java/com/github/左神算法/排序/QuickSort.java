package com.github.左神算法.排序;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/6/13 17:09
 */
public class QuickSort {

    /**
     * 快速排序
     *
     *  快排和归并排序的思想是相反的
     *    快排的思想：先整体基础排序，将数组划分为 小于、等于、大于区域，然后对小于、大于区域继续划分，然后排序，等下最小的区域排好序，那么整体数组就有序
     *    归并排序的思想：先划分数组，将数组对半划分，知道划分到最小的区域，然后对最小的区域进行排序，等到左右的区域都排好序，然后对最大的两个左右区域进行合并，合并完成就对整体排好序
     *
     *  1. 递归版本
     *  2. 非递归版本
     *
     */

    @Test
    public void test() {
        int[] arr = {9,1,3,8,2,5,6,1,2,4};
        quickSort(arr);

        int[] arr2 = Arrays.copyOf(arr, arr.length);
        quickSort2(arr2);

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arr2));
    }


    public void quickSort2(int[] arr) {
        process2(arr, 0, arr.length - 1);
    }

    private void process2(int[] arr, int l, int r) {
        if(l >= r) {
            return;
        }
        int[] pivot = patition2(arr, l, r);
        process2(arr, pivot[0] + 1, r);
        process2(arr, l, pivot[1] - 1);
    }

    private int[] patition2(int[] arr, int l, int r) {
        int less = l - 1;
        int more = r; // 以最后一位作为基准就从r开始，如果是随机数为基准，则从r+1开始
        int cur = l;
        while(cur < more) {
            if(arr[cur] < arr[r]) {
                swap(arr, ++less, cur++);
            } else if(arr[cur] > arr[r]) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        swap(arr, more, r);
        return new int[]{less + 1, more};
    }


    /**
     * 递归 快速排序
     *
     */
    public void quickSort(int[] arr) {
        if(arr == null || arr.length <= 0) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private void process(int[] arr, int L, int R) {
        if(L >= R) {
            return;
        }
        // 先patition，拿到划分区域
        int[] p = patition(arr, L, R);
        // 对接下来的数组继续划分
        process(arr, L, p[0] - 1);
        process(arr, p[1] + 1, R);
    }

    /**
     * patition的过程，将数组划分为小于、等于、大于
     *
     */
    private int[] patition(int[] arr, int L, int R) {
        int left = L - 1;
        int right = R;
        int index = L;
        while (index < right) {
            if(arr[index] > arr[R]) {
                swap(arr, index, --right);
            } else if(arr[index] < arr[R]) {
                swap(arr, ++left, index++);
            } else {
                index++;
            }
        }
        // 将最后一位数组和大于范围的指向的数字进行交换
        swap(arr, right, R);
        // 返回大于范围的区域指向
        return new int[]{left + 1, right};
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
