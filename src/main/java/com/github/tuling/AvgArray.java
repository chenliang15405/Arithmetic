package com.github.tuling;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/2 19:12
 */
public class AvgArray {

    /**
     * 计算数组中指定长度的子数组的平均值的大小，找到子数组最大的平均值
     */

    @Test
    public void test() {
        int[] arr = new int[]{1, 5, 4, 3, 6};
        System.out.println(findMaxAverage(arr, 3));
    }

    /**
     * 如果平均值要求最大，在子数组的大小固定情况下，那么子数组的sum肯定最大
     *
     */
    private double findMaxAverage(int[] nums, int k) {
        int sum = 0;

        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }

        int max = sum;
        for (int i = k; i < nums.length; i++) {
            sum = sum - nums[i - k] + nums[i];
            max = Math.max(max, sum);
        }

        return 1.0 * sum / k;
    }



}
