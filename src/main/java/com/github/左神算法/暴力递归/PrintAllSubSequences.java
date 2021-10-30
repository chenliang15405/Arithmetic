package com.github.左神算法.暴力递归;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tangsong
 * @date 2021/6/20 19:46
 */
public class PrintAllSubSequences {

    /**
     * 打印一个字符串的全部子序列
     *
     */
    public List<String> print(String str) {
        String path = "";
        List<String> ans = new ArrayList<>();
        process(str.toCharArray(), 0, path, ans);
        return ans;
    }

    private void process(char[] chars, int index, String path, List<String> ans) {
        if(index == chars.length) {
            ans.add(path);
        }
        // 要当前位置或者不要当前位置
        process(chars, index+1, path, ans);
        process(chars, index+1, path + chars[index], ans);

    }

    /**
     *  打印一个字符串的全部子序列, 要求不出现重复字面值的子序列
     *
     */
    public List<String> print2(String str) {
        String path = "";
        // set去重
        Set<String> ans = new HashSet<>();
        process2(str.toCharArray(), 0, path, ans);
        return new ArrayList<>(ans);
    }

    private void process2(char[] chars, int index, String path, Set<String> ans) {
        if(index == chars.length) {
            ans.add(path);
        }
        // 要当前位置或者不要当前位置
        process2(chars, index+1, path, ans);
        process2(chars, index+1, path + chars[index], ans);

    }
}
