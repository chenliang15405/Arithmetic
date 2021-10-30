package com.github.tuling;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/5/2 15:44
 */
public class RemoveDuplicates {

    /**
     * 原地删除数组中的重复项
     */

    @Test
    public void test() {
        int[] arr = new int[]{1, 3, 3, 5, 2, 2};

        removeDuplicates(arr);

        System.out.println(Arrays.toString(arr));

    }


    /**
     * 双指针法删除重复项
     *
     */
    private void removeDuplicates(int[] arr) {
        if(arr == null || arr.length == 0) {
            return;
        }
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            if(arr[index] != arr[i]) {
                // 相当于将自身赋值给自身
                arr[++index] = arr[i];
            }
        }
    }


}
