package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author alan.chen
 * @date 2020/4/22 10:57 PM
 */
public class SelectionSort {

    private static int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};


    /**
     * 初始版选择排序
     */
    @Test
    public void selectionSort() {
        for (int i = 0; i < array.length - 1; i++) {
            // 定义最小值
            int minValue = array[i];
            int index = 0;
            // 比较出来最小值
            for (int j = i + 1; j < array.length; j++) {
                if(minValue > array[j]) {
                    // 如果有比该值小的数，则重新赋值
                    minValue = array[j];
                    index = j;
                }
            }
            // 在一轮比较完成之后，如果当前最小值发生改变，则交换最小值到数据的前面
            if(array[i] != minValue) {
                swap(array, i, index);
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }


    /**
     * 优化选择排序，减少定义一个变量
     */
    @Test
    public void selectionSort2() {
        for (int i = 0; i < array.length - 1; i++) {
            // 定义最小值的索引
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if(array[minIndex] > array[j]) {
                    // 记录比较出来的最小值的索引
                    minIndex = j;
                }
            }
            // 判断是否有比假设还小的值
            if(i != minIndex) {
                swap(array, i, minIndex);
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }


    @Test
    public void test() {
        if(array == null || array.length < 2) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if(array[minIndex] > array[j]) {
                    minIndex = j;
                }
            }
            if(minIndex != i) {
                swap(array, minIndex, i);
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }

    private void swap(int[] array, int i, int index) {
        int temp = array[i];
        array[i] = array[index];
        array[index] = temp;
    }


    @Test
    public void test3() {
        for (int i = 0; i < array.length; i++) {
            int index = i;
            for (int j = i + 1; j < array.length; j++) {
                if(array[index] > array[j]) {
                    index = j;
                }
            }
            if(index != i) {
                swap(array, i, index);
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }


}
