package com.github.左神算法.子数组问题;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/10/19 12:08
 */
public class LongestSumSubArrayLengthInPositiveArray {

    /**
     * 给定一个正整数组成的无序数组arr，给定一个正整数值k，找到arr的所有子数组里
     * 哪个子数组的累加和等于k，并且是长度最大的，返回其长度
     *
     * 1. 滑动窗口
     * 2. map实现的前缀和 {@link com.github.左神算法.子数组问题.LongestSumSubArrayLength}
     *
     *  滑动窗口问题无法解决数组中包含负数的情况，通过Map实现的前缀和可以解决
     *
     */
    @Test
    public void test() {
        int len = 20;
        int k = 15;
        int[] arr = generatePositiveArray(len);
        printArray(arr);
//        int[] arr = {9,7,7,6,2,7,3,1,1,3};
        System.out.println("k=" + k);
        System.out.println(getMaxLength(arr, k));
        System.out.println(getMaxLength1(arr, k));
    }

    /**
     * 滑动窗口
     *
     */
    public int getMaxLength(int[] arr, int k) {
        if(arr == null || arr.length == 0 || k < 0) {
            return -1;
        }
        int sum = arr[0]; // 初始化为第一个值, 为了好比较
        int L = 0;
        int R = 0;
        int len = 0;

        while(R < arr.length) {
            if(sum == k) {
                len =  Math.max(len, R - L + 1);
                // 减去当前最左边的元素，当前的元素已经收集过了，这样就额可以然后继续向右滑动
                sum -= arr[L++];
            } else if(sum < k) {
                // 如果小于，说明当前还可以继续向右扩，所以向右继续移动
                R++;
                if(R == arr.length) {
                    break;
                }
                sum += arr[R];
            } else {
                // 如果大于k，则减去最左边窗口的值
                sum -= arr[L++];
            }
        }

        return len;
    }

    public int getMaxLength1(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int L = 0;
        int R = 0;
        int sum = arr[0];
        int len = 0;
        while (R < arr.length) {
            if (sum == k) {
                len = Math.max(len, R - L + 1);
                sum -= arr[L++];
            } else if (sum < k) {
                R++;
                if (R == arr.length) {
                    break;
                }
                sum += arr[R];
            } else {
                sum -= arr[L++];
            }
        }
        return len;
    }


    public static int[] generatePositiveArray(int size) {
        int[] result = new int[size];
        for (int i = 0; i != size; i++) {
            result[i] = (int) (Math.random() * 10) + 1;
        }
        return result;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
