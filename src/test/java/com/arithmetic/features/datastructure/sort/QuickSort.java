package com.arithmetic.features.datastructure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author alan.chen
 * @date 2020/5/27 10:52 PM
 */
public class QuickSort {

    private static int[] arr = {50, 3, 22, 99, 90, 567, 999, 66, 123, 9};


    @Test
    public void test() {
        quickSort(arr, 0, arr.length - 1);

        print();
    }

    private void quickSort(int[] arr, int l, int r) {
        if(l > r) {
            return;
        }
        int[] p = partition(arr, l, r);
        quickSort(arr, l, p[0] - 1);
        quickSort(arr, p[1] + 1, r);
    }

    private int[] partition(int[] arr, int l, int r) {
        int less = l - 1;
        int more = r;

        while(l < more) {
            if(arr[l] > arr[r]) {
                swap(arr, l, --more);
            } else if(arr[l] < arr[r]){
                swap(arr, l++, ++less);
            } else {
                l++;
            }
        }
        swap(arr, more, r);
        return new int[]{less + 1, more};
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
