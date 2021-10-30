package com.github.左神算法.子数组问题;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/10/19 12:25
 */
public class LongestSumSubArrayLength {

    /**
     * 给定一个整数组成的无需数组arr，值可能正、可能负、可能0，给定一个整数值k
     * 找到arr的所有子数组里，哪个子数组的累加和等于k，并且长度最大的返回其长度
     *
     */
    @Test
    public void test() {
        int[] arr = generateArray(20);
        System.out.println(Arrays.toString(arr));

        System.out.println(maxLength(arr, 10));
    }


    /**
     * 使用前缀和 Map实现
     *
     *
     */
    public int maxLength(int[] arr, int k) {
        if(arr == null) {
            return -1;
        }
        // key: 前缀和
        // value : 0~value这个前缀和是最早出现key这个值的
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // 表示没有前缀和需要减，直接相加就等于k的时候，就直接使用-1作为前缀和的索引，用于计算最长的子数组长度

        int sum = 0;
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            // 判断map是否包含剩下值，如果包含则表示两个前缀和相减可以获取到k, 那么长度就是两个前缀和的索引的位置相减
            if(map.containsKey(sum - k)) {
                len = Math.max(len, i - map.get(sum - k));
            }
            // 保存到map
            if(!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }

        return len;
    }


    private int[] generateArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) ((Math.random() * 11) - 5); // 正负数都有，并且都是一半概率生成
        }
        return arr;
    }

}
