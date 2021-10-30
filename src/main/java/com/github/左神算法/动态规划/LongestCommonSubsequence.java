package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/26 20:42
 */
public class LongestCommonSubsequence {

    /**
     * 最长公共子序列
     *     str1: abc12kd3
     *     str2: klso1ll2lo2
     *
     *     最长公众组序列是123，子序列可以是不连续的
     *
     *  一个样本做行，一个样本做列的尝试模型，这种尝试模型一般从最后一个位置开始尝试
     *
     */
    @Test
    public void test() {
        String str1 = "abc12kd3";
        String str2 = "klso1ll23lo2";

        System.out.println(way1(str1, str2));

        System.out.println(way2(str1, str2));
    }


    /**
     * 暴力递归
     * 
     */
    public int way1(String s1, String s2) {
        return process1(s1.toCharArray(), s2.toCharArray(), s1.length() - 1, s2.length() - 1);
    }

    /**
     * 函数的定义：
     *      s1[0..i] 与 s2[0...j] 的长度下，最长的公共子序列
     * 那么主函数中，传递的函数的参数就是 s1, s2, s1.length-1, s2.length - 1
     */
    private int process1(char[] s1, char[] s2, int i, int j) {
        // base case
        if(i == 0 && j == 0) {
            return s1[i] == s2[j] ? 1 : 0;
        }
        if(i == 0) {
            if(s1[i] == s2[j]) {
                return 1;
            } else {
                // 如果不相等，那么就计算i和前面的所有位置是否相等
                return process1(s1, s2, i, j - 1);
            }
        }
        if(j == 0) {
            if(s1[i] == s2[j]) {
                return 1;
            } else {
                // 如果不相等，那么就算i之前的所有位置与j位置是否相等
                return process1(s1, s2, i - 1, j);
            }
        }
        // 开始尝试
        // 四种可能，结尾相等，结尾不相等，以一个结尾和另一个结尾
        int p1 = process1(s1, s2, i - 1, j - 1);
        int p2 = process1(s1, s2, i, j - 1);
        int p3 = process1(s1, s2, i - 1, j);
        // 第4中情况是 process1(s1, s2, i, j) 但是不能这样写，因为基本条件就是这个，这样会无线循环
        int p4 = 0;
        if(s1[i] == s2[j]) {
            // 如果相等，那么剩下的可能和p1相同
            p4 = p1 + 1;
        }
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    /**
     * 动态规划
     *
     */
    public int way2(String s1, String s2) {
        int N = s1.length();
        int M = s2.length();
        char[] char1 = s1.toCharArray();
        char[] char2 = s2.toCharArray();
        int[][] dp = new int[N][M];
        // 开始初始化dp
        dp[0][0] = char1[0] == char2[0] ? 1 : 0;

        for (int i = 1; i < N; i++) {
            dp[i][0] = char1[i] == char2[0] ? 1 : dp[i-1][0];
        }
        for (int j = 1; j < M; j++) {
            dp[0][j] = char1[0] == char2[j] ? 1 : dp[0][j - 1];
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                int p1 = dp[i-1][j-1];
                int p2 = dp[i][j-1];
                int p3 = dp[i-1][j];
                int p4 = 0;
                if(char1[i] == char2[j]) {
                    p4 = p1 + 1;
                }
                dp[i][j] = Math.max(Math.max(p1, p2), Math.max(p3, p4));
            }
        }
        return dp[N-1][M-1];
    }


}
