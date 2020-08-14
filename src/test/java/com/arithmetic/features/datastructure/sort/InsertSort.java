package com.arithmetic.features.datastructure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author alan.chen
 * @date 2020/5/27 10:27 PM
 */
public class InsertSort {

    private static int[] arr = {50, 3, 22, 99, 90, 567, 999, 66, 123, 9};

    @Test
    public void test() {
        for (int i = 1; i < arr.length; i++) {
            int index = i;
            for (int j = i - 1; j >= 0; j--) {
                if(arr[index] < arr[j]) {
                    swap(arr, index, j);
                    index = j;
                }
            }
        }
        print();
    }

    @Test
    public void test2() {
        for (int i = 1; i < arr.length; i++) {
            int index = i;
            int cur = arr[index];
            for (int j = i - 1; j >= 0; j--) {
                if(cur < arr[j]) {
                    arr[index] = arr[j];
                    index = j;
                }
            }
            if(index != i) {
                arr[index] = cur;
            }
        }
        print();
    }


    public static void print() {
        Arrays.stream(arr).forEach(System.out::println);
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
