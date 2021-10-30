package com.github.左神算法.暴力递归;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tangsong
 * @date 2021/6/20 20:02
 */
public class PrintAllPermutations {

    /**
     * 打印一个字符串的全部排列
     *
     */

    public List<String> print(String str) {
        List<String> result = new ArrayList<>();
        process(str.toCharArray(), 0, result);
        return null;
    }

    private void process(char[] chars, int index, List<String> result) {
        if(index == chars.length) {
            result.add(String.valueOf(chars));
        } else {
            // 将当前位置的字符和后面的每个位置的字符交换位置，然后递归，不用和前面的交换，只需要和后面的交换即可，前面的会和当前的交换
            for (int i = index; i < chars.length; i++) {
                swap(chars, index, i);
                process(chars, index+1, result);
                // 在一次交换完成之后，需要将当前位置还原，这样才能进行下一次交换，只是index的位置会改变，字符数组原始应该是不变的
                swap(chars, index, i);
            }
        }

    }

    /**
     * 打印不重复的字符串的全排列
     *
     */
    public List<String> print2(String str) {
        List<String> result = new ArrayList<>();
        process2(str.toCharArray(), 0, result);
        return null;
    }

    private void process2(char[] chars, int index, List<String> result) {
        if(index == chars.length) {
            result.add(String.valueOf(chars));
        } else {
            // 将当前位置的字符和后面的每个位置的字符交换位置，然后递归，不用和前面的交换，只需要和后面的交换即可，前面的会和当前的交换

            // 需要去重，则当前位置不能有重复的字符，针对当前的字符进行交换的时候，如果有重复则不继续
            boolean[] visited = new boolean[256];
            for (int i = index; i < chars.length; i++) {
                // 只有当前字符没有试过，才进行交换
                if(!visited[chars[i]]) {
                    visited[chars[i]] = true;
                    swap(chars, index, i);
                    process2(chars, index+1, result);
                    // 在一次交换完成之后，需要将当前位置还原，这样才能进行下一次交换，只是index的位置会改变，字符数组原始应该是不变的
                    swap(chars, index, i);
                }
            }
        }

    }


    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

}
