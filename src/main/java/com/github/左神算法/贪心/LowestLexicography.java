package com.github.左神算法.贪心;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author tangsong
 * @date 2021/6/14 18:07
 */
public class LowestLexicography {

    /**
     *  给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，返回所有可能的拼接结果中，字典序最小的结果
     *
     */

    /**
     * 贪心策略： a+b的字典序一定是小于b+a，所以按照此策略对字符串进行排序，并拼接即可
     *
     */
    public String lowestString(String[] strs) {
        if(strs == null || strs.length <= 0) {
            return "";
        }
        // 给定贪心策略，直接对数组按照指定的策略及进行排序，然后拼接
        Arrays.sort(strs, new StringComparator());
        String res = "";
        for (int i = 0; i < strs.length; i++) {
            res += strs[i];
        }
        return res;
    }

    public static class StringComparator implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo((b + a));
        }
    }
}
