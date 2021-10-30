package com.github.bstation;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/13 23:35
 */
public class Knapsack {

    /**
     * 背包问题
     *
     *   1. 给定两个一样大小的数组，一个数组是货物的质量，一个数组是货物的价值，给定一个背包，求背包中可以计算的最大价值
     *
     */

    @Test
    public void test() {
        int[] weight = {3,2,4,7};
        int[] values = {5,6,3,19};
        int bag = 11;

        System.out.println(getMaxValue(weight, values, bag));

        System.out.println(dp(weight, values, bag));

    }


    public int getMaxValue(int[] w, int[] v, int bag) {
        if(bag == 0) {
            return 0;
        }
        return process(w, v, 0, bag);
    }

    /**
     * 暴力递归
     * 不能直接贪心，因为局部最优并不是全局最优
     *
     */
    private int process(int[] w, int[] v, int index, int rest) {
        // 判断当前背包的空间是否不足装物品
        if(rest < 0) {
            return -1;
        }
        if(index == w.length) {
            return 0;
        }
        // 计算不装当前货物的价值
        int p1 = process(w, v, index+1, rest);
        // 计算装单签货物的价值
        int p2 = -1;
        int pNext = process(w, v, index+1, rest-w[index]);
        if(pNext != -1) {
            p2 = pNext + v[index];
        }

        // 计算带当前货物的最大值和不带当前货物的最大值
        return Math.max(p1, p2);
    }


    /**
     * 动态规划
     *
     */
    public int dp(int[] w, int[] v, int rest) {
        int N = w.length;
        // 两个可变参数构成的二维表，行代表装的货物的重量，列代表背包的空间
        int[][] dp = new int[N+1][rest+1];
//        dp[N][...] = 0 ，不用初始化，因为默认就是0
        // 从下向上填,因为最后一行是确定的
        for (int i = N-1; i >= 0; i--) {
            for (int j = 0; j <= rest; j++) {
                int p1 = dp[i+1][j];
                int p2 = -1;
                if(j - w[i] >= 0) {
                    p2 = dp[i+1][j-w[i]] + v[i];
                }
                dp[i][j] = Math.max(p1, p2);
            }
        }
        return dp[0][rest];
    }


}
