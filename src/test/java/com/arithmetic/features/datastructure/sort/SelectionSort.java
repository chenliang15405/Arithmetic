package com.arithmetic.features.datastructure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author alan.chen
 * @date 2020/5/27 10:34 PM
 */
public class SelectionSort {

    private static int[] arr = {50, 3, 22, 99, 90, 567, 999, 66, 123, 9};


    @Test
    public void test() {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if(arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            if(minIndex != i) {
                swap(arr, minIndex, i);
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
