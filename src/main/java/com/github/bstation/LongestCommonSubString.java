package com.github.bstation;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/25 23:19
 */
public class LongestCommonSubString {

    /**
     * 最长公共子序列问题
     *   str1: abc12kd3
     *   str2: klso1ll2lo2
     *
     *   最长公众组序列是123，子序列可以是不连续的
     *
     *   使用两种方式获取最长公共子序列
     *   1. 暴力递归（尝试模型：一个参数做行 一个参数做列模型） -> 一个样本做行，一个样本做列的模型总是从最后一个元素开始选择进行尝试 -> 这种问题一般会给两个样本，这种问题就思考是否可以使用样本对应模型解题
     *   2. 动态规划（二维表）
     *
     */

    @Test
    public void test() {
        String str1 = "psnw";
        String str2 = "vozsh";

        System.out.println(lcs(str1, str2));

        System.out.println(dp(str1, str2));
    }


    /**
     * 暴力递归
     *  返回最大长度
     */
    public int lcs(String str1, String str2) {
        char[] char1 = str1.toCharArray();
        char[] char2 = str2.toCharArray();
        int N = char1.length;
        int M = char2.length;

        return process(char1, char2, N-1, M-1);
    }

    /**
     * 为什么这里直接可以设置参数，分别是两个字符串的长度，因为这种模型就是一个参数做行 一个参数做列的模型，所以就是需要传递2个参数进行递归
     *
     * 1. 考虑base case
     * 2. 基本判定及递归方式
     *
     *  （这种一个参数做行和一个参数做列的模型，根据经验，从头开始或者从结尾开始）
     * 最长子序列的长度从最后一位开始向前判断，当到达第一位的时候，分为4中情况：
     *   1. 不以两个指针指向的位置结尾
     *   2. 以i1结尾，不以i2结尾
     *   3. 以i2结尾，不以i1结尾
     *   4. 以i1和i2结尾，那么最长子序列就是不以i1和i2结尾的长度+1（第一种情况+1）
     */
    private int process(char[] str1, char[] str2, int i1, int i2) {
        if(i1 == 0 && i2 == 0) {
            return str1[i1] == str2[i2] ? 1 : 0;
        }
        // 潜规则就是 i2 != 0，因为到这里的话，那么i2肯定是不等于0
        if(i1 == 0) {
            // str1[0..0] str2[0..i2-1]，如果当前的字符不相等，那么判断str2在0~i2-1之前的字符和str1[i1]是否相等
            return ((str1[i1] == str2[i2]) || process(str1, str2, i1, i2 - 1) == 1) ? 1 : 0;
        }
        if(i2 == 0) {
            return ((str1[i1] == str2[i2]) || process(str1, str2, i1 -1, i2) == 1) ? 1 : 0;
        }

        // 不以i1和i2结尾的最长子序列的最长的长度
        int p1 = process(str1, str2, i1 - 1, i2 - 1);
        //  最长子序列以i1结尾，不以i2结尾的情况
        //  （默认process函数是黑盒的话，那么根据参数的含义，传递参数，那么返回的就是这个指定两个字符串及位置的最长的子序列）,
        //  所以无需管递归执行的过程，只要base case正确，那么根据宏观的条件去调用，就可以返回对应的值，然后根据对应的值进行计算即可，
        //  大问题和小问题的计算过程基本一致（只要在拆问题的时候，拆出来重复子问题即可）
        int p2 = process(str1, str2, i1, i2 - 1);
        //  最长子序列以i2结尾，不以i1结尾的情况
        int p3 = process(str1, str2, i1 - 1, i2);
        int p4 = 0;
        if(str1[i1] == str2[i2]) {
            // 如果当前的值相等，那么p4情况就是p1+1，p1表示i1和i2之前相等的子序列的多少个
            p4 = p1 + 1;
        }

        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }


    /**
     * 动态规划
     *   最长子序列的长度
     *
     * 根据暴力递归来源
     *
     */
    public int dp(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = s1.length();
        int M = s2.length();
        // 创建二维数组，一个参数做行，一个参数做列
        int[][] dp = new int[N][M];
        // 初始化位置的数据
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        // 初始化第一行, 从1开始，0已经初始化过了
        for (int i = 1; i < M; i++) {
            // 如果和str1的第一个字符相等，则为1，否则在之前的字符串中有相等的字符，也算作是子序列
            dp[0][i] = str1[0] == str2[i] ? 1 : dp[0][i-1];
        }
        // 初始化第一列, 从1开始，0已经初始化过了
        for (int i = 1; i < N; i++) {
            // 如果和str2的第一个字符相等，则为1，否则在之前的字符串中有相等的字符，也算作是子序列
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i-1][0];
        }

        // 计算过程
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {

                // 计算 第二种情况和第三种情况的最大值(参考暴力递归的4种情况)
                dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                // 判断是否有第四种情况
                if(str1[i] == str2[j]) {
                    // 如果有第四种情况，则需要判断目前的最大子序列的长度和第四种情况计算的子序列的长度的最大值
                    // 第四种情况的最大值就是第一种情况的最大值+1，所以就是dp[i-1][j-1]+1
                    /**
                     * 之所以不计算第一种情况，因为每个格子的值就是[i-1][j]和[i][j-1]的最大值，第一种情况的格子是当前格子的
                     * 左上角，第一种情况的格子肯定是小于当前格子的上面的格子或者当前格子的左边的格子，因为每个格子都是计算两个格子
                     * 的最大值，所以，第一种情况必定小于第二种情况和第三种情况的值，所以不需要进行比较，只需要在第四种情况时计算需要用到
                     */
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j-1] + 1);
                }
            }
        }
        return dp[N-1][M-1];
    }


}
