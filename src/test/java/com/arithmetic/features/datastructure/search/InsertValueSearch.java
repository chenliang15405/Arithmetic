package com.arithmetic.features.datastructure.search;

import org.junit.Test;

/**
 * 插值查找  插值查找和二分查找基本一致，不同的是中位数的计算公式不同，插值查找可以根据查询值快速定位到查找的数据中
 *
 *  mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left])
 *
 * 插值查找和二分查找的使用场景：
 *    插值查找适用场景是均匀分布的数据，数据连续性比较强
 *    二分查找适用是不均匀分布的数据，数据的跳跃性比较大
 *
 * 前提：有序的数组
 *
 * @author alan.chen
 * @date 2020/5/28 10:35 PM
 */
public class InsertValueSearch {

    private static int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,14,15,16,17,18,19,20 };

    @Test
    public void test() {
        int key = 10;
        int index = insertValueSearch(arr, key, 0, arr.length - 1);

        System.out.println("索引为：" + index);
    }

    private int insertValueSearch(int[] arr, int key, int left, int right) {
        System.out.println("insertValueSearch");
        if(left > right) {
            return -1;
        }
        // 计算插值排序的mid值
        int mid = left + (right - left) * (key - arr[left]) / (arr[right] - arr[left]);

        if(key > arr[mid]) {
            return insertValueSearch(arr, key, mid + 1, right);
        } else if(key < arr[mid]) {
            return insertValueSearch(arr, key, left, mid - 1);
        }
        return mid;
    }

}
