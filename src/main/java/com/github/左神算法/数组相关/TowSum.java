package com.github.左神算法.数组相关;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/6/13 19:11
 */
public class TowSum {

    /**
     * 小和问题
     *
     *   数组中，每个数左边比当前数小的数累加起来就是小和
     *
     *
     *  归并排序相关问题
     *
     */

    @Test
    public void test() {
        int[] arr = {6,3,2,1,6,7};
        System.out.println(smallSum(arr));

    }


    /**
     * 小和问题
     *    借助归并排序实现
     *
     */
    public int smallSum(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    private int process(int[] arr, int L, int R) {
        if(L == R) {
            return 0;
        }
        int mid = L + (R - L) / 2;

        // 左边数组的小和
        int left = process(arr, L, mid);
        // 右边数组的小和
        int right = process(arr, mid + 1, R);
        // 左边和右边在merge过程中产生的小和
        int merge = merge(arr, L, mid, R);

        return left + right + merge;
    }

    private int merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int sum = 0;
        int p1 = L;
        int p2 = M + 1;
        int index = 0; // 临时数组填充索引变量

        while (p1 <= M && p2 <= R) {
            // 对数组进行排序的同时计算小和
            // 只有当左边的数符合要求的时候，才计算小和，因为左边的数在原数组中原本就应该计算小和（小和就是计算当前数左边比它小的数之和），
            // 右边的数在本次（当前数组）的归并过程中，不计算小和，右边的数只有在跨数组的时候才计算小和，而左边的数符合要求则计算一次小和，在跨数组的时候还需要再次计算小和

            // 这里不是计算每个数左边一共有多少个数比它小之和，而是每次都计算一个数右边有多少个比它大的数，那么当前数就应该在计算和的时候加多少次
            // 所以只有在归并的过程中，左边数小的时候才计算小和
            sum += arr[p1] < arr[p2] ? (R - p2 + 1) * arr[p1] : 0;
            help[index++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 归并排序原本流程
        while (p1 <= M) {
            help[index++] = arr[p1++];
        }
        while (p2 <= R) {
            help[index++] = arr[p2++];
        }
        // 将排序完成的数copy到原数组
        for (int i = 0; i < index; i++) {
            arr[L + i] = help[i];
        }

        return sum;
    }


}
