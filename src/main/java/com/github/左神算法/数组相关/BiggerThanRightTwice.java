package com.github.左神算法.数组相关;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/13 19:58
 */
public class BiggerThanRightTwice {

    /**
     * 给定一个数组，计算每个数的右边有多少个数的2倍，依然小于当前数。一共有多少个数
     *
     * 归并排序相关问题
     *
     */

    @Test
    public void test() {
        int[] arr = {6,1,2,3,7,5,9};

        System.out.println(getNum(arr));
    }


    public int getNum(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    private int process(int[] arr, int L, int R) {
        if(L == R) {
            return 0;
        }
        int mid = L + (R - L) / 2;
        // 总的数量等于左边数组内部的个数 + 右边数组内部的个数 + 总的两个数组之间的个数（因为右边数组内部都已经计算过了，所以这里只需要计算右边数组中有多少个数符合小于左边数组中的数）
        return process(arr, L, mid) + process(arr, mid + 1, R) + merge(arr, L, mid, R);
    }

    /**
     * 因为计算当前数右边的比当前数大的数，所以只需要在排序的时候计算当前数右边的数*2是否小于当前数即可，并且在局部排序的时候求一次，然后再
     * 和其他数组比较的时候求一次，那么会将所有的数比较完，因为跨数组比较是有序的，所以可以快速计算到有多少个数符合要求
     *
     */
    private int merge(int[] arr, int L, int M, int R) {
        // 先对两个部分的数据计算有多少个数满足 乘以2之后还是小于当前数
        int windowsR = M + 1;
        int count = 0;
        // 遍历左边的数组，并计算每个数数
        for (int i = L; i <= M; i++) {
            // 因为是有序的，所以前面的数大于n个右边数组的数的2倍，那么下一个数一定也至少有n个符合要求，所以只需要将指针一直向右移动，减少重复计算
            while (windowsR <= R && arr[i] > arr[windowsR] * 2) {
                windowsR++;
            }
            // 计算当前符合要求的个数
            count += windowsR - M - 1; // 因为当前的windowR这个数不符合要求，所以需要额外-1
        }

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
        return count;
    }


}
