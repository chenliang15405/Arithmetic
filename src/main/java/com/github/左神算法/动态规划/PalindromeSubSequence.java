package com.github.左神算法.动态规划;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/6/27 16:39
 */
public class PalindromeSubSequence {

    /**
     * 最长回文子序列
     * 给定一个字符串str，返回这个字符串的最长回文子序列的长度
     * 比如： str = "a12b3c43def2ghi1kpm"
     * 最长回文子序列是“1234321” 或者“123c321”，返回长度7
     * <p>
     * 有两种思路：
     * 1. 计算当前的字符串和将字符串反转之后的字符串的最长公共子序列的长度，这个最长回文子序列的长度就是最长回文子序列
     * 2. 范围尝试模型，L...R上计算最长的回文子序列
     * <p>
     * 范围尝试模型解题思路：
     * 一般会根据L 和 R的位置进行考虑计算来进行解题
     */
    @Test
    public void test() {
        String str = "a12b3c43def2ghi1kpm";
        System.out.println(lpsl(str));
        System.out.println(dp(str));
        System.out.println(longestPalindromeSubseq(str));
    }


    /**
     * 暴力递归
     */
    public int lpsl(String str) {
        return process1(str.toCharArray(), 0, str.length() - 1);
    }

    // 计算str[L..R]上的最长回文子序列
    private int process1(char[] str, int L, int R) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return 1;
        }
        // 只有2个字符，这种情况可以不考虑
//        if (L == R - 1) {
//            return str[L] == str[R] ? 2 : 1;
//        }
        // 根据L和R的位置考虑每种情况，一共有4中情况，分别对L和R进行考虑
        // 回文子序列是否以L和R开头，是否以L+1、R开头
        int p1 = process1(str, L + 1, R);
        int p2 = process1(str, L + 1, R - 1);  
        int p3 = process1(str, L, R - 1);
        // 因为p4就是L和R相等的情况，所以不能直接这样写，因为这样的话就无线递归了
        int p4 = 0;
        if (str[L] == str[R]) {
            // 2 表示L和R相等的情况
            p4 = 2 + process1(str, L + 1, R - 1);
        }

        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    /**
     * 从左向右尝试模型
     *   时间复杂度较高
     *
     */
    public int longestPalindromeSubseq(String s) {
        if(s == null || s.length() == 0) return 0;
        char[] chars = s.toCharArray();
        return process(chars, 0, "");
    }

    private int process(char[] chars, int index, String path) {
        if(index == chars.length) {
            if(isPalidStr(path)) {
                return path.length();
            }
            return 0;
        }
        int p1 = process(chars, index + 1, path);
        int p2 = process(chars, index + 1, path + chars[index]);
        return Math.max(p1, p2);
    }
    Map<String, Boolean> map = new HashMap<>();

    private boolean isPalidStr(String path) {
        if(map.containsKey(path)) return map.get(path);
        boolean flag = true;
        for(int i = 0; i < path.length() / 2; i++) {
            if(path.charAt(i) != path.charAt(path.length() - i - 1)) {
                flag = false;
            }
        }
        map.put(path, flag);
        return flag;
    }

    /**
     * 动态规划
     */
    public int dp(String str) {
        char[] chars = str.toCharArray();
        int N = str.length();
        int[][] dp = new int[N][N];
        // 初始化dp数组
        // 就是对角线
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }
        // 考虑每个位置的依赖
        // N -1的位置已经填过了
        // 从下向上填写，从左向右
        for (int i = N - 2; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                int p1 = dp[i + 1][j];
                int p2 = dp[i + 1][j - 1];
                int p3 = dp[i][j - 1];
                int p4 = 0;
                if (chars[i] == chars[j]) {
                    p4 = 2 + dp[i + 1][j - 1];
                }
                dp[i][j] = Math.max(Math.max(p1, p2), Math.max(p3, p4));
            }
        }
        return dp[0][N - 1];
    }

}
