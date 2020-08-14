package com.arithmetic.features.datastructure.search;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/**
 * @author alan.chen
 * @date 2020/6/12 2:58 PM
 */
public class BinarySearch3 {

    private static int[] arr = {1, 3, 22, 99, 99, 99, 99, 123, 123, 567, 999};


    @Test
    public void test() {
        int left = 0;
        int right = arr.length - 1;

        int key = 99;
        int val = findFirstVal(arr, key, left, right);
        System.out.println("找到元素下标为：" + val);

        int lastKey = findLastKey(arr, key, left, right);
        System.out.println("找到最后一个元素下标为：" + lastKey);


        int firstBiggerKey = findFirstBiggerKey(arr, key, left, right);
        System.out.println("找到第一个大于等于key的下标为：" + firstBiggerKey);

        int firstSmallerKey = findFirstSmallerKey(arr, key, left, right);
        System.out.println("找到第一个小于等于key的下标为：" + firstSmallerKey);

    }


    /**
     * 查找第一个值等于给定值的元素
     */
    private int findFirstVal(int[] arr, int key, int left, int right) {
        if(left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;

        if(arr[mid] > key) {
            return findFirstVal(arr, key, left, mid - 1);
        } else if(arr[mid] < key) {
            return findFirstVal(arr, key, mid + 1, right);
        } else {
            if(mid == 0 || arr[mid - 1] != key) {
                return mid;
            } else {
                return findFirstVal(arr, key, left, mid - 1);
            }
        }
    }


    @Test
    public void test1() {
        maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4});
    }

    public int maxSubArray(int[] nums) {
        int ans = nums[0];
        int sum = 0;
        for(int num: nums) {
            if(sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            ans = Math.max(ans, sum);
        }
        return ans;
    }

    /**
     * 查找最后一个值等于给定值的元素
     *
     * @return
     */
    private int findLastKey(int[] arr, int key, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if(arr[mid] > key) {
                right = mid - 1;
            } else if(arr[mid] < key) {
                left = mid + 1;
            } else {
                //int index = mid;
                //while (mid <= arr.length - 1) {
                //    if(arr[mid] == key) {
                //        index = mid;
                //    }
                //    mid++;
                //}
                //return index;
                if(mid == arr.length - 1 || arr[mid+1] != key) {
                    return mid;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }


    /**
     * 查找第一个大于等于给定值的元素
     *
     * @return
     */
    public int findFirstBiggerKey(int[] arr, int key, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if(arr[mid] >= key) {
                if(mid == 0 || arr[mid - 1] < key) {
                    return mid;
                }
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个小于等于给定值的元素
     * @return
     */
    public int findFirstSmallerKey(int[] arr, int key, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if(arr[mid] <= key) {
                if(mid == arr.length - 1 || arr[mid + 1] > key) {
                    return mid;
                }
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

}
