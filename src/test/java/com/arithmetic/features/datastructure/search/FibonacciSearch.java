package com.arithmetic.features.datastructure.search;

import org.junit.Test;

import java.util.Arrays;

/**
 * 斐波那契数列查找（黄金分割查找）
 *
 * 构造斐波那契数列，然后根据需要查询数组的长度，选择指定的斐波那契数列值
 *
 * mid = left + F[k - 1] - 1
 *
 * @author alan.chen
 * @date 2020/5/28 11:06 PM
 */
public class FibonacciSearch {

    private static int[] arr = {1, 3, 22, 66, 99, 100, 123, 567, 999};

    int maxSize = 10;

    @Test
    public void test() {
        int key = 66;

        int index = fibSearch(arr, key);
        System.out.println("索引为：" + index);
    }

    private int fibSearch(int[] arr, int key) {
        int left = 0;
        int right = arr.length - 1;
        int k = 0;

        // 构建斐波那契数列
        int[] fib = fib(maxSize);
        // 获取到斐波那契分割数值的下标
        while (right > fib[k] - 1) {
            k++;
        }

        // 将该数组的长度设置为斐波那契数列k的长度
        int[] temp = Arrays.copyOf(arr, fib[k]);
        // 因为需要构造有序数组，所以需要将多余的数填充为最大数
        for (int i = right + 1; i < temp.length; i++) {
            temp[i] = temp[right];
        }

        // 非递归的方式寻找该Key
        while(left <= right) {
            // 获取斐波那契数列的中位数值（黄金分割值）
            int mid = left + (fib[k] - 1) - 1;
            if(key > temp[mid]) {
                left = mid + 1;
                //  全部元素 = 前面的元素 + 后边元素
                //  f[k] = f[k-1] + f[k-2]
                // 这个是后面的元素，所以k--
                k--;
            } else if(key < temp[mid]) {
                right = mid - 1;
                // 这个是前分割的数组，使用k-=2，但是这两个倒过来好像也不影响
                k -= 2;
            } else {
                // 确定返回的是哪个下标，因为数据被填充了新的元素
                if(mid <= right) {
                    return mid;
                } else {
                    return right;
                }
            }
        }
        return -1;
    }

    private int[] fib(int maxSize) {
        int[] fib = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            fib[i] = fibNum(i);
        }
        return fib;
    }

    private int fibNum(int i) {
        if(i == 0 || i == 1) {
            return 1;
        }
        return fibNum(i - 1) + fibNum(i - 2);
    }


}
