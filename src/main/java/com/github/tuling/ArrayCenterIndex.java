package com.github.tuling;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/5/2 15:54
 */
public class ArrayCenterIndex {


    /**
     * 找到数组的中心下标， 数组的中心下标： 下标的左边的值之和 = 下标的右边的值之和
     */
    @Test
    public void test() {
        int[] arr = new int[]{1, 3, 5, 2, 2};
        System.out.println(findCenterIndex(arr));
    }


    private int findCenterIndex(int[] arr) {
        if(arr == null || arr.length == 0) {
            return -1;
        }
        // 因为是左边数据之和等于右边数据之和，那么求出总的和，逐个从左开始向右减就可以
        int sum = Arrays.stream(arr).sum();
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            // 向让total增加，这样就算到了中心点，那么两边都是增加了中心点，相当于都没有增加中心点
            total += arr[i];
            if(total == sum) {
                return i;
            }
            sum -= arr[i];
        }
        return -1;
    }


}
