package com.github.tuling;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/5/2 18:55
 */
public class MergeSortArray {

    /**
     * 合并有序数组
     * 两个有序数组，合并为一个数组，并且保持有序
     *
     */

    @Test
    public void test() {
        int[] arr1 = new int[]{1, 3, 5, 7, 9};
        int[] arr2 = new int[]{2, 4, 6, 8};

        System.out.println(Arrays.toString(mergeArray(arr1, arr2)));
    }


    /**
     * 双指针法合并有序数组
     *
     */
    private int[] mergeArray(int[] arr1, int[] arr2) {
        int[] newArr = new int[arr1.length + arr2.length];

        int j = 0;
        int x = 0;
        int y = 0;
        while (x < arr1.length && y < arr2.length) {
            if(arr1[x] <= arr2[y]) {
                newArr[j++] = arr1[x++];
            } else if(arr1[x] > arr2[y]) {
                newArr[j++] = arr2[y++];
            }
        }
        while (x < arr1.length) {
            newArr[j++] = arr1[x++];
        }
        while (y < arr2.length) {
            newArr[j++] = arr2[y++];
        }
        return newArr;
    }

}
