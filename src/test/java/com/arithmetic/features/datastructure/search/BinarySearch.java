package com.arithmetic.features.datastructure.search;

import org.junit.Test;

/**
 * 二分查找，这个查找只能查找一个数据的指定索引，如果是多个相等的数据则无法查找到
 *
 * 前提是：有序数组
 *
 * @author alan.chen
 * @date 2020/5/28 10:16 PM
 */
public class BinarySearch {


    private static int[] arr = {1, 3, 22, 66, 99, 100, 123, 567, 999};
    private static int[] arr2 = {1, 3, 22, 66, 99, 99, 123, 567, 999};

    private static int[] arr3 = {5, 6, 7, 8, 9, 1, 2, 3};


    /**
     * 二分查找
     */
    @Test
    public void test() {
        int key = 999;

        int index = binarySearch(arr, key, 0, arr.length - 1);

        System.out.println("索引为：" + index);
    }


    /**
     * 二分查找 两个升序数组
     */
    @Test
    public void test2() {
        int index = binarySearchArr(arr3, 0, arr3.length - 1, 2);
        System.out.println("两个升序数组的key : " + index);
    }

    private int binarySearchArr(int[] arr, int left, int right, int key) {
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if(arr[mid] == key) {
                return mid;
            }
            // 都要大于等于，否则找不到
            if(arr[left] <= arr[mid]) {
                if(arr[left] <= key && arr[mid] >= key) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if(arr[mid] < key && arr[right] >= key) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }


    /**
     * 二分查找，没有相同元素情况
     * @param arr
     * @param key
     * @param left
     * @param right
     * @return
     */
    private int binarySearch(int[] arr, int key, int left, int right) {
        if(left > right) {
            return -1;
        }
        // 这种可能会内存溢出
        //int mid = (left + right) / 2;
        int mid = left + (right - left) / 2; // 防止溢出，如果left、right都超过int最大值的一半，则两个和会超过int最大值溢出


        if(key > arr[mid]) {
            return binarySearch(arr, key, mid + 1, right);
        } else if(key < arr[mid]) {
            return binarySearch(arr, key, left, mid - 1);
        }
        return mid;
    }



}
