package com.github.左神算法.暴力递归;

/**
 * @author tangsong
 * @date 2021/6/20 19:38
 */
public class Hanoi {

    /**
     * 汉诺塔问题
     *
     */

    public void hanoi(int n) {
        if(n > 0) {
            process(n, "A", "B", "C");
        }
    }

    private void process(int n, String A, String B, String C) {
        if(n == 1) {
            System.out.println(A + "->" + C);
        } else {
            // n-1指的是非第一个的上面的n-1个
            process(n - 1, A, C, B);
            System.out.println(A + "->" + C);
            process(n - 1, B, A , C);
        }
    }


}
