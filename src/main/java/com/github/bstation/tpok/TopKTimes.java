package com.github.bstation.tpok;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author tangsong
 * @date 2021/5/23 17:04
 */
public class TopKTimes {

    /**
     * 给定一个字符串组成的数组String[] strs，给定一个正数K, 返回词频最大的前K个字符串，假设结果是唯一的
     *
     */

    @Test
    public void test() {
        String[] arr = {"abc", "bcd", "abc", "ccd", "abc", "bcd", "oqi", "kkd"};

        printTopKAndRank(arr, 3);
    }

    /**
     * topK问题
     *
     */
    public void printTopKAndRank(String[] arr, int topK) {
        if(arr == null || arr.length == 0) {
            return;
        }
        // 创建哈希表
        Map<String, Integer> map = new HashMap<>();
        for (String str : arr) {
            map.put(str, map.getOrDefault(str, 0) + 1);
        }

        // 创建小根堆, 比较器是从小到大
        PriorityQueue<String> queue = new PriorityQueue<>(topK, (o1, o2) -> {
            return map.get(o1) - map.get(o2);
        });

        for (String key : map.keySet()) {
            if(queue.size() < topK) {
                queue.add(key);
            } else {
                if(map.get(queue.peek()) < map.get(key)) {
                    queue.poll();
                    queue.add(key);
                }
            }
        }
        System.out.println(queue.size());
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

}
