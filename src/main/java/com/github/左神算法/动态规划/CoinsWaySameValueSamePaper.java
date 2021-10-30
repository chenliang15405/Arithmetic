package com.github.左神算法.动态规划;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/7/3 16:02
 */
public class CoinsWaySameValueSamePaper {

    /**
     * arr数货币数组，其中的值都是整数，在给定一个正数aim
     * 每个值都认为是一张货币
     * 认为值相同的货币没有任何不同，返回组成aim的方法数
     * 例如：arr = {1,2,1,1,2,1,2}，aim=4
     *  方法：1+1+1+1， 1+1+2, 2+2
     *  一共就3种方法，所以返回3
     */
    @Test
    public void test() {
        int[] arr = {1, 2, 1, 1, 2, 1, 2};
        int aim = 4;
        System.out.println(way1(arr, aim));
        System.out.println(dp1(arr, aim));
        System.out.println(dp2(arr, aim));

    }

    /**
     * 暴力递归
     *
     */
    public int way1(int[] arr, int aim) {
        // 使用Map统计每个货币对应的张数
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        int[] coins = new int[map.size()];
        int[] counts = new int[map.size()];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            coins[index] = entry.getKey();
            counts[index] = entry.getValue();
            index++;
        }

        return process(coins, counts, aim, 0);
    }

    // 从arr[index...]开始选择任意张数的货币（不能超过counts[index...]的张数），最终得到aim的最多的方法数多少
    private int process(int[] coins, int[] counts, int aim, int index) {
        if(index == coins.length) {
            return aim == 0 ? 1 : 0;
        }
        int ways = 0;
        // 不仅需要判断aim的边界范围还需要判断当前可以使用的张数
        for (int zhang = 0; zhang * coins[index] <= aim && zhang <= counts[index]; zhang++) {
            ways += process(coins, counts, aim - zhang * coins[index], index + 1);
        }
        return ways;
    }


    /**
     * 动态规划
     *
     */
    public int dp1(int[] arr, int aim) {
        // 使用Map统计每个货币对应的张数
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        int N = map.size();
        int[] coins = new int[N];
        int[] counts = new int[N];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            coins[index] = entry.getKey();
            counts[index] = entry.getValue();
            index++;
        }

        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                int ways = 0;
                // 不仅需要判断aim的边界范围还需要判断当前可以使用的张数
                for (int zhang = 0; zhang * coins[i] <= j && zhang <= counts[i]; zhang++) {
                    ways += dp[i + 1][j - zhang * coins[i]];
                }
                dp[i][j] = ways;
            }
        }
        return dp[0][aim];
    }


    /**
     * 动态规划优化 --  斜率优化
     *    根据空间位置关系的依赖优化所有的枚举行为
     *
     */
    public int dp2(int[] arr, int aim) {
        // 使用Map统计每个货币对应的张数
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        int N = map.size();
        int[] coins = new int[N];
        int[] counts = new int[N];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            coins[index] = entry.getKey();
            counts[index] = entry.getValue();
            index++;
        }

        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                dp[i][j] = dp[i + 1][j];
                if (j - coins[i] >= 0) {
                    dp[i][j] += dp[i][j - coins[i]];
                }
                if (j - coins[i] * (counts[i] + 1) >= 0) {
                    dp[i][j] -= dp[i + 1][j - coins[i] * (counts[i] + 1)];
                }
            }
        }
        return dp[0][aim];
    }


}
