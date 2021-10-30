package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/24 23:17
 */
public class ConvertToLetterString {

    /**
     * 规定1和A对应，2和B对应，3和C对应... 26和Z对应，那么一个数字字符串比如"111" 就可以转化为：“AAA”,"KA"和“AK”
     * 给定一个只有数字字符组成的字符串str，返回由多少种转化结果
     */

    @Test
    public void test() {
        String str = "111";

        System.out.println(way1(str));
        System.out.println(way2(str));
    }

    /**
     * 暴力递归
     */
    public int way1(String str) {
        return process(str.toCharArray(), 0);
    }

    /**
     * 从index开始，可以转换为字符的最多的方法数是多少
     */
    private int process(char[] str, int index) {
        if (index == str.length) {
            // 如果已经到最大的长度，超过最后一个了，那么认为之前选择的是有效的方法数，返回1
            return 1;
        }
        if (str[index] == '0') {
            // 如果字符串中有0，那么表示无效解，当然2个不会遇到这种情况，除非2个计算的时候，第一个字符是0，那么也不行
            return 0;
        }

        // 计算一个字符一个字符转换的方法数, 都可以转换完成才算做是一种方法数，当前可以转换并不算是一种方法数，后面的不确定是否可以转换
        int way = process(str, index + 1); // 表示当前的字符可以转换，计算后面的字符是否可以转换
        // 判断两个字符的时候是否可以转换
        if (index + 1 < str.length && (str[index] - '0') * 10 + (str[index + 1] - '0') <= 26) {
            way += process(str, index + 2); // 如果当前两个字符可以转化，那么计算后续的是否可以转换，以及后续可以转换的方法数
        }
        return way;
    }

    /**
     * 动态规划
     */
    public int way2(String str) {
        int N = str.length();
        char[] chars = str.toCharArray();
        int[] dp = new int[N + 1];
        dp[N] = 1;
        // 从暴力递归可以知道每个位置都依赖下一个位置，所以从右向左计算
        for (int i = N - 1; i >= 0; i--) {
            int way = dp[i + 1];
            if (i + 1 < N && (chars[i] - '0') * 10 + (chars[i + 1] - '0') <= 26) {
                way += dp[i + 2];
            }
            dp[i] = way;
        }
        return dp[0];
    }


}
