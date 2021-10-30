package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/27 22:39
 */
public class CoinsWayEveryPaperDifferent {

    /**
     * arr是货币数组，其中的值都是整数，在给定一个正数aim。
     * 每个值都认为是一张货币，即便是值相同的货币也认为每一张都是不同的，返回组成aim的方法数
     * 例如: arr = {1,1,1} aim = 2
     * 每个位置都是组成aim，总的方法数是3
     *
     *
     *  从左向右的尝试模型
     */

    @Test
    public void test() {
        int[] arr = {1,1,1};
        int aim = 2;

        System.out.println(coinsWay(arr, aim));
        System.out.println(dp(arr, aim));
    }

    /**
     * 暴力递归
     */
    public int coinsWay(int[] arr, int aim) {
        if (arr.length <= 0 || aim <= 0) {
            return 0;
        }
        // 这种模型，一般都选择当前数据要或者不要，和背包类似
        return process1(arr, aim, 0);
    }

    // 从index开始，返回最大的方法数
    private int process1(int[] arr, int aim, int index) {
        if (aim < 0) {
            return 0;
        }
        if (index == arr.length) {
            return aim == 0 ? 1 : 0;
        }
        // 不要当前位置的数
        int p1 = process1(arr, aim, index + 1);
        // 要当前位置的数
        int p2 = process1(arr, aim - arr[index], index + 1);

        // 计算的是最多的方法数，所以返回总的方法数
        return p1 + p2;
    }


    /**
     * 动态规划
     *
     */
    public int dp(int[] arr, int aim) {
        if (arr.length <= 0 || aim <= 0) {
            return 0;
        }
        int N = arr.length;
        int M = aim;
        int[][] dp = new int[N + 1][M + 1];
        // 初始化dp
        dp[N][0] = 1;

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= M; j++) {
                int p1 = dp[i + 1][j];
                int p2 = j - arr[i] < 0 ? 0 : dp[i + 1][j - arr[i]];
                dp[i][j] = p1 + p2;
            }
        }
        return dp[0][aim];
    }

}
