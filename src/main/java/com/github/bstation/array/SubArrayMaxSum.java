package com.github.bstation.array;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/30 16:44
 */
public class SubArrayMaxSum {

    /**
     * 给定一个数组arr，返回子数组的最大累加和
     *
     */


    /**
     * 数组中有正有负，并且还需要保证是子数组（即连续的数据）
     *
     *  遍历数组，做累加和，当前的累加和为负数时，则将累加和重置为0，否则继续累加，每次比较最大值
     *  因为最大的累加和，不可能是加上负数的数字，如果加上负数都是最大的，那么去掉负数这一段应该更大
     *  当全是负数的时候，也支持，因为每次都需要比较一个最大值，最终比较出来的肯定是最大的一个负数作为最大的累加和
     *
     */

    @Test
    public void test() {
        int[] arr = {1, -2, 3, 5, -2, 6, -1};

        System.out.println(getMax(arr));
    }


    public int getMax(int[] arr) {
        if(arr == null || arr.length == 0) {
            return -1;
        }

        // 遍历数组
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int i = 0; i < arr.length; i++) {
            cur += arr[i];
            max = Math.max(max, cur);
            if(cur < 0) {
                // 再下次进行累加之前，判断当前的累加和是否等于0，如果等于0则直接重置为0，相当于重新开始累加，因为和负数相加的累加和肯定不是最大的
                cur = 0;
            }
        }
        return max;
    }

}
