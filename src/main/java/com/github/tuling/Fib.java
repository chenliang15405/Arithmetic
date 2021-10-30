package com.github.tuling;

import org.junit.Test;


/**
 * @author tangsong
 * @date 2021/5/3 0:15
 */
public class Fib {

    /**
     * 斐波那契数列
     *
     * 0 1 1 2 3 5 8...
     *
     */

    @Test
    public void test() {
        System.out.println(fib1(8));

        System.out.println(fib2(8));
    }


    /**
     * 暴力递归
     *
     */
    private int fib1(int n) {
        if(n == 0) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }
        return fib1(n - 1) + fib1(n - 2);
    }


    /**
     * 记忆优化
     *   因为在递归的过程中，有多个重复计算的子问题，所以可以通过备忘录进行优化
     *
     */
    private int fib2(int n) {
        // 因为数组的索引从0开始，而n是可以被取到的，所以n+1的长度，虽然可能0索引不使用
        int[] arr = new int[n + 1];

        return recursion(n, arr);
    }

    /**
     * 带有备忘录的递归
     */
    private int recursion(int n, int[] arr) {
        if(n == 0) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }
        // 0已经在前面都过滤了，并且是fib数列，递增的，后面的不会为0
        // 数组的初始值都是0，如果不等于则表示可以从备忘录中找到数据
        if(arr[n] != 0) {
            return arr[n];
        }
        int cur = recursion(n - 1, arr) + recursion(n - 2, arr);
        arr[n] = cur;
        return cur;
    }


}
