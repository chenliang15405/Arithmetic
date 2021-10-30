package com.github.左神算法.对数器及数据量找规律;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/9/1 22:58
 */
public class AppleMinBags {

    /**
     *  小虎取买苹果，商店值提供两种类型的塑料袋，每种类型都有任意数量。
     *  1> 能装下6个苹果的袋子
     *  2> 能装下8个苹果的袋子
     *  小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，它要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满，给定一个正整数N,
     *  返回至少使用多少袋子。如果N无法让使用德玛介个袋子必须装满，返回-1
     *
     */
    @Test
    public void test() {
        int n = 16;
        for (int i = 1; i < 1000; i++) {
            System.out.println(i + "=" +minBag(i));
        }


    }

    /**
     * 暴力方法
     *
     */
    public int minBag(int n) {
        // 先使用8的袋子最多的开始计算
        int bag8 = n / 8;
        int rest = n - bag8 * 8;

        while(bag8 >= 0) {
            if(rest % 6 == 0) {
                return bag8 + rest / 6;
            }
            rest += 8;
            bag8--;
        }

        return -1;
    }

    /**
     * 优化
     *  O(1)
     *
     */
    public int minBag2(int n) {
        // 如果是奇数，直接返回-1
        if((n & 1) != 0) {
            return -1;
        }

        if(n < 18) {
            return n == 6 || n == 8 ? 1 : n == 12 || n ==  14 || n == 16 ? 2 : -1;
        }

        // 下面的计算式由暴力递归的数据规模看出来的规律
        return (n - 18) / 8 + 3;
    }


}
