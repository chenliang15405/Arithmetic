package com.github.tuling;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/2 17:51
 */
public class MaxThreeNum {


    /**
     * 数组中3个数组成的最大乘积
     */

    @Test
    public void test() {
        int[] arr = new int[]{1,2,3,4,5,6};

        System.out.println(getMaxMin(arr));
    }

    /**
     * 线性扫描
     *
     * 当全部为正数，那么就是3个最大数的乘积之和，当全部是负数，也是3个最大数的乘积，当有负有正时，最大乘积为 两个最小数 和最大数的乘积
     */
    private int getMaxMin(int[] arr) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            // 遍历数组，找到最小值、次最小值和最大值、第二最大值、第三最大值
            int x = arr[i];

            if(x < min1) {
                // 赋值操作
                min2 = min1;
                min1 = x;
            } else if(x < min2) {
                min2 = x;
            }

            if(x > max1) {
                // 将最大值设置为max1，并将原本的最大值依次设置为第二大、第三大
                max3 = max2;
                max2 = max1;
                max1 = x;
            } else if(x > max2) {
                max3 = max2;
                max2 = x;
            } else if(x > max3) {
                max3 = x;
            }
        }

        return Math.max((max1 * max2 * max3), (min1 * min2 * max1));
    }
}
