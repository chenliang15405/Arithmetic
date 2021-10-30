package com.github.bstation;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/23 15:05
 */
public class Bag {

    /**
     * 背包容量为w，一共有n袋零食，第i袋零食体积为v[i]>0,总体积不超过背包容量的情况下，一共有多少种方法（总体积为0也算一种方法）
     *
     */

    @Test
    public void test() {
        int[] arr = {3,2,4,7};
        int w = 10;

        System.out.println(getMax(arr, w));

        System.out.println(way2(arr, w));
    }


    public int getMax(int[] arr, int w) {
        return way1(arr, 0, w);
    }

    /**
     * 递归
     *   尝试模型：要某个零食、不要某个零食进行尝试
     *
     * @param arr 零食的集体
     * @param i 当前的哪个零食
     * @param w 背包容量
     */
    private int way1(int[] arr, int i, int w) {
        if(w < 0) {
            // 这里可以直接返回0，但是对于某些问题，min等问题就不能返回0，因为0会引入处理问题
            return -1;
        }
        if(i == arr.length) {
            // 如果背包容量还有，并且已经选到最后一个零食，那么就表示一种有效方案
            return 1;
        }
        // 如果不要当前的零食
        int p1 = way1(arr, i + 1, w);
        // 如果要当前零食
        int p2 = way1(arr, i + 1, w - arr[i]);
        // 返回总的方法数
        return p1 + (p2 == -1 ? 0 : p2);
    }


    /**
     *  根据暴力递归改动态规划
     *
     */
    private int way2(int[] arr, int w) {
        if(w < 0) {
            return -1;
        }
        int N = arr.length;
        // 构建二维数组, 当前的零食体积和背包容量
        int[][] dp = new int[N+1][w+1];
        // dp的N位置都是1有效解，因为当选择到零食的最后一个，无论背包剩余多少空间，只要不是负的都是有效解
        for (int j = 0; j < w + 1; j++) {
            dp[N][j] = 1;
        }

        // 从最后一行向前计算，因为最后一行已经填充了
        for (int i = N-1; i >= 0; i--) {
            // 按照从左向右填充
            for (int j = 0; j <= w; j++) {
                // 递归改造为dp
                int p1 = dp[i + 1][j];
                int p2  = 0; // 这里不设置为-1了， 可以直接设置为0
                if(j - arr[i] >= 0) {
                    p2 = dp[i+1][j - arr[i]];
                }
                dp[i][j] = p1 + p2;
            }
        }
        // 按照递归的初始参数，需要的结果就是0,w
        return dp[0][w];
    }
}
