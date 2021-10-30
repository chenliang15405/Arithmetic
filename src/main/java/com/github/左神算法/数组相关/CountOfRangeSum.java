package com.github.左神算法.数组相关;

/**
 * @author tangsong
 * @date 2021/6/13 23:27
 */
public class CountOfRangeSum {

    /**
     * 给定义个数组arr，两个整数lower和upper
     *
     * 返回arr中有多少个子数组的累加和在[lower,upper]的范围上
     *
     * 归并排序相关问题
     *
     */

    /**
     * 1。计算每个子数组的前缀和，sum[i]表示0~i的累加和
     * 2. 如果要计算原数组在[lower,upper]的范围上，当数组的sum和X，就可以转化为当前数组之前的前缀数组中是否有[x-upper, x-lower]的范围上
     * 3. 归并排序，利用归并排序的有序性，保证计算过程中不回退，时间复杂度为O(N)
     *
     *
     */
    public int countOfRangeSum(int[] arr, int lower, int upper) {
        // 先计算每个子数组的前缀累加和
        long[] sum = new long[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum[i] = sum[i-1] + arr[i]; // 计算每个位置的前缀累加和
        }
        // 使用sum数组就可以进行求解，不需要arr数组，因为计算原数组的累加和在[lower、upper]的范围上，那么子数组的累加和需要在[x-upper，x-lower]范围上
        return process(sum, 0, arr.length - 1, lower, upper);
    }


    private int process(long[] sum, int L, int R, int lower, int upper) {
        // 如果只有一个数字，就表示0~x 这个数字上的前缀和，就看这个单独的子数组是否满足需求
        if(L == R) {
            // 只看当前这个子数组是否满足需求
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }
        int M = L + ((R - L) >> 1);
        return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper) + merge(sum, L, M, R, lower, upper);
    }

    private int merge(long[] sum, int L, int M, int R, int lower, int upper) {
        // 计算当前的前缀和
        int res = 0;
        int windowL = L;
        int windowR = L;
        // 对每个右边的数计算其前缀和是否满足需求，并且左边数组的指针范围是否不回退的，因为数组是有序的，前面的符合要求，因为是递增的，所以只会向右走，
        // 当前位置的前缀和的子数组的[x-upper, x-lower]的范围也是递增的，所以指针不回退，只能向右移动
        for (int i = M+1; i <= R; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            // 计算当前位置包含的子数组满足前缀和的[x-upper, x-lower]范围的子数组有哪些
            while (windowL <= M && sum[windowL] < min) {
                windowL++;
            }
            while (windowR <= M && sum[windowR] <= max) {
                windowR++;
            }
            // 当前位置的前缀和可以符合范围内的数组个数，就是子数组满足剩余范围要求的个数
            // 并且windowsL 和windowR是递增的，不回退，因为右边的数组顺序是递增的，已经排好序了
            res += windowR - windowL; // 这里不用+1了，因为上面计算的时候，已将windowR移动到了最大范围的下一位
        }
        long[] help = new long[sum.length];
        int p1 = L;
        int p2 = M + 1;
        int index = 0;

        while (p1 <= M && p2 <= R) {
            help[index++] = sum[p1] > sum[p2] ? sum[p2++] : sum[p1++];
        }
        while (p1 <= M) {
            help[index++] = sum[p1++];
        }
        while (p2 <= R) {
            help[index++] = sum[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            sum[L + i] = help[i];
        }
        return res;
    }


}
