package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/7/3 18:00
 */
public class SplitNum {

    /**
     * 给定一个正数1，裂开的方法有一种(1)，给定一个正数2，裂开的方法有两种(1,1)(2)
     * 给定一个正数3，裂开的方法有3种（1,1,1）（1,2）（3）,任何一个数的裂开的时候，后面的数不能比前面的数小，
     * 给定一个正数n，求裂开的方法数
     * <p>
     * 动态规划优化状态的技巧
     */

    @Test
    public void test() {
        System.out.println(way1(3));
        System.out.println(dp(3));
    }


    public int way1(int n) {
        if(n < 0) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }
        return process(1, n);
    }

    // 上一拆出来的数是pre，还剩余rest数需要拆，返回拆开的方法数
    private int process(int pre, int rest) {
        // 当rest等于0表示当前是一种有效的方法数
        if(rest == 0) {
            return 1;
        }
        // 如果上一个拆出来的数大于剩余的数，那么违反条件，不算做有效方法数
        if(pre > rest) {
            return 0;
        }
        int ways = 0;
        // 以pre为开始数，进行拆分
        for (int i = pre; i <= rest; i++) {
            ways += process(i, rest - i);
        }
        return ways;
    }

    /**
     * 动态规划
     *
     */
    public int dp(int n) {
        if(n < 0) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 0; pre <= n; pre++) {
            dp[pre][0] = 1;
            // 对角线也是1，因为两个数相同
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 0; pre--) {
            // 从对角线后面一个开始填充，前面的都是不会成立的
            for (int rest = pre + 1; rest <= n; rest++) {
                int ways = 0;
                // 以pre为开始数，进行拆分
                for (int i = pre; i <= rest; i++) {
                    ways += dp[i][rest - i];
                }
                dp[pre][rest] = ways;
            }
        }

        return dp[1][n];
    }


}
