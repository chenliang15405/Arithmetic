package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/7/3 16:15
 */
public class BobDie {

    /**
     * 给定5个参数，N,M,row,col,k
     * 表示N*M的区域上，醉汉Bob初始在(row,col)位置
     * Bob一共要迈出K步，且每步都会等概率向上下左右四个方向走一个单位
     * 任何时候Bob只要离开N*M的区域，就直接死亡
     * 返回k步之后，Bob还在N*M的区域的概率
     */
    @Test
    public void test() {
        System.out.println(livePosibility1(6, 6, 10, 50, 50));
        System.out.println(livePosibility2(6, 6, 10, 50, 50));

    }

    /**
     * 暴力递归
     */
    public double livePosibility1(int row, int col, int k, int N, int M) {
        // 总的可能数为 4^k,因为每次都有4个方向选择的可能，并且有k次可以选择
        // 计算所有的可能中，在指定区域的可能的个数
        int count = process(row, col, k, N, M);
        double total = Math.pow(4, k);
        return (double) count / total;
    }

    // 含义：在(N,M)矩阵上，从(row,col)开始，走k步，可以存活的最大个数
    private int process(int row, int col, int k, int N, int M) {
        // 如果越界，则直接返回0
        if (row < 0 || row >= N || col < 0 || col >= M) {
            return 0;
        }
        // 还在棋盘中
        if (k == 0) {
            return 1;
        }
        // 计算走向每个方向的存活的最大个数
        int p1 = process(row + 1, col, k - 1, N, M);
        int p2 = process(row - 1, col, k - 1, N, M);
        int p3 = process(row, col + 1, k - 1, N, M);
        int p4 = process(row, col - 1, k - 1, N, M);

        return p1 + p2 + p3 + p4;
    }

    /**
     * 动态规划
     */
    public double livePosibility2(int row, int col, int k, int N, int M) {
        // 总的可能数为 4^k,因为每次都有4个方向选择的可能，并且有k次可以选择
        // 计算所有的可能中，在指定区域的可能的个数
        double total = Math.pow(4, k);
        // 3个变量，所以使用3维数组
        int[][][] dp = new int[N + 1][M + 1][k + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                dp[i][j][0] = 1;
            }
        }

        for (int rest = 1; rest <= k; rest++) {
            for (int i = 0; i <= N; i++) {
                for (int j = 0; j <= M; j++) {
                    int p1 = pick(dp, i + 1, j, rest - 1, N, M);
                    int p2 = pick(dp, i - 1, j, rest - 1, N, M);
                    int p3 = pick(dp, i, j + 1, rest - 1, N, M);
                    int p4 = pick(dp, i, j - 1, rest - 1, N, M);
                    dp[i][j][rest] = p1 + p2 + p3 + p4;
                }
            }
        }
        return (double) dp[row][col][k] / total;
    }

    private int pick(int[][][] dp, int row, int col, int rest, int N, int M) {
        if (row < 0 || row >= N || col < 0 || col >= M) {
            return 0;
        }
        return dp[row][col][rest];
    }

}
