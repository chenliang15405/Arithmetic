package com.arithmetic.features.arithmetic.interview;

import org.junit.Test;

/**
 * 岛问题
 *
 * 一个矩阵中只有0和1，每个位置都可以和自己的上、下、左、右四个位置相连，
 * 如果有一片1连在一起，则这个部分叫做岛，计算一个矩阵中有多少个岛
 *
 *
 * 如果矩阵数据量很大，如何计算
 *
 *  将矩阵分成多个小矩阵，统计每个小矩阵的岛信息，然后合并，合并的时候只用统计每个矩阵的岛信息
 *  以及他们的边界信息，通过并查集的方式合并即可
 *
 *
 * @author alan.chen
 * @date 2020/6/14 10:16 PM
 */
public class CountIsLand {


    @Test
    public void countIsLand() {
        int[][] arr = new int[][]{
                { 0, 1, 1, 1, 1, 0, 0 },// 0
                { 0, 1, 0, 0, 0, 0, 0 },// 1
                { 0, 1, 0, 1, 1, 1, 0 },// 2
                { 0, 1, 0, 0, 0, 0, 1 },// 3
                { 0, 0, 0, 1, 1, 1, 1 },// 4
        };

        int M = arr.length - 1;
        int N = arr[0].length - 1;

        int count = 0;

        for (int i = 0; i <= M; i++) {
            for (int j = 0; j <= N; j++) {
                // 如果该点是岛，则将与该1相连的所有1标示，因为是同一个岛
                if(arr[i][j] == 1) {
                    // 自增
                    count++;
                    // 将同一个岛标示
                    infect(arr, i, j, M, N);
                }
            }
        }

        System.out.println("岛的数量" + count);

    }

    /**
     * 将当前位置相连的岛标示
     *
     * 递归回溯
     *
     * @param M
     * @param N
     */
    private void infect(int[][] arr, int i, int j, int M, int N) {
        // 递归 base case
        if(i < 0 || i > M || j < 0 || j > N || arr[i][j] != 1) {
            return;
        }
        // 将当前位置标示为已访问
        arr[i][j] = 2;

        // 向右标示
        infect(arr, i, j + 1, M, N);
        // 向下标示
        infect(arr, i + 1, j, M, N);
        // 向左
        infect(arr, i, j - 1,M, N);
        // 向上
        infect(arr, i - 1, j, M, N);
    }

}
