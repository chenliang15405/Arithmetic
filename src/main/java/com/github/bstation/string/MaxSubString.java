package com.github.bstation.string;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/5 19:35
 */
public class MaxSubString {

    /**
     * 给定一个只由小写字母(a-z）组成的字符串str, 返回其中最长无重复字符的子串长度
     *
     *  解法：
     *    1. 暴力解法
     *    2. 动态规划的方法
     *       思想：每次都求解以i结尾的字符串的最大长度
     */
    @Test
    public void test() {
        String str = "pwwkew";
        System.out.println(maxLength(str));

    }

    /**
     * 动态规划
     *  判断以i结尾的字符串的最长子串，那么就判断以i-1结尾的最长子串和i字符上次出现的位置对比，最短的就是以i结尾的最长子串
     *
     *  只能判断没有特殊字符
     */
    public int maxLength(String str) {
        char[] chars = str.toCharArray();

        // 使用dp数组，保存每个字符上次出现的位置
        // dp[0] a上次出现的位置
        // dp[1] b上次出现的位置
        // dp[2] c上次出现的位置
        // ...
        // dp[25] z上次出现的位置
        int[] dp = new int[26]; // 一共只有26个字母
        // 将每个字符的初始位置都设置为-1, 因为0是有效位置, 设置为-1，这样在计算长度的时候，就不用+1了，只需要减去-1就是子串的长度
        // 对于0索引位置的就不用+1，因为i - 0 = i， 就是长度
        for (int i = 0; i < 26; i++) {
            dp[i] = -1;
        }
        dp[chars[0] - 'a'] = 0; // 计算第一个字符上次出现的位置，chars[0] - 'a'可以计算到当前字符的索引位置（保证顺序），出现在第0位
        int max = 1;
        // 以i-1结尾的子串的最大长度
        int preLength = 1; // 初始都是1，因为下面的循环从1开始，0位置不计算了，已经计算过了

        for (int i = 1; i < chars.length; i++) {
            // 判断当前字符上次出现的位置到当前字符位置的长度 和 以i-1结尾的字符计算的最大长度，谁小取谁，因为小的话，代表以i结尾的子串的最大长度只能取到这里
            preLength = Math.min(i - dp[chars[i] - 'a'], preLength + 1); // preLength记录的是i-1结尾的长度，计算i的话需要+1
            // dp[chars[i] - 'a'] 表示当前字符上次出现的位置索引, i - dp[chars[i] - 'a']就表示当前字符上次出现的问题到当前位置的长度
            max = Math.max(preLength, max);
            // 更新dp数组中当前字符出现的位置
            dp[chars[i] - 'a'] = i;
        }
        return max;
    }


}
