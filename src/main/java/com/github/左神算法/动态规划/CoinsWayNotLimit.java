package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/27 22:41
 */
public class CoinsWayNotLimit {

    /**
     * arr是面值数组，其中的值都是正数并且没有重复，在给定一个正数aim。
     * 每个值都认为是一张面值，且认为张数是无限的，返回组成aim的方法数
     * 例如: arr = {1,2} aim = 4
     * 1+1+1+1、1+1+2、2+2
     * 一共3种方法
     */
    @Test
    public void test() {
        int[] arr = {2,1,3};
        int aim = 4;
        System.out.println(coinsWay(arr, aim));
        System.out.println(dp(arr, aim));
        System.out.println(dp1(arr, aim));
    }

    @Test
    public void test1() {
        int a = 1024;
        boolean isTwo = (a & (a - 1)) == 0;
        System.out.println(isTwo);

    }

    /**
     * 暴力递归
     *
     */
    public int coinsWay(int[] arr, int aim) {
        return process1(arr, aim, 0);
    }

    // arr[index...] 到后面所有面值可有，总共有多少种方法数
    private int process1(int[] arr, int aim, int index) {
        if (index == arr.length) {
            return aim == 0 ? 1 : 0;
        }
        // 加不上这个都行，因为是DFS所以，所以这里只是提前结束
//        if(aim == 0) {
//            return 1;
//        }
        int ways = 0;
        // 每个面值有无限张可以使用，那么每次选择的时候都可以选择不同的张数
        for (int zhang = 0; zhang * arr[index] <= aim; zhang++) {
            ways += process1(arr, aim - zhang * arr[index], index + 1);
        }
        return ways;
    }


    /**
     * 动态规划
     *
     */
    public int dp(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                int ways = 0;
                // 每个面值有无限张可以使用，那么每次选择的时候都可以选择不同的张数
                for (int zhang = 0; zhang * arr[i] <= j; zhang++) {
                    ways += dp[i + 1][j - zhang * arr[i]];
                }
                dp[i][j] = ways;
            }
        }
        return dp[0][aim];
    }


    /**
     * 斜率优化， 优化dp的枚举行为
     *   建立空间感，因为其中的每个位置都有相互依赖的关系，当前位置的依赖关系可以通过之前的位置已经计算过了，所以直接依赖之前的位置的
     *   数据即可，不用再次枚举所有的，只需要加上额外的
     *
     */
    public int dp1(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                // 因为dp[i][j]位置时下一行的每个[i+1][j-zhang*arr[i]]的位置，而前面的每个位置已经通过同一行的[i][j-arr[i]]计算过了
                // 所以可以直接获取该数据, 只需要加上当前的位置的数据
                dp[i][j] = dp[i+1][j];
                if(j - arr[i] >= 0) {
                    dp[i][j] += dp[i][j - arr[i]];
                }
            }
        }
        return dp[0][aim];
    }


}
