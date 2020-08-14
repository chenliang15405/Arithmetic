package com.arithmetic.features.datastructure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author alan.chen
 * @date 2020/5/27 10:52 PM
 */
public class MergeSort {

    private static int[] arr = {50, 3, 22, 99, 90, 567, 999, 66, 123, 9};


    @Test
    public void test() {
        sort2(arr, 0, arr.length - 1);

        print();
    }


    private void sort(int[] arr, int left, int right) {
        if(left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        sort(arr, left, mid);
        sort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private void sort2(int[] arr, int left, int right) {
        if(left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        sort2(arr, left, mid);
        sort2(arr, mid + 1, right);
        merge2(arr, left, mid, right);
    }

    private void merge2(int[] arr, int left, int mid, int right) {
        int[] temp = new int[arr.length];
        int p1 = left;
        int p2 = mid + 1;
        int t = 0;

        while(p1 <= mid && p2 <= right) {
            if(arr[p1] <= arr[p2]) {
                temp[t++] = arr[p1++];
            } else {
                temp[t++] = arr[p2++];
            }
        }

        while(p1 <= mid) {
            temp[t++] = arr[p1++];
        }
        while(p2 <= right) {
            temp[t++] = arr[p2++];
        }

        t = 0;
        while(left <= right) {
            arr[left++] = temp[t++];
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[arr.length];
        int p1 = left;
        int p2 = mid + 1;
        int t = 0;

        while (p1 <= mid && p2 <= right) {
            if(arr[p1] <= arr[p2]) {
                temp[t++] = arr[p1++];
            } else {
                temp[t++] = arr[p2++];
            }
        }

        while (p1 <= mid) {
            temp[t++] = arr[p1++];
        }
        while(p2 <= right) {
            temp[t++] = arr[p2++];
        }

        t = 0;
        while(left <= right) {
            arr[left++] = temp[t++];
        }
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
