package com.arithmetic.features.arithmetic.interview.array;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定义一个目标值K，求一个数组中最长子数组的sum等于目标值的子数组的长度
 *
 *
 * @author tangsong
 * @date 2021/2/27 21:42
 */
public class LongestSumSubArrayLength {

    @Test
    public void test() {
        int[] arr = generateArray(20);  
        System.out.println(Arrays.toString(arr));

        System.out.println(maxLength(arr, 10));
    }


    /**
     * 思路：
     *   1. 使用map记录数组每个元素当前累加和sum和位置
     *   2. 使用当前累加和sum - k，计算得到的值如果在map中存在，那么该值对应的索引到当前的索引值就是可以累加和得到k的子数组
     *   3. 每次记录当前的子数组的最大长度
     *
     *  原理: 如果一个数组有5个元素，前2个元素相加得到5，第4个元素相加等于12，要找的k=7, 因为12-7=5，所以第2个元素到第4个元素构成的子数组相加一定等于7
     *     对所有这样的子数组的长度进行比较，就可以获取到最大长度的子数组
     *
     *   如果从0开始到某个元素的累加和等于sum，目标值为k, 如果想要获取到以这个元素结尾的最长累加等于目标值的子数组，那么一定要有个从0开始累加到某个元素
     *   的累加和等于sum-k的这样的一个数组，那么从某个元素+1到当前元素的子数组就是符合的数组，获取到所有这样的数组，比较长度就可以获取到最长子数组
     *
     */
    public static int maxLength(int[] arr, int k) {
        if(arr == null) {
            return -1;
        }
        // map中记录每个元素和之前元素的sum 和 对应的索引
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // 重要

        int sum = 0;
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if(map.containsKey(sum - k)) {
                len = Math.max(i - map.get(sum - k), len);
            }
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
