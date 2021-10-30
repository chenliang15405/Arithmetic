package com.github.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/9/20 15:26
 */
public class ContractArray {

    /**
     * 给你几个数组，每个数组抽一个数，把所有组合情况列一下
     *
     * DFS + 回溯
     *
     */

    List<List<Integer>> ans = new ArrayList<>();
    @Test
    public void test() {
        int[][] res = {{1,2,3}, {1, 5, 6}, {7, 9}};

        process(res, 0, new ArrayList<>());

        for (List<Integer> item : ans) {
            System.out.println(item);
        }
    }

    private void process(int[][] res, int index, ArrayList<Integer> list) {
        if(index == res.length) {
            ans.add(new ArrayList<>(list));
            return;
        }

        // 每个数组从0开始，小于每个数组的长度的大小
        for (int i = 0; i < res[index].length; i++) {
            list.add(res[index][i]);
            process(res, index + 1, list);
            list.remove(list.size() - 1);
        }
    }

}
