package com.arithmetic.features.datastructure.hanoit;

import org.junit.Test;

/**
 *
 * 分治算法 汉诺塔
 *
 * 将所有的盘看作是n-1个和n个，递归移动
 *
 * 时间复杂度: O(2 ^ n)
 *
 * @author alan.chen
 * @date 2020/6/6 6:11 PM
 */
public class HanoitTower {

    @Test
    public void test() {
        int n = 5;

        hanoitTower(n, "A", "B", "C");
    }


    /**
     * 递归移动
     *
     * @param n 盘子的个数
     * @param a a柱
     * @param b b柱
     * @param c c柱
     */
    private void hanoitTower(int n, String a, String b, String c) {
        if(n == 1) {
            // 如果只有1个，则直接移动
            System.out.println(a + "->" + c);
        } else {
            // 如果超过1个，则将n-1从A移动到B
            hanoitTower(n-1, a, c, b);
            // 上面的递归完，然后将n由a移动到c
            System.out.println(a + "->" + c);
            // n移动完，再将n-1由b移动到c
            hanoitTower(n-1, b, a, c);
        }
    }

}
