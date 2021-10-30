package com.github.左神算法.哈希函数;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/8/21 19:37
 */
public class TestHash {

    @Test
    public void test() {
        int a = 1;
        a = a | 1 << 3;

        int status = ((a & (1 << 2)) == 0) ? 0 : 1;

        System.out.println(status);
    }

    @Test
    public void test2() {
        int[] arr = new int[10];

        int i = 100;

        arr[i / 32] = arr[i / 32] | (1 << (i % 32));

        int status = (arr[i / 32] & (1 << (i % 32))) == 0 ? 0 : 1;

        System.out.println(status);
    }

}
