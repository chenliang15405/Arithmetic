package com.github.左神算法.排序;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/6/13 16:14
 */
public class MergeSort {

    /**
     * 归并排序
     *
     * 1. 递归的将数组进行划分，让每一个小的区域有序，然后对整体的两个有序的小数组进行归并，对每一部分进行merge
     *
     */

    @Test
    public void test() {
        int[] arr = {9,1,3,8,2,5,6,1,2,4};
        mergeSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public void mergeSort(int[] arr) {
        process(arr, 0, arr.length - 1);
    }

    private void process(int[] arr, int L, int R) {
        if(L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid , R);
    }

    private void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int p1 = L;
        int p2 = M + 1;
        int index = 0;

        while (p1 <= M && p2 <= R) {
            help[index++] = arr[p1] > arr[p2] ? arr[p2++] : arr[p1++];
        }
        while (p1 <= M) {
            help[index++] = arr[p1++];
        }
        while (p2 <= R) {
            help[index++] = arr[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }


}
