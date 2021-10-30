package com.github.bstation;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/9/25 17:25
 */
public class LongestCommonSubStringII {

    /**
     * 给定两个字符串str1和str2,输出两个字符串的最长公共子串
     * 题目保证str1和str2的最长公共子串存在且唯一。
     *
     * 输入：
     *  "1AB2345CD","12345EF"
     * 返回值：
     *  "2345"
     */
    @Test
    public void test() {
        String str1 = "1AB2345CD";
        String str2 = "12345EF";

        System.out.println(LCS(str1, str2));
    }


    /**
     * 动态规划
     *
     */
    public String LCS (String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        char[] c1 = str1.toCharArray();
        char[] c2 = str2.toCharArray();

        // dp[i][j] 表示 str1以i结尾，str2以j结尾的最长子串的长度
        int[][] dp = new int[n][m];
        dp[0][0] = c1[0] == c2[0] ? 1 : 0;
        int maxLen = 0;
        int end = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if(c1[i] == c2[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = 0;
                }

                // 判断当前是否为最大长度，需要记录索引的位置和长度，计算最终的字符串
                if(dp[i][j] > maxLen) {
                    maxLen = dp[i][j];
                    // 记录最长子串的最后的索引
                    end = j;
                }
            }
        }

        if(maxLen == 0) {
            return null;
        }
        return str2.substring(end - maxLen + 1, end + 1);
    }
}
