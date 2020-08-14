package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 * 它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
 * 插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序）
 *
 * @author alan.chen
 * @date 2020/4/23 9:44 PM
 */
public class InsertionSort {

    private static int[] array = {3, 45, 58, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};


    /**
     * 插入排序，少使用一个变量
     */
    @Test
    public void insertionSort3() {
        for (int i = 1; i < array.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                // 每次和前面的一个数比较即可，j+1 就是i，如果多次比较则j是变化的，所以使用j+1表示，每次前面都已经是有序的了
                if(array[j] > array[j+1]) {
                    swap(array, j, j+1);
                }
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }
    
    /**
     * 自己写的插入排序
     */
    @Test
    public void insertionSort() {
        // 遍历所有的元素，相当于除了第一个元素之外认为是无序元素
        for (int i = 1; i < array.length; i++) {
            // 定义记录插入值的索引
            int currentIndex = i;
            // 这里遍历已经有序的元素列
            for (int j = i - 1; j >= 0; j--) {
                // 比较插入值和有序数列的数的大小
                if(array[currentIndex] < array[j]) {
                    // 交换位置
                    swap(array, currentIndex, j);
                    // 记录交换完位置的插入值的索引，继续向前比较
                    currentIndex = j;
                }
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }


    /**
     * 插入排序
     */
    @Test
    public void insertionSort2() {
        int insertNum;
        for (int i = 1; i < array.length; i++) {
            // 记录需要插入的值
            insertNum = array[i];

            // 定义有序数列的最后一位
            int j = i - 1;
            while (j >= 0 && insertNum < array[j]) {
                // 有序数列向后移动一位
                array[j+1] = array[j];
                // 向前开始遍历
                j--;
            }
            // 一个插入值比较遍历完成，将值插入到有序数列中
            array[j+1] = insertNum;
        }
        Arrays.stream(array).forEach(System.out::println);
    }

    @Test
    public void test() {
        for (int i = 0; i < array.length; i++) {
            int cur = i;
            for (int j = i - 1; j > 0 ; j--) {
                if(array[cur] < array[j]) {
                    swap(array, cur, j);
                    cur = j;
                }
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }



    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    @Test
    public void test3() {
        for (int i = 0; i < array.length; i++) {
            int cur = i;
            for (int j = i - 1; j > 0; j--) {
                if(array[cur] < array[j]) {
                    swap(array, cur, j);
                    cur = j;
                }
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }


}
