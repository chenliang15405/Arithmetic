package com.arithmetic.features.arithmetic.interview;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;


/**
 * 给定一个数组，求如果排序之后，相邻两数的最大差值，要求最大时间复杂度O(N)，且要求不能使用非基于比较的排序
 *
 * @author alan.chen
 * @date 2020/6/7 11:28 AM
 */
public class MaxGap {


    @Test
    public void test() {
        int[] arr = {3, 91, 20, 63, 55, 35, 10, 30};

        int i = maxGap(arr);

        System.out.println(i);
    }


    @Test
    public void test1() {
        int[] arr = {3, 91, 20, 63, 55, 35, 10, 30};

        Arrays.sort(arr); // O(n * logn)

        int maxGap = 0;
        for (int i = 1; i < arr.length; i++) {
            maxGap = Math.max(maxGap, arr[i] - arr[i - 1]); // O(n)
        }
        System.out.println(maxGap);
    }

    @Test
    public void test2() {
        int[] arr = {3, 91, 20, 63, 55, 35, 10, 30};

        int i = maxGap1(arr);
        System.out.println(i);
    }


    private int maxGap1(int[] arr) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
            min = Math.min(arr[i], min);
        }
        int length = arr.length;
        int[] maxs = new int[length + 1];
        int[] mins = new int[length + 1];
        boolean[] hashNum = new boolean[length + 1];

        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            int bucket = bucket1(num, length, min, max);
            maxs[bucket] = hashNum[bucket] ? Math.max(num, maxs[bucket]) : num;
            mins[bucket] = hashNum[bucket] ? Math.min(num, mins[bucket]) : num;
            hashNum[bucket] = true;
        }

        int result = 0;
        int largest = maxs[0];

        for (int i = 1; i <= length; i++) {
            if(hashNum[i]) {
                result = Math.max(result, mins[i] - largest);
                largest = maxs[i];
            }
        }
        return result;
    }

    private static int bucket1(int num, int length, int min, int max) {
        return (num - min) * length / (max - min);
    }



    private int maxGap(int[] arr) {
        int len = arr.length;

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        // 计算最大值和最小值
        for (int i = 0; i < len; i++) {
            max = Math.max(arr[i], max);
            min = Math.min(arr[i], min);
        }
        // 定义三个数组，相当于桶
        int[] mins = new int[len + 1];
        int[] maxs = new int[len + 1];
        boolean[] hashNum = new boolean[len + 1];

        int bid = 0;
        for (int i = 0; i < len; i++) {
            int num = arr[i];
            // 获取当前数据在哪个桶中
            bid = bucket(num, len, min, max);
            // 判断当前桶中是否有数据，如果有，则将数据分别放入最大值和最小值数组中
            maxs[bid] = hashNum[bid] ? Math.max(num, maxs[bid]) : num;
            mins[bid] = hashNum[bid] ? Math.min(num, mins[bid]) : num;
            // 将当前桶设置为有值
            hashNum[bid] = true;
        }

        // 从第一个桶中获取到最大值，然后和后面非空桶中的最小值比较出最大的差值
        // 相同的桶中不比较，因为相同的桶中的数据不会出现最大差值，只有再相邻的桶中才会有最大差值
        int res = 0;
        int largest = maxs[0];
        for (int i = 1; i <= len; i++) {
            // 如果有值则进行判断
            if(hashNum[i]) {
                // 比较最大差值
                res = Math.max(res, mins[i] - largest);
                largest = maxs[i];
            }
        }

        return res;
    }

    public static int bucket(long num, long len, long min, long max) {
        return (int)((num - min) * len / (max - min));
    }

}
