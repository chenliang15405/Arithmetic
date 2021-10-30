package com.github.bstation.string;

/**
 * @author tangsong
 * @date 2021/5/30 18:26
 */
public class EditCost {

    /**
     *
     * 给定两个字符串str1和str2，在给定3个整数ic、dc和rc，分别代表插入、删除和替换一个字符的代价，返回str1编辑成str2的最小代价
     *
     * 举例：
     * str1="abc"，str2="adc" ic=5、dc=3、rc=2，从"abc"编辑为"adc"，把"b"替换成"d"是代价最小的，所以返回2
     * str1="abc"，str2="adc" ic=5、dc=3、rc=100，从"abc"编辑为"adc"，先删除"b"，然后插入"d"的代价是最小的，所以返回8
     * str1="abc"，str2="abc"，则不用编辑，所以返回0
     */


    /**
     * 动态规划解决，dp[i][j]表示以i和j结尾的字符，str1变为str2所需要的代价
     * 这个问题需要从最后一个字符向前看
     *
     */
    public int minCost(String s1, String s2, int ic, int dc, int rc) {
        // 创建二维表，行代表s1的每个字符结尾的情况, 列代表s2的每个字符结尾的情况
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = s1.length();
        int M = s2.length();
        // 创建二维数组
        int[][] dp = new int[N + 1][M + 1]; // 表示的不是索引，是长度，所以需要+1
        dp[0][0] = 0;

        // 填充第一行，当s1的长度是0，那么需要编辑为s2，则需要插入每个字符
        for (int i = 1; i <= M; i++) {
            dp[0][i] = ic * i;
        }
        // 填充第一列，当s2的长度为0，那么编辑为s2，则需要删除每个字符的代价
        for (int i = 1; i <= N; i++) {
            dp[i][0] = dc * i;
        }
        // 填充dp数组
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                // 判断str1的最后一个字符是否和str2的最后一个字符相等
                if(str1[i-1] == str2[j-1]) {
                    // 如果相等，那么需要计算的代价就是前一个字符串需要做的代价
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    // 如果不相等，那么就需要是替换操作, 就是i-1字符串的代价 + 替换的代价
                    dp[i][j] = dp[i-1][j-1] + rc;
                }
                // 需要判断当前替换和增加删除的代价做最小值
                dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + ic); // 如果是插入代价，那么就需要计算str2的j-1个字符串代价是多少
                dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + dc); // 如果是删除代价，那么就需要计算str1的i-1个字符串代价是多少
            }
        }
        return dp[N-1][M-1];
    }

}
