package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 * 荷兰国旗问题，比num大的放在右边，比num小的放在左边，相等的放在中间
 *
 * @author alan.chen
 * @date 2020/5/8 4:13 PM
 */
public class NetherLandsFlag {

    private static int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    @Test
    public void test() {
        int L = 0;
        int R = array.length - 1;
        int num = 26;
        partition(array, L, R, num);

        Arrays.stream(array).forEach(System.out::println);
    }


    private int[] partition(int[] array, int L, int R, int num) {
        int left = L - 1;
        int right = R + 1;

        while (L >= 0 && L < right) {
            if(array[L] > num) {
                swap(array, L, --right);
            } else if(array[L] < num) {
                swap(array, L++, ++left);
            } else {
                L++;
            }
        }
        // 返回等于num的索引区域
        return new int[]{left + 1, right - 1};
    }

    private void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }


}
