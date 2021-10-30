package com.arithmetic.features.arithmetic.interview.kmp;

import org.junit.Test;

/**
 * KMP算法
 *  解决问题：在一个字符串Str1中是否包含子串Str2，如果包含，则返回Str2在Str1中第一次出现位置的索引
 *
 *  例如： abc123dfe是否包含123df, 包含并且出现的位置索引为3
 *
 * @author tangsong
 * @date 2021/1/17 18:40
 */
public class KmpTest {


    @Test
    public void kmpMatchString() {
        String str1 = "bacbababadababacambabacaddababacasdsd";
        String str2 = "ababaca";

        int indexOf = getIndexOf(str1, str2);
        System.out.println(indexOf);
    }

    /**
     * KMP 匹配字符串
     *
     */
    private int getIndexOf(String s, String m) {
        if(s == null || m == null || s.length() <= 0 || m.length() <= 0 || s.length() < m.length()) {
            return -1;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = m.toCharArray();
        // 获取最长前缀和后缀的数组（next数组），构建str2字符串的最长前缀和后缀的数组，用于窗口的滑动
        int[] next = getNextArray(str2);
        int i = 0; // 定义两个指针
        int j = 0;
        while (i < str1.length && j < str2.length) {
            if(str1[i] == str2[j]) {
                // 如果当前的字符相等，则两个字符串的指针同时向后移动
                i++;
                j++;
            } else if(next[j] == -1) {
                // 如果当前的窗口（str2的指针已经移动到0索引的位置（构建next数组时规定0索引的前缀为-1），表示当前的字符已经匹配不到，那么str1的指针需要向后移动一个）
                i++;
            } else {
                // 如果str1的i指针的字符和str2的j指针的字符不相等，
                // 并且当前str2的指针没有到达到0索引，那么就行窗口的滑动，获取当前字符的最大的前缀长度，然后从该位置开始继续和str1的当前字符进行匹配
                // str1: abctabct...   str2:abctabck  如果当前的t和k没有匹配到，那么str2的k的最大前缀长度为3，所以j指针移动到3，然后和str1的t进行匹配，如果匹配到则继续，匹配不到则继续获取当前未知的最大前缀继续滑动窗口
                /**
                 * abctabct
                 *     abctabck
                 * 直接使用t和t进行匹配即可，因为前面匹配到的字符串都相等，并且计算的最大前缀长度，那么前缀和后缀是相等的，所以直接从前缀的长度开始匹配即可
                 */
                j = next[j];
            }
        }
        return j == str2.length ? i - j : -1;
    }

    /**
     * 构建next数组，计算每个字符的最长前后缀的长度
     */
    private int[] getNextArray(char[] str2) {
        if(str2.length <= 0) {
            return new int[]{};
        }
        int[] next = new int[str2.length];
        next[0] = -1; // 0位置的前缀规定为-1，因为0索引的位置没有前缀，前面没有元素，使用-1表示
        next[1] = 0; // 1位置的前缀规定为0，1位置也没有前缀, 因为当前的下标表示的是前面的前缀大小，并且不能取整体，所以是0
        int cn = 0; // 定义前缀的长度，也代表前缀的下一个字符在哪里
        int i = 2; // 目前在那个位置求next数组
        while (i < str2.length) {
            // i位置的前缀和 和i位置的前缀没有关系（i位置记录的是i-1的最大前缀）
            if(str2[i-1] == str2[cn]) {
                // 如果当前位置的前一个字符和定义的cn（之前计算的最长前缀的索引位置）相等，那么当前位置的最长前缀就是cn+1，因为cn已经是前一个字符的最长前缀了，下一个字符的最长前缀一定是前一个字符的前缀的长度+1, 不可能超过更多
                next[i++] = ++cn;
            } else if(cn > 0) {
                // 如果当前位置的前一个字符和已经统计的前缀长度位置的下一个字符不相等，那么就需要当前前缀位置的字符记录的前缀的下一个字符是否和当前字符相等，如果相等，就等于当前的前缀长度+1
                cn = next[cn];
            } else {
                // 如果cn（最长前缀的计算位置）已经移动到字符串的0索引位置，那么当前字符的最长前缀为0
                next[i++] = 0;
            }
        }
        return next;
    }


}
