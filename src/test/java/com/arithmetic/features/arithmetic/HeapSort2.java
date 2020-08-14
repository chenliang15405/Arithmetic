package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 * 工程中的排序：如果是基本数据类型使用 快速排序（不保证稳定性）
 *             如果是引用数据类型使用 归并排序（保证稳定性）
 *             如果样本量小（小于60）使用插入排序（插入排序尽管是O(N^2)，但是常数项低）
 *
 *
 * @author alan.chen
 * @date 2020/5/10 12:36 PM
 */
public class HeapSort2 {

    private static int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    @Test
    public void heapSortTest() {
        heapSort(array);

        Arrays.stream(array).forEach(System.out::println);
    }

    /**
     * 堆排序
     */
    private void heapSort(int[] array) {
        if(array == null || array.length < 2) {
            return;
        }
        // 构建大根堆
        for (int i = 0; i < array.length; i++) {
            int index = i;
            while (array[index] > array[(index - 1) / 2]) {
                swap(array, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }
        // 根据需要排序的长度进行排序
        int heapSize = array.length;
        // 大根堆排序
        while(heapSize > 0) {
            swap(array, 0, --heapSize);
            heapify(array, 0, heapSize);
        }

    }

    // 调整大根堆
    private void heapify(int[] array, int i, int heapSize) {
        int left = i * 2 + 1; // 当前节点的左孩子
        while (left < heapSize) {
            int right = i * 2 + 2; // 当前节点的右孩子
            int largest; // 左孩子、右孩子中最大的索引
            if(right < heapSize && array[right] > array[left]) {
                largest = right;
            } else {
                largest = left;
            }
            // 比较父节点和最大孩子节点的值
            if(array[i] > array[largest]) {
               break;
            }
            // 如果父节点比孩子节点小，则交换并继续向下比较
            swap(array, i, largest);
            i = largest;
            left = i * 2 + 1;
        }
    }


    // 堆排序
    @Test
    public void test() {
        heapSort2(array);

        Arrays.stream(array).forEach(System.out::println);
    }

    private void heapSort2(int[] array) {
        if(array == null || array.length < 2) {
            throw new IllegalArgumentException();
        }
        // 构建大顶堆
        for (int i = 0; i < array.length; i++) {
            heapInsert(array, i);
        }
        // 排序
        int heapSize = array.length;
        while(heapSize > 0) {
            swap(array, 0, --heapSize);
            heapify2(array, 0, heapSize);
        }

    }

    private void heapify2(int[] array, int index, int heapSize) {
        int left = index * 2 + 1;
        while(left < heapSize) {
            int largest = left + 1 < heapSize && array[left+1] > array[left] ? left + 1 : left;
            largest = array[index] > array[largest] ? index : largest;

            if(largest == index) {
                break;
            }
            swap(array, largest, index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    private void heapInsert(int[] array, int i) {
        while(array[i] > array[(i-1) / 2]) {
            swap(array, i, (i-1) / 2);
            i = (i-1) / 2;
        }
    }

    private void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }


    @Test
    public void test3() {
        // 调整为大顶堆
        for (int i = 0; i < array.length; i++) {
            int index = i;
            while(array[index] > array[(index - 1) / 2]) {
                swap(array, index, (index - 1 ) / 2);
                index = (index - 1) / 2;
            }
        }
        // 排序
        int heapSize = array.length;
        while(heapSize > 0) {
            swap(array, 0, --heapSize);
            heapify3(array, 0, heapSize);
        }
        Arrays.stream(array).forEach(System.out::println);
    }

    private void heapify3(int[] array, int i, int heapSize) {
        int left = i * 2 + 1;
        while(left < heapSize) {
            int largest = left + 1 < heapSize && array[left+1] > array[left] ? left + 1 : left;
            largest = array[i] > array[largest] ? i : largest;

            if(i == largest) {
                break;
            }
            swap(array, i, largest);
            i = largest;
            left = i * 2 + 1;
        }

    }

}
