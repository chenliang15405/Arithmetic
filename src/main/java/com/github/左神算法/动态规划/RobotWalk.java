package com.github.左神算法.动态规划;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/6/21 23:13
 */
public class RobotWalk {

    /**
     * 机器人路线
     *
     *   N: 固定参数 长度， 1~N
     *   M: 当前所在的位置
     *   K: 剩余可走的步数
     *   P: 最终的位置
     *
     *  返回走到目的地的方法数
     */
    @Test
    public void test() {
        System.out.println(way1(5, 2, 3, 3));
        System.out.println(way2(5, 2, 3, 3));
        System.out.println(way3(5, 2, 3, 3));
    }


    /**
     * 暴力递归
     *
     */
    public int way1(int N, int M, int K, int P) {
        return process(N, M, K, P);
    }

    private int process(int N, int M, int K, int P) {
        // base case
        if(K == 0) {
            // 判断当前是否到目标位置
            return M == P ? 1 : 0;
        }
        // 当位于最左边的时候，边界情况
        if(M == 1) {
            return process(N, M + 1, K - 1, P);
        }
        // 当位于最右边的时候，边界情况
        if(M == N) {
            return process(N, M - 1, K - 1, P);
        }
        // 总的方法数
        return process(N, M - 1, K - 1, P) + process(N, M + 1, K - 1, P);
    }

    /**
     * 记忆化搜索
     *
     */
    public int way2(int N, int M, int K, int P) {
        int[][] dp = new int[N + 1][K + 1];
        return process2(N, M, K, P, dp);
    }

    private int process2(int N, int M, int K, int P, int[][] dp) {
        int ans = 0;
        // base case
        if(K == 0) {
            // 判断当前是否到目标位置
            ans = M == P ? 1 : 0;
            return ans;
        }
        // 当位于最左边的时候，边界情况
        if(M == 1) {
            ans = process(N, M + 1, K - 1, P);
        } else if(M == N) {
            ans = process(N, M - 1, K - 1, P);
        } else {
            // 总的方法数
            ans = process(N, M - 1, K - 1, P) + process(N, M + 1, K - 1, P);
        }

        dp[M][K] = ans;
        return ans;
    }


    /**
     * 动态规划
     *
     */
    public int way3(int N, int M, int K, int P) {
        if(N < 1 || M > N || K < 0 || P > N || M < 1) {
            return 0;
        }
        // 两个变量，二维数组
        int[][] dp = new int[N + 1][K + 1]; // dp[i][j] 在i、j的位置有多少种解法
        dp[P][0] = 1; // 只有当前位置等于P的时候，并且K=0那么这个时候有一种解法

        // 不能从行开始遍历，需要将列作为外层循环进行遍历，因为每个位置的依赖关系，每个位置都依赖的是前一列中不同行的数据，所以需要按照列进行填充
        // 这样相同行不同列在填充的时候才可以找到数据
        for (int i = 1; i <= K; i++) {
            for (int j = 1; j <= N; j++) {
                if(j == 1) {
                    dp[j][i] = dp[j+1][i-1];
                } else if(j == N) {
                    dp[j][i] = dp[j-1][i-1];
                } else {
                    dp[j][i] = dp[j+1][i-1] + dp[j-1][i-1];
                }
            }
        }
        return dp[M][K];
    }

}
