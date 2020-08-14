package com.arithmetic.features.datastructure.search;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 二分查找，可以查询单个数据的指定索引，也可以查找多个相同数据的索引
 *
 * 前提是：有序数组
 *
 * @author alan.chen
 * @date 2020/5/28 10:16 PM
 */
public class BinarySearch2 {

    private static int[] arr = {1, 3, 22, 99, 99, 99, 123, 567, 999};

    @Test
    public void test() {
        int key = 99;

        List<Integer> list = new ArrayList<>();
        List<Integer> indexList = binarySearch(arr, key, 0, arr.length - 1, list);

        indexList.stream().forEach(System.out::println);
    }


    /**
     * 二分查找数据，如果有多个相同元素，则返回多个值
     * @param arr
     * @param key
     * @param left
     * @param right
     * @param list
     * @return
     */
    private List<Integer> binarySearch(int[] arr, int key, int left, int right, List<Integer> list) {
        if(left > right) {
            return list;
        }
        // 这种可能会内存溢出
        //int mid = (left + right) / 2;
        int mid = left + (right - left) / 2; // 防止溢出，如果left、right都超过int最大值的一半，则两个和会超过int最大值溢出


        if(key > arr[mid]) {
            return binarySearch(arr, key, mid + 1, right, list);
        } else if(key < arr[mid]) {
            return binarySearch(arr, key, left, mid - 1, list);
        } else {
            // 如果找到相等的数据，则向左和向右继续查看是否有匹配数据，因为是有序数组，所以直接向右或者向左即可
            // 将当前数据假如到list中
            list.add(mid);

            int more = mid + 1;
            int less = mid - 1;
            // 判断右边是否有相等的数据
            while (more <= right && key == arr[more]) {
                list.add(more++);
            }
            // 判断左边是否有相等的数据
            while (less >= left && key == arr[less]) {
                list.add(less--);
            }
        }
        return list;
    }


    public int binarySearch2(int[] arr, int key) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if(arr[mid] > key) {
                right = mid - 1;
            } else if(arr[mid] < key) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public int binarySearch3(int[] arr, int key, int left, int right) {
        if(left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if(arr[mid] > key) {
            return binarySearch3(arr, key, left, mid - 1);
        } else if(arr[mid] < key){
            return binarySearch3(arr, key, mid + 1, right);
        }
        return mid;
    }

}
