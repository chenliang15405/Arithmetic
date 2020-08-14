package com.arithmetic.features.datastructure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author alan.chen
 * @date 2020/5/27 10:26 PM
 */
public class BubleSort {


    private static int[] arr = {50, 3, 22, 99, 90, 567, 999, 66, 123};

    @Test
    public void test() {
        boolean flag = true;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if(arr[j] > arr[j+1]) {
                    flag  = false;
                    swap(arr, j, j + 1);
                }
            }
            if(flag) {
                break;
            }
        }
        Arrays.stream(arr).forEach(System.out::println);
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
