package com.arithmetic.features.issue.interview;

import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * @author alan.chen
 * @date 2020/6/17 2:38 PM
 */
public class BinarySearchInterview {


    /**
     * 有序数组可能会在某个点旋转，搜索指定的值并返回索引
     */
    @Test
    public void q1() {
        int[] arr = {4,5,6,7,8, 9, 0,1,2,3};
        //int[] arr = {1, 2, 3, 4, 5, 6, 7,  8};
        int key = 0;
        // 没有重复值情况
        int index = q1(arr, key);
        System.out.println("查找到的索引：" + index);

        int[] arr2 = {4,5,6,7,8, 9, 0,1,2,3};
        int key2 = 8;
        int index2 = q2(arr2, key2);
        System.out.println("查找到的索引：" + index2);

        //System.out.println(search(arr, 7));
    }

    private int q1(int[] arr, int key) {
        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if(key == arr[mid]) {
                return mid;
            }

            // 如果前半段是递增
            if(arr[start] <= arr[mid]) {
                if(key > arr[start] && key < arr[mid]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                // 如果后半段是递增的
                if(key > arr[mid] && key < arr[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }



    public int q2(int[] arr, int key) {
        int left = 0;
        int right = arr.length - 1;

        while(left < right) {
            int mid = (left + right) / 2;

            if(key == arr[mid]) {
                return mid;
            }

            if(arr[left] <= arr[mid]) {
                if(arr[left] < key && key < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if(arr[mid] < key && key < arr[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

        }
        return -1;
    }





    // 非递归二分查找
    public int search(int[] arr, int key) {
        int start = 0;
        int end = arr.length - 1;

        while (start < end) {
            int mid = (start + end) / 2;

            if(key > arr[mid]) {
                start = mid + 1;
            } else if(key < arr[mid]) {
                end = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
