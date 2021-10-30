package com.github.左神算法.KMP;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/8/7 17:35
 */
public class KMP {

    /**
     * 字符串匹配算法 KMP
     *
     * 解决问题：在一个字符串Str1中是否包含子串Str2，如果包含，则返回Str2在Str1中第一次出现位置的索引
     *
     *  例如： abc123dfe是否包含123df, 包含并且出现的位置索引为3
     *
     */
    @Test
    public void test() {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("str=" + str);
                System.out.println("match=" + match);
                System.out.println("index1=" + getIndexOf(str, match));
                System.out.println("index2=" + str.indexOf(match));
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


    /**
     * KMP算法
     *  1. 构建next数组，表示当前位置记录的最长前缀
     *  2. 对比每个位置的元素，并根据next数组进行移动（滑动窗口的移动）
     *
     *  O(N)
     *
     */
    public int getIndexOf(String s1, String s2) {
        if(s1 == null || s2 == null || s1.length() <= 0 || s1.length() < s2.length()) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        // 构建next数组，next[i]记录的是i-1为结尾的字符串的最长的前缀和后缀的长度
        int[] next = getNextArray(str2);

        int x = 0;
        int y = 0;

        while(x < str1.length && y < str2.length) {
            // 对比两个位置的元素是否相等
            if(str1[x] == str2[y]) {
                x++;
                y++;
            } else if(next[y] != -1) {
                // next数组中保存的是长度，所以这里获取到的y就是y记录的最长前缀的长度，那么下面对比的就是y位置和x位置是否相等，
                // 因为x-1和y-1的位置都已经相等了，那么最长前缀也是相等的，所以直接对比最长前缀的下一个元素和x元素对比
                y = next[y];
            } else {
                // 如果next数组中已经到第0个位置了，0位置规定next数组中为-1，那么将x移动到下一位匹配
                x++;
            }
        }

        return y ==  str2.length ? x - y : -1;
    }

    private int[] getNextArray(char[] str2) {
        if(str2.length == 1) {
            return new int[]{-1};
        }
        // res[i] 记录的是i-1位置的最长前缀
        int[] next = new int[str2.length];
        next[0] = -1; // 规定第一位置为-1
        next[1] = 0; // 第一个位置为0
        int cn = 0; // 即表示当前最长前缀的长度，也表示当前需要对比的字符是哪个
        int i = 2;
        while (i < next.length) {
            // 如果前一个位置和cn位置的字符相等，那么则表示当前的前缀长度+1
            if(str2[i - 1] == str2[cn]) {
                next[i++] = ++cn;
            } else if(cn > 0) {
                // 如果不相等，则对比cn位置对应的前缀的，因为始终需要以i-1的位置结尾，那么需要对比的前缀长度也是需要滑动比较
                cn = next[cn];
            } else {
                // 如果当前cn已经到第一个位置，都无法匹配，则i-1位置的最长前缀是0
                next[i++] = 0;
            }
        }
        return next;
    }



    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

}
