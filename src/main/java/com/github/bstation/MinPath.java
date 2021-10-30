package com.github.bstation;

import org.junit.Test;
import sun.plugin2.gluegen.runtime.CPU;

/**
 * @author tangsong
 * @date 2021/5/23 15:44
 */
public class MinPath {

    /**
     * 给定一个二维数组，其中每个数都是正数，要求从左上到右下每一步只能向右或向下，沿途经过的数字累加起来，最后返回最小的路径和
     *
     */

    @Test
    public void test() {
        int[][] arr = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };

        System.out.println(minPath(arr));

        System.out.println(way2(arr));
    }


    public int minPath(int[][] arr) {
        return way1(arr, 0, 0);
    }

    private int way1(int[][] arr, int i, int j) {
        // 到达终点
        if(i == arr.length - 1 && j == arr[0].length - 1) {
            return arr[i][j];
        }
        // 当位于最左边的时候，只能向右走
        if(i == arr.length - 1) {
            return arr[i][j] + way1(arr, i, j+1);
        }
        // 当位于最右边的时候，只能向下走
        if(j == arr[0].length - 1) {
            return arr[i][j] + way1(arr, i + 1, j);
        }

        // 每一步都可以向右或者向下
        int sum1 = way1(arr, i + 1, j);
        int sum2 = way1(arr, i, j + 1);

        return arr[i][j] + Math.min(sum1, sum2);
    }


    /**
     * 通过暴力递归改动态规划
     *
     *  普通位置的依赖关系：(i, j) 依赖它左边(i, j - 1)和上边(i - 1, j)位置
     */
    private int way2(int[][] arr) {
        int row = arr.length;
        int col = arr[0].length;

        // 行和列的每种解法的路径和
        int[][] dp = new int[row][col];
        // 初始位置
        dp[0][0] = arr[0][0];

        // 初始化base case
        // 最上面一行只能向下走, 从1开始
        for (int i = 1; i < col; i++) {
            // 依赖左边的位置
            dp[0][i] = arr[0][i] + dp[0][i-1];
        }
        // 最左边的列只能向右走
        for (int i = 1; i < row; i++) {
            // 依赖上面的位置
            dp[i][0] = arr[i][0] + dp[i-1][0];
        }
        // 开始递归填充dp数组
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                // 根据暴力递归，计算每个位置的最小路径, 每个位置都依赖上一个位置或者左边的位置
                dp[i][j] = arr[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
            }
        }
        // 获取终点的位置
        return dp[row-1][col-1];
    }
}
