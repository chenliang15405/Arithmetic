package com.github.左神算法.Manacher;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/8/7 19:26
 */
public class AddShortestEnd {

    /**
     * 给定一个字符串，只能在这个字符串的后面添加字符，可以添加任意字符，
     * 最少添加多少个字符可以让整个字符串构成一个回文串？
     *
     * 第二问：添加什么样的字符串可以变成一个回文串
     *
     * Manacher算法解题
     *
     */
    @Test
    public void test() {
        String str1 = "abcd123321";
        System.out.println(getNum(str1));
        System.out.println(shortestEnd(str1));
    }


    /**
     * 添加多少个回文串可以将原字符串构成一个回文串
     *   计算最长回文串包含最后一个的最长回文串长度，总的长度 - 回文串的长度  就是需要添加的长度
     *
     */
    public int getNum(String s) {
        if(s == null || s.length() == 0) return 0;

        // 构建manacher串
        char[] str = manacherString(s);
        int C = -1;
        // R表示当前最长的回文半径位置的下一个位置
        int R = -1;
        int maxString = 0;
        // 回文半径数组
        int[] pArr = new int[str.length];

        for (int i = 0; i < str.length; i++) {
            // 当前位置的回文半径的预处理
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                // 查看当前的回文半径之外的两边的数字是否相等
                if(str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            // 判断当前R是否可以扩张
            if(i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            // 判断是否已经扩张到最右边界，如果达到最右边界，那么当前的回文串就是最长的，因为是从左向右计算的回文串的长度
            // R 表示当前成功扩张位置的下一个，所以这里直接和长度比较
            if(R == str.length) {
                maxString = pArr[i];
                break;
            }
        }
        return s.length() - maxString;
    }


    /**
     * 第二问： 添加最短的字符是什么字符，可以让原字符串构整体构成回文串
     *
     */
    public String shortestEnd(String s) {
        if(s == null || s.length() == 0) return null;

        // 构建manacherString
        char[] str = manacherString(s);
        int[] pArr= new int[str.length];
        int C = -1;
        int R = -1;
        int maxContains = 0;

        for (int i = 0; i < str.length; i++) {
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if(str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if(i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            if(R == str.length) {
                // 记录当前最长的回文半径（其实就是原始字符串的回文子串的全部长度）
                maxContains = pArr[i];
                break;
            }
        }
        // 需要补充在最后的字符串
        char[] res = new char[s.length() - maxContains];
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = s.charAt(i);
        }
        return String.valueOf(res);
    }


    private char[] manacherString(String s) {
        char[] chars = s.toCharArray();
        char[] res = new char[s.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            // 奇数填充#，偶数保持原数字不变
            res[i] = (i & 1) != 0 ? '#' : chars[index++];
        }
        return res;
    }


}
