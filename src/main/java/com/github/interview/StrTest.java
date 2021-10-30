package com.github.interview;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/9/19 15:44
 */
public class StrTest {

    @Test
    public void test() {
//        String str = "abcdab";
//        String str = "abcdab";
//        String str = "aaa";
        String str = "aba";
//        String str = "a";
        System.out.println(convertString2(str));

    }

    /**
     *
     * 给定一个字符串s，请计算输出含有连续两个s作为子串的最短字符串
     *
     */
    private String convertString(String str) {
        if(str == null || str.length() == 0) {
            return null;
        }

        // 获取到最长前缀
        char[] chars = str.toCharArray();
        String s = "";
        for (int i = 0; i <= chars.length / 2; i++) {
            if(chars[i] != chars[chars.length - i - 1]) {
                break;
            }
            s += chars[i];
        }
        if(s.length() == 0) {
            str += str;
        } else {
            str += s;
        }

        return str;
    }

    private String convertString2(String str) {
        if(str == null || str.length() == 0) {
            return null;
        }

        // 获取到最长前缀
        int[] next = getNextArray(str.toCharArray());
        int l = next[str.length() - 1];
        if(l != -1) {
            str = str + str.substring(l);
        }

        return str;
    }


    private int[] getNextArray(char[] str2) {
        if(str2.length == 1) {
            return new int[]{-1};
        }
        // res[i] 记录的是i-1位置的最长前缀
        int[] next = new int[str2.length];
        next[0] = 0; // 规定第一位置为-1
        int cn = 0; // 即表示当前最长前缀的长度，也表示当前需要对比的字符是哪个
        int i = 1;
        while (i < next.length) {
            // 如果前一个位置和cn位置的字符相等，那么则表示当前的前缀长度+1
            if(str2[i] == str2[cn]) {
                next[i++] = ++cn;
            } else if(cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }
}
