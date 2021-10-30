package com.github.tuling;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/5/3 16:44
 */
public class Triangles {

    /**
     * 三角形的最大周长  贪心算法
     *  给定一个数组，计算数组中可以组成三角形并且三角形的最大周长是多少
     *
     */

    @Test
    public void test() {
        int[] arr = new int[]{3,6,2,3};

        System.out.println(largestPerimeter(arr));
    }

    /**
     *  三角形满足的条件： a + b > c && c > a &&  c >B
     *    如果需要周长最大，那么先对数组排序，然后从最大的开始寻找，如果不满足则向前移动一位
     *
     */
    private int largestPerimeter(int[] arr) {
        Arrays.sort(arr);

        int max = Integer.MIN_VALUE;
        for (int i = arr.length - 1; i >= 2; i--) {
            if(arr[i] < (arr[i-1] + arr[i-2])) {
               return arr[i] + arr[i-1] + arr[i-2];
            }
        }
        return 0;
    }


}
