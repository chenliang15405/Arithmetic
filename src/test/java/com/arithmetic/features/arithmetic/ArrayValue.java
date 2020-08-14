package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.*;

/**
 * @author alan.chen
 * @date 2020/5/3 10:38 AM
 */
public class ArrayValue {

    private static int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};


    /**
     * 数组的最大值和最小值
     *
     * 两数之和
     *
     */
    @Test
    public void test1() {
        int[] nums = {2, 7, 11, 15};
        int key = 22;
        int[] ints = computed(nums, key);
        System.out.println(Arrays.toString(ints));

        int[] ints1 = computed1(nums, key);
        System.out.println(Arrays.toString(ints1));

    }

    public int[] computed(int[] nums, int key) {
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if ((x + nums[j] == key)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public int[] computed1(int[] nums, int key) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int target = key - nums[i];
            if(map.containsKey(target)) {
                return new int[]{map.get(target), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

}
