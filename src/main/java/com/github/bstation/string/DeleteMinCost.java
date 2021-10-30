package com.github.bstation.string;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/5/30 18:43
 */
public class DeleteMinCost {


    /**
     * 给定两个字符串s1和s2，问s2最少删除多少个字符可以成为s1的子串？
     * 比如s1="abcde"，s2="axbc"
     * 返回1， s2删除"x"就是s1的子串
     *
     * 子串是需要保证连续的，并且按照顺序
     * 子序列是可以不连续的顺序可以不同
     *
     */

    @Test
    public void test() {
        String s1 = "abcde";
        String s2 = "axbc";

        System.out.println(minCost(s1, s2));
    }


    /**
     * 获取到s2的所有子序列，然后根据子序列的长度进行降序排序，然后和s1进行匹配，如果可以通过kmp匹配到，那么就表示当前的长度就是
     * 删除指定的字符得到的最长的子串（因为根据长度进行排序的子序列，所以这个删除最少的元素）
     *
     */
    public int minCost(String s1, String s2) {
        char[] str2 = s2.toCharArray();
        List<String> list = new ArrayList<>();
        // 获取s2字符串的所有子序列
        process(str2, 0, "", list);

        // 对所有的子序列进行排序，根据字符串的长度进行排序(从大到小)
        list.sort((o1, o2) -> o2.length() - o1.length());
        for (String s : list) {
            // 判断当前子序列是否是str1的子串，如果是子串，那么就是最长的一个子串，可以通过删除str2中的字符实现，则就是最终答案
            // 需要通过KMP进行匹配，也可以通过indexOf，底层几乎是相同的
            if(s1.indexOf(s) != -1) {
                return s2.length() - s.length();
            }
        }
        // 如果没有匹配到，则表示需要删除所有的s2
        return s2.length();
    }

    /**
     * 递归获取字符串的子序列
     * 并将子序列收集到list中
     *
     * 递归中，将尝试模型分为需要当前字符和不需要当前字符，则分为两种可能，并加上base case即可
     * 主要是参数不是特别好确定
     */
    private void process(char[] str, int index, String path, List<String> list) {
        if(str.length == index) {
            list.add(path);
            return;
        }
        // 如果子序列中不要当前字符
        process(str, index + 1, path, list);
        // 如果子序列中需要当前的字符，递归
        process(str, index + 1, path + str[index], list);
    }

}
