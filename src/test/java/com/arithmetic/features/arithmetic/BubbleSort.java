package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 *
 * 最佳情况：T(n) = O(n) 最差情况：T(n) = O(n2) 平均情况：T(n) = O(n2)
 *
 * @author alan.chen
 * @date 2020/4/22 10:24 PM
 */
public class BubbleSort {

    private static int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    /**
     * 初始冒泡排序
     */
    @Test
    public void bubbleSort1() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j);
                }
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }

    /**
     * 优化冒泡排序，每轮少比较已排好序的元素
     */
    @Test
    public void bubleSort2() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j);
                }
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }


    /**
     * 优化冒泡排序，对已经有序的数据，减少比较次数
     */
    @Test
    public void bubleSort3() {
        int[] arr = {1, 3, 9, 20, 50, 61};

        boolean isSorted = true;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    isSorted = false;
                    swap(arr, j);
                }
            }
            if (isSorted) {
                System.out.println("该数组本是有序数组");
                break;
            }
        }
        Arrays.stream(arr).forEach(System.out::println);
    }

    @Test
    public void test6() {
        boolean flag = true;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if(array[j] > array[j + 1]) {
                    flag = false;
                    swap(array, j);
                }
            }
            if(flag) {
                break;
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }


    @Test
    public void test1() {
        boolean flag = true;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if(array[j] > array[j+1]) {
                    flag = false;
                    swap(array, j);
                }
            }
            if(flag) {
                break;
            }
        }
        Arrays.stream(array).forEach(System.out::println);
    }


    private void swap(int[] array, int j) {
        int temp = array[j];
        array[j] = array[j + 1];
        array[j + 1] = temp;
    }


    /**
     * 使用对数器验证算法正确性
     */
    @Test
    public void verifyMethod() {
        int size = 10;
        int value = 100;
        int testTime = 500000;

        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(size, value);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);

            bubleSortM(arr);
            rightSort(arr2);
            if(!isEqual(arr, arr2)) {
                System.out.println("校验失败");
                System.out.println(Arrays.toString(arr3));
                break;
            }
        }

        System.out.println("校验通过");
    }


    // 冒泡排序 对数器
    public static int[] generateRandomArray(int size, int value) {
        // 生成随机长度额数组
        int[] randomArray = new int[(int)((size +1) * Math.random())];
        // 填入随机数据
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = (int)((value + 1) * Math.random()) - (int)(value * Math.random());
        }
        return randomArray;
    }

    // 绝对正确的方法
    public static void rightSort(int[] arr) {
        Arrays.sort(arr);
    }

    // 需要验证的方法
    public void bubleSortM(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j);
                }
            }
        }
    }

    // 复制数组
    public static int[] copyArray(int[] arr) {
        if(arr == null) {
            return null;
        }
        int[] newArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        return newArr;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null) ) {
            return false;
        }
        if(arr1 == null && arr2 == null) {
            return true;
        }
        if(arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if(arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }


}