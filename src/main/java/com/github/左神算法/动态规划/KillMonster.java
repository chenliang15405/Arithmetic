package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/7/3 16:47
 */
public class KillMonster {

    /**
     * 给定3个参数，N、M、K
     * 怪兽一共有N滴血，等着英雄来砍自己，英雄每一次打击，都让怪兽流失[0~M]的血量，
     * 到底流失多少？每一次都在[0~M]上等概率的获得一个值，求K次打击之后，英雄把怪兽砍死的概率
     * <p>
     * 样本对应模型
     */
    @Test
    public void test() {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 1;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = way1(N, M, K);
            double ans2 = dp(N, M, K);
            System.out.println("测试结束" + ans1);
            System.out.println("测试结束" + ans2);
        }
//        double ans1 = way1(5, 7, 8);
//        System.out.println("测试结束" + ans1);
    }


    /**
     * 暴力递归
     */
    public double way1(int N, int M, int K) {
        // 计算出总的可能数
        double total = Math.pow(M + 1, K);
        // 计算出可以打死怪兽的可能数
        long count = process(N, M, K);

        return count / total;
    }

    // 当前可以打K次，有N滴血，每次打M滴血，打死怪兽的方法数
    private long process(int N, int M, int K) {
        if (K == 0) {
            return N <= 0 ? 1 : 0;
        }
        // 如果已经小于等于0了，那么剩下的所有方法都可以打死怪兽，所以返回所有的可能数
        if (N <= 0) {
            return (long) Math.pow(M + 1, K);
        }

        long ways = 0;
        for (int i = 0; i <= M; i++) {
            ways += process(N - i, M, K - 1);
        }
        return ways;
    }

    /**
     * 动态规划
     *
     */
    public double dp(int N, int M, int K) {
        // 计算出总的可能数
        double total = Math.pow(M + 1, K);

        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;

        for (int i = 1; i <= K; i++) {
            // 当血量为0的实收，可以直接计算当前的所有的可能性
            dp[i][0] = (long) Math.pow(M + 1, i);
            for (int j = 1; j <= N; j++) {
                long ways = 0;
                for (int index = 0; index <= M; index++) {
                    if(j - index >= 0) {
                        ways += dp[i - 1][j - index];
                    } else {
                        // 如果当前砍一刀之后，血量小于0了，直接计算后续的所有可能性，防止越界
                        ways += Math.pow(M + 1, i - 1); // i-1表示已经砍了一刀了
                    }
                }
                dp[i][j] = ways;
            }
        }
        // 计算出可以打死怪兽的可能数
        long count = dp[K][N];

        return count / total;
    }




}
