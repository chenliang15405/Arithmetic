package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/7/4 14:50
 */
public class SplitSumClosed {

    /**
     * 给定一个正数数组arr，请把arr中所有数分成两个集合，尽量让两个集合的累加和接近
     * 返回：
     * 最接近的情况下，较小集合的累加和
     *
     * 从左到右的尝试模型
     *
     */
    @Test
    public void test() {
        int[] arr = {41, 25, 33, 7, 22, 8, 41, 40, 14, 28, 38}; // 148
        System.out.println(right(arr));
    }


    /**
     * 暴力递归
     *
     */
    public int right(int[] arr) {
        if(arr == null || arr.length <= 0) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }

        return process(arr, 0, sum / 2);
    }

    // 从index开始，返回数据之和小于等于sum的最大值
    private int process(int[] arr, int index, int sum) {
        if(index == arr.length) {
            return sum < 0 ? -1 : 0;
        }
        if(sum < 0) {
            return -1;
        }
        // 不要当前的数
        int p1 = process(arr, index + 1, sum);
        int p2 = 0;
        if(sum - arr[index] >= 0) {
            // 如果不等于-1，则表示要当前这个数是可行的，那么就加上当前这个数，并且继续向下递归
            p2 += arr[index] + process(arr, index + 1, sum - arr[index]);
        }

        return Math.max(p1, p2);
    }


}
