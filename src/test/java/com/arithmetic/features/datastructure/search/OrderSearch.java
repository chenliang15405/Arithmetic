package com.arithmetic.features.datastructure.search;

import org.junit.Test;


/**
 * 线性查找
 *
 * @author alan.chen
 * @date 2020/5/28 10:16 PM
 */
public class OrderSearch {

    private static int[] arr = {1, 3, 22, 66, 99, 100, 123, 567, 999};
    private static int[] arr2 = {1, 3, 22, 66, 99, 99, 123, 567, 999};

    @Test
    public void test() {
        int key = 99;
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if(key == arr[i]) {
                index = i;
            }
        }
        System.out.println("索引为：" + index);
    }

}
