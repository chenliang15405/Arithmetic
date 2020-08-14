package com.arithmetic.features.issue;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 计算n个数中的前K个最大/最小的元素
 *
 * @author alan.chen
 * @date 2020/6/2 9:38 AM
 */
public class TopK {

    private static int[] arr = {50, 3, 22, 99, 90, 567, 999, 66, 123};

    private static int[] array = {1, 3, 22, 99, 190, 1567, 1999, 6666};


    /**
     * 前3个最大的数
     */
    @Test
    public void test() {
        int n = 3;
        // 建立n个数的小顶堆
        for (int i = 0; i < n; i++) {
            int index = i;
            while (arr[index] < arr[(index-1)/2]) {
                swap(arr, index, (index-1)/2);
                index = (index - 1) /2;
            }
        }
        // 遍历其他元素和堆顶比较
        for (int i = n; i < arr.length; i++) {
            if(arr[0] < arr[i]) {
                swap(arr, 0, i);
                // 调整小根堆
                heapify(arr, 0, n);
            }
        }
        // 前n个最大的数
        for (int i = 0; i < n; i++) {
            System.out.println(arr[i]);
        }
    }


    /**
     * 前3个最大的数——优先级队列
     */
    @Test
    public void test2() {
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        for (int i = 0; i < arr.length; i++) {
            heap.offer(arr[i]);
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(heap.poll());
        }
    }

    @Test
    public void test6() {
        int n = 3;
        for (int i = 0; i < n; i++) {
            int index = i;
            while(arr[index] < arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index -  1) / 2;
            }
        }

        for (int i = n; i < arr.length; i++) {
            if(arr[i] > arr[0]) {
                swap(arr, 0, i);
                heapify3(arr, 0, n);
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println(arr[i]);
        }
    }

    private void heapify3(int[] arr, int i, int n) {
        int left = i * 2 + 1;
        while(left < n) {
            int smallest = left + 1 < n && arr[left + 1] < arr[left] ? left + 1 : left;
            smallest = arr[smallest] < arr[i] ? smallest : i;
            if(smallest == i) {
                break;
            }
            swap(arr, i, smallest);
            i = smallest;
            left = i * 2 + 1;
        }
    }

    /**
     * 二分查找
     */
    @Test
    public void test5() {
        int key = 22;

        int left = 0;
        int right = array.length - 1;

        while(left <= right) {
            int mid = left + (right - left) / 2;

           if(array[mid] > key) {
                right = mid - 1;
            } else if(array[mid] < key) {
                left = mid + 1;
            } else {
               System.out.println("mid=" + mid);
               return;
            }
        }
        System.out.println("mid = -1");


        int i = binarySearch(array, left, right, key);
        System.out.println(i);
    }

    public int binarySearch(int[] arr, int left, int right, int key) {
        if(left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;
        if(arr[mid] > key) {
            return binarySearch(arr, left, mid - 1, key);
        } else if(arr[mid] < key) {
            return binarySearch(arr, mid + 1, right, key);
        }
        return mid;
    }


    /**
     * 前3个最大的数——手动实现堆
     */
    @Test
    public void test3() {
        int n = 3;
        for (int i = 0; i < n; i++) {
            heapInsert(arr, i);
        }

        for (int i = n; i < arr.length; i++) {
            if(arr[i] > arr[0]) {
                swap(arr, i, 0);
                heapify2(arr, 0, n);
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println(arr[i]);
        }
    }

    private void heapify2(int[] arr, int index, int size) {
        int left = index * 2 + 1;
        while(left < size) {
            int smallest = left + 1 < size && arr[left+1] < arr[left] ? left + 1 : left;
            smallest = arr[smallest] < arr[index] ? smallest : index;
            if(smallest == index) {
                return;
            }
            swap(arr, smallest, index);
            index = smallest;
            left = index * 2 + 1;
        }
    }

    private void heapInsert(int[] arr, int i) {
        while(arr[i] < arr[(i - 1) / 2]) {
            swap(arr, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }


    // 调整小根堆
    private void heapify(int[] array, int i, int heapSize) {
        int left = i * 2 + 1; // 当前节点的左孩子
        while (left < heapSize) {
            int right = i * 2 + 2; // 当前节点的右孩子
            int lowest; // 左孩子、右孩子中最小的索引
            if(right < heapSize && array[right] < array[left]) {
                lowest = right;
            } else {
                lowest = left;
            }
            // 比较父节点和最小孩子节点的值
            if(array[i] < array[lowest]) {
                break;
            }
            // 如果父节点比孩子节点小，则交换并继续向下比较
            swap(array, i, lowest);
            i = lowest;
            left = i * 2 + 1;
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
