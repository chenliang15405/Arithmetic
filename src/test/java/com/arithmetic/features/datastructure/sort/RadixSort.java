package com.arithmetic.features.datastructure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 *  基数排序
 *
 *  以空间换时间，稳定排序算法，这个实现不能排序负数，需要改进才可以
 *
 * @author alan.chen
 * @date 2020/5/27 11:30 PM
 */
public class RadixSort {

    private static int[] arr = {50, 3, 22, 99, 90, 567, 999, 66, 123, 9};


    @Test
    public void test() {

        radixSort(arr);

        print();
    }

    private void radixSort(int[] arr) {
        // 创建二维数组，表示桶，用来保存每次排序的数据
        int[][] bucket = new int[10][arr.length]; // 行表示0-9的每位数保存的桶位置，arr.length表示该桶保存的最大数据，可能会有浪费，不过这样不会溢出

        // 该数组用来记录每个桶中的有几个数
        int[] countOfElement = new int[10];

        int maxNum =  arr[0];
        for (int i = 1; i < arr.length; i++) {
            if(maxNum < arr[i]) {
                maxNum = arr[i];
            }
        }
        // 数字的最大位数
        int maxNumLength = String.valueOf(maxNum).length();

        // 数据的位数决定了循环多少次，可以完成排序
        for (int i = 0, n = 1; i < maxNumLength; i++, n*=10) {
            // 循环数据，保存到指定的桶
            for (int j = 0; j < arr.length; j++) {
                // 计算当前位数的桶是多少
                int digitOfElement = arr[j] / n % 10;
                // 将数据保存到对应的桶, 并保存到第几个桶的第几个位置，这个位置用另一个数组记录
                bucket[digitOfElement][countOfElement[digitOfElement]] = arr[j];

                // 记录指定桶保存的数据量
                countOfElement[digitOfElement]++;
            }

            // 定义原数组中的填充索引
            int index = 0;
            // 每次将数据保存一次到桶中，然后顺序的开始将数据从桶中填充到原数组
            for (int k = 0; k < countOfElement.length; k++) {
                if(countOfElement[k] != 0) {
                    // 如果记录的指定的桶中有数据，则从桶中取数据并填充到原数组
                    for (int l = 0; l < countOfElement[k]; l++) {
                        arr[index++] = bucket[k][l];
                    }
                }
                // 将记录数组中的桶的数据个数清空，为了下一次大循环可以继续使用
                countOfElement[k] = 0;
            }
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
