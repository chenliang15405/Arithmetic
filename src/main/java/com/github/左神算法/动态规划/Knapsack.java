package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/24 22:28
 */
public class Knapsack {

    /**
     * 背包问题
     *   1. w数组表示货物重量、v数组表示货物价值，bag 表示背包的容量
     *
     *   计算背包可以装的最大价值
     */

    @Test
    public void test() {
        int[] weight = {3,2,4,7,3,1};
        int[] values = {5,6,3,19,12,4};
        int bag = 15;
        System.out.println(way1(weight, values, bag));
        System.out.println(dp(weight, values, bag));

    }

    /**
     * 暴力递归
     */
    public int way1(int[] w, int[] v, int bag) {
        if (w.length <= 0 || v.length <= 0 || bag <= 0) {
            return 0;
        }
        return process1(w, v, bag, 0);
    }

    // 从index...length 可以获取的最大的价值
    public int process1(int[] w, int[] v, int bag, int index) {
        // 判断上一次的选择是否为有效的选择，就是上一次选择之后，背包是否超过容量，如果已经超了，那么上一次就不能选择
        if (bag < 0) {
            return -1;
        }
        // 如果到了最大的位置，那么当前就不能继续了，以及超过数据了
        if (index == w.length) {
            return 0;
        }
        int p1 = process1(w, v, bag, index + 1);
        int p2 = 0;
        int next = process1(w, v, bag - w[index], index + 1);
        if (next != -1) {
            // 如果当前的选择不会让背包的容量小于0，证明当前的选择是有效的，可以将当前的选择加上
            p2 = v[index] + next;
        }
        return Math.max(p1, p2);
    }


    /**
     * 动态规划
     *
     */
    public int dp(int[] w, int[] v, int bag) {
        if (w.length <= 0 || v.length <= 0 || bag <= 0) {
            return 0;
        }
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j < bag + 1; j++) {
                int p1 = dp[i+1][j];
                int p2 = 0;
                if(j - w[i] >= 0) {
                    p2 = v[i] + dp[i+1][j-w[i]];
                }
                dp[i][j] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }


}
