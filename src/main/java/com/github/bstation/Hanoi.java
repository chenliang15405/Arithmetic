package com.github.bstation;

/**
 * @author tangsong
 * @date 2021/5/20 22:11
 */
public class Hanoi {

    /**
     * 汉诺塔
     *  将整体N个看做是 N-1 和 最底下的一个，当做整体进行移动
     */


    public void hanoi(int n, String A, String B, String C) {
        if(n == 1) {
            System.out.println(A + "->" + C);
        } else {
            hanoi(n-1, A, C, B);
            System.out.println(A + "->" + C);
            hanoi(n-1, B, A, C);
        }
    }
}
