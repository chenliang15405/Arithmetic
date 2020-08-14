package com.arithmetic.features.arithmetic.interview.recursion;

import org.junit.Test;

/**
 * n!
 * n的阶乘
 *
 * @author alan.chen
 * @date 2020/7/26 4:43 PM
 */
public class Factorial {

    @Test
    public void test() {

        long factorial1 = getFactorial1(3);
        System.out.println(factorial1);

        long factorial2 = getFactorial2(3);

        System.out.println(factorial2);
    }

    // 递归
    public long getFactorial2(int n) {
        if(n == 1) {
            return 1L;
        }
        return (long)n * getFactorial2(n - 1);
    }

    // 非递归
    public long getFactorial1(int n) {
        long reuslt = 1;
        for (int i = 1; i <= n; i++) {
            reuslt *= i;
        }
        return reuslt;
    }

}
