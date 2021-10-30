package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/27 20:10
 */
public class MinPathSum {

    /**
     * 最小路径问题
     * 给定一个二维数组matrix，一个人必须从左上角出发最后到达右下角沿途只可以向下或者向右走，沿途的数字都累加就是累加和
     * 返回最小距离的累加和
     */
    @Test
    public void test() {
        int[][] arr = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 2}
        };

        System.out.println(minPath1(arr));
        System.out.println(minPath2(arr));
        System.out.println(minPath3(arr));
    }

    /**
     * 暴力递归
     */
    public int minPath1(int[][] matrix) {
        return process(matrix, 0, 0, matrix.length - 1, matrix[0].length - 1);
    }

    // 从x,y开始到a,b的最小的累加和
    private int process(int[][] matrix, int x, int y, int a, int b) {
        if (x == a && y == b) {
            return matrix[x][y];
        }
        if (x == matrix.length - 1) {
            return matrix[x][y] + process(matrix, x, y + 1, a, b);
        }
        if (y == matrix[0].length - 1) {
            return matrix[x][y] + process(matrix, x + 1, y, a, b);
        }
        // 计算向下或者向左中最小的距离
        int p1 = matrix[x][y] + process(matrix, x + 1, y, a, b);
        int p2 = matrix[x][y] + process(matrix, x, y + 1, a, b);
        return Math.min(p1, p2);
    }

    /**
     * 动态规划
     */
    public int minPath2(int[][] matrix) {
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        // 初始化dp， dp[i][j] 到达这个位置的最小距离
        dp[0][0] = matrix[0][0];
        // 先填充好第一行，因为第一行只依赖左边的数据
        for (int i = 1; i < M; i++) {
            // 0,0的位置不依赖其他的数据
            dp[0][i] = matrix[0][i] + dp[0][i - 1];
        }
        // 填充好第一列
        for (int i = 1; i < N; i++) {
            // 0,0的位置不依赖其他的数据
            dp[i][0] = matrix[i][0] + dp[i - 1][0];
        }
        // 填充每个位置，从上向下
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                // 每个位置都只依赖这个位置的上面和左边，计算两个中的最小值加上当前的值就是最小距离
                int p1 = matrix[i][j] + dp[i - 1][j];
                int p2 = matrix[i][j] + dp[i][j - 1];

                dp[i][j] = Math.min(p1, p2);
            }
        }
        return dp[N - 1][M - 1];
    }


    /**
     * 动态规划优化
     * 空间压缩，使用一维数组，因为每个计算的时候只需要依赖的上一个数组就可以，不需要二维数组的其他的行
     */
    public int minPath3(int[][] matrix) {
        int N = matrix.length;
        int M = matrix[0].length;
        // 只使用一维数组
        int[] arr = new int[M];
        arr[0] = matrix[0][0];
        for (int i = 1; i < M; i++) {
            arr[i] = arr[i - 1] + matrix[0][i];
        }
        // 开始计算每一行的值
        for (int i = 1; i < N; i++) {
            // 每次填充一行的时候，先计算0位置的数据，0位置比较特殊，不能依赖左边的位置
            arr[0] += matrix[i][0];
            for (int j = 1; j < M; j++) {
                // 因为当前行的0位置已经计算好，并且arr中的数据，其他列此时放入的上一行的数据，所以可以从左向右进行计算
                // 每一个位置都计算左边的数据和上一行当前列的数据的最小值，加上当前值就是此时的最小距离
                arr[j] = Math.min(arr[j - 1], arr[j]) + matrix[i][j];
            }
        }
        return arr[M - 1];
    }


}
