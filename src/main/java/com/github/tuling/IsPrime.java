package com.github.tuling;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/2 14:48
 */
public class IsPrime {

    /**
     * 判断一个数字是否为素数，素数：只能被1和本身整除的数就是素数，0 和 1 不算素数
     *
     * 给定一个数字n，计算2 —— n 范围内的素数的个数
     *
     */

    @Test
    public void test1() {
        System.out.println(bf(100));

        System.out.println(eratosthenes(100));
    }


    /**
     * 暴力解法
     */
    private int bf(int n) {
        int count = 0;
        for (int i = 2; i < n; i++) {
            // 判断当前的数是否为素数
            if(isPrime(i)) {
                count++;
            }
        }
        return count;
    }


    /**
     * 埃氏筛选
     */
    public int eratosthenes(int n) {
        // 定义数组表示数字是否为素数
        boolean[] isPrime = new boolean[n]; // false表示素数，true表示合数

        int count = 0;
        for (int i = 2; i < n; i++) {
            // 判断当前的数是否为素数，因为从小的数开始判断的，当遇到大的数的时候，要不已经被标识了，如果没有标识则表示当前的数是素数，因为这个如果有素数，那么
            // 这个因子一定比当前数小
            if(!isPrime[i]) {
                count++;
                // 如果不是素数，则表示是合数，那么将当前的这个数相关因子全部标识为合数
                // 其中，i * i 是通过 2 * i 表示过来，因为在计算每个i的过程中有多个重复计算的优化，j+=i 表示 i的每次底层，2*2、2*3。。。
                for (int j = i * i; j < n; j+=i) {
                    isPrime[j] = true;
                }
            }
        }
        return count;
    }


    /**
     * 判断一个数是否为素数，能否被除 1 和自身整除的数
     */
    private boolean isPrime(int x) {
        for (int i = 2; i < x; i++) {
            if(x % i == 0) {
                return false;
            }
        }
        return true;
    }

}
