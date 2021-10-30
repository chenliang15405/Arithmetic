package com.github.bstation;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/5/16 14:37
 */
public class Robot {


    @Test
    public void ways1() {
        int N = 7;
        int M = 2;
        int K = 5;
        int P = 3;

        System.out.println(way(N, M, K, P));

        System.out.println(way2(N, M, K, P));

        System.out.println(way3(N, M, K, P));
    }

    /**
     * 暴力递归
     *
     * N： 数组的长度
     * M: 当前的位置
     * K: 可以走的步数
     * P: 目标位置
     */
    public int way(int N, int M, int K, int P) {
        if(N < 1 || M > N || K < 0 || P > N || M < 1) {
            return 0;
        }
        if(M == P && K == 0) {
            return 1;
        }
        if(K <= 0) {
            return 0;
        }
        if(M == 1) {
            return way(N, M + 1, K - 1, P);
        }
        if(M == N) {
            return way(N, M - 1, K - 1, P);
        }
        return way(N, M + 1, K - 1, P) + way(N, M - 1, K - 1, P);
    }


    /**
     * 动态规划（记忆化搜索）
     *   因为有两个可变参数，其他两个参数都是固定的，所以缓存的数据结构采用二维表
     *
     */
    public int way2(int N, int M, int K, int P) {
        if(N < 1 || M > N || K < 0 || P > N || M < 1) {
            return 0;
        }
        // 数组的长度为N，因为M可能需要走到N的长度
        int[][] dp = new int[N+1][K+1];

        // 先将dp中的元素都初始化为-1，如果有缓存的数据就不为-1，不为0防止和答案重复
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }

        return walk(N, M, K, P, dp);
    }

    private int walk(int N, int M, int K, int P, int[][] dp) {
        // 缓存中有则从缓存中取
        if(dp[M][K] != -1) {
            return dp[M][K];
        }

        // 缓存没有则进行计算
        if(K == 0) {
            dp[M][K] = M == P ? 1 : 0;
            return dp[M][K];
        }
        if(M == 1) {
            dp[M][K] = walk(N, M + 1, K - 1, P, dp);
            return dp[M][K];
        }
        if(M == N) {
            dp[M][K] = walk(N, M - 1, K - 1, P, dp);
            return dp[M][K];
        }
        dp[M][K] = walk(N, M + 1, K - 1, P, dp) + walk(N, M - 1, K - 1, P, dp);
        return dp[M][K];
    }


    /**
     * 动态规划
     *
     */
    public int way3(int N, int M, int K, int P) {
        if(N < 1 || M > N || K < 0 || P > N || M < 1) {
            return 0;
        }
        // 数组的长度为N，因为M可能需要走到N的长度
        // 因为是2个可变参数，所以定义二维数组代表每个确定的参数所能的最优解
        // 行代表的是位置，K代表的是剩余的步数
        int[][] dp = new int[N+1][K+1];
        // 当步数为0时，如果当前位置为P，则有一种解法
        dp[P][0] = 1;
        // 并且根据暴力递归得到的结论，当N当前的行数为1时，由当前位置的下一行的前一列决定
        // 当前行为N时，当前位置的下一行由前一行的前一列决定
        // 其他行，则由前一行的前一列 和 前一行的下一列 相加决定
        // 这里需要通过步数作为外驱动，因为在递归的过程中，通过每个步数可以确定转移方程获取到的方法数
        for (int i = 1; i <= K; i++) {
            for (int j = 1; j <= N; j++) {
                // 下面的转移方程是通过暴力递归推导出来的
                if (j == 1) {
                    dp[j][i] = dp[j+1][i-1];
                }else if(j == N) {
                    dp[j][i] = dp[j-1][i-1];
                } else {
                    dp[j][i] = dp[j+1][i-1] + dp[j-1][i-1];
                }
            }
        }
        System.out.println(Arrays.deepToString(dp));
        return dp[M][K];
    }



}
