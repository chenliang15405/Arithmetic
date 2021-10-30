package com.github.左神算法.数组相关;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/13 19:48
 */
public class ReversePair {

    /**
     * 逆序对
     *
     *   给定一个数组，如果数组中两个数 （num1, num2）中num1>num2，那么就称为逆序对，那么一共有多少个逆序对
     *
     *  归并排序相关问题
     */

    @Test
    public void test() {
        int[] arr = {6,1,2,3,7,5,9};

        System.out.println(getNum(arr));
    }


    /**
     * 分治的思想
     *   因为左边的数需要比右边的数大，才算是逆序对，并且为了加速计算逆序对，可以对数组进行排序的方式进行计算
     *
     */
    public int getNum(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    /**
     * 归并排序过程
     *
     */
    private int process(int[] arr, int L, int R) {
        if(L == R) {
            return 0;
        }
        int mid = L + (R - L) / 2;
        int left = process(arr, L, mid);
        int right = process(arr, mid + 1, R);
        int merge = merge(arr, L, mid , R);

        // 左边数组中的逆序对 + 右边数组的逆序对 + 合并过程中产生的逆序对
        return left + right + merge;
    }

    private int merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int p1 = M; // 从最后一位开始向前进行判断，因为需要统计每个的逆序对
        int p2 = R;
        int pair = 0;
        int index = help.length - 1;

        while (p1 >= L && p2 > M) {
            // 计算p1是否大于p2，因为只有当大于的时候，才计算逆序对，等于的时候不能计算，需要将p2填充到数组中
            pair += arr[p1] > arr[p2] ? (p2 - M) : 0;
            help[index--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
        }
        while (p1 >= L) {
            help[index--] = arr[p1--];
        }
        while (p2 > M) {
            help[index--] = arr[p2--];
        }

        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }

        return pair;
    }


}
