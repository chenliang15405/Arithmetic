package com.arithmetic.features.datastructure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 升序排列 大顶堆
 *
 * 降序排列 小顶堆
 *
 *
 * @author alan.chen
 * @date 2020/5/30 10:58 PM
 */
public class HeapSort {

    private static int[] arr = {50, 3, 22, 99, 90, 567, 999, 66, 123};


    @Test
    public void test() {
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }

        int heapSize = arr.length;
        while (heapSize > 0) {
            swap(arr, 0, --heapSize);
            heapify(arr, 0, heapSize);
        }
        print();
    }

    private void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[index] > arr[largest] ? index : largest;
            if(largest == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    private void heapInsert(int[] arr, int i) {
        while (arr[i] > arr[(i-1) /2]) {
            swap(arr, i, (i-1) / 2);
            i = (i - 1) / 2;
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
