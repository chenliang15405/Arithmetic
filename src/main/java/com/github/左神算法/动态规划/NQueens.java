package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/7/4 15:52
 */
public class NQueens {

    /**
     * N皇后问题
     *
     *   N皇后为是指在N*N的棋盘上要摆N个皇后，要求任何两个皇后不同行、不同列、也不再同一条斜线是哪个
     *
     *   给定一个整数n，返回n皇后的摆法有多少种
     *   n=1，返回1
     *   n=2或者3,都没有解法，返回0
     *   n=8，返回92
     *
     */
    @Test
    public void test() {
        int n = 8;
        System.out.println(way(n));
    }

    /**
     * 暴力递归
     *   时间复杂度: O(N^N)
     *
     */
    public int way(int n) {
        if(n == 0) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }
        if(n == 2 || n == 3) {
            return 0;
        }
        // 使用一维数组记录当前每一行选择的皇后的位置
        int[] p = new int[n];
        return process(n, p, 0);
    }

    // 从index个皇后开始选择，不管之前的选择的位置，最多的每个皇后不冲突的方法数
    private int process(int n, int[] p, int row) {
        if(row == n) {
            return 1;
        }
        int ways = 0;
        // 对每个皇后来讲，每个位置都可以被尝试进行选择
        for (int i = 0; i < n; i++) {
            // 将第index行的皇后拜访到i位置
            p[row] = i;
            // 判断当前位置是否可以选择，不冲突
            if(isValid(p, i, row)) {
                ways += process(n, p, row + 1);
            }
        }
        return ways;
    }

    // 判断当前行的皇后摆放的位置是否和之前的位置冲突
    private boolean isValid(int[] p, int cur, int row) {
        // 因为行肯定是否不一样的，索引只需要判断是否同一列或者同一斜线
        for (int i = 0; i < row; i++) {
            // 任意一个成立即可
            if(p[i] == cur || Math.abs(i - row) == Math.abs(p[i] - cur)) {
                return false;
            }
        }
        return true;
    }


}
