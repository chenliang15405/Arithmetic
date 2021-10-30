package com.github.bstation.other;

/**
 * @author tangsong
 * @date 2021/6/6 18:54
 */
public class RandomP {

    /**
     * 给定一个随机函数f， 以p概率返回0，以1-p概率返回1，这是唯一可以使用的随机函数，如何实现等概率返回0或者1
     *
     * 解法：
     *  1. 随机两次，那么可能会有四种结果：
     *    0 0   概率为 p^2
     *    1 1   概率为 (1-p)^2
     *    1 0   概率为(1-p)*p
     *    0 1   概率为p*(1-p)
     *
     *  最后两种的概率是相同的，所以可以规定 1 0 返回1  0 1返回0即可
     */

    public static class RandomBox {
        private final double p;

        public RandomBox(double p) {
            this.p = p;
        }

        public int random() {
            return Math.random() < p ? 0 : 1;
        }
    }

    /**
     * 随机两次，根据相同概率的可能的不同数来表示0或者1
     *
     * @param randomBox 系统的随机函数
     * @return  等概率返回0或1
     */
    public int random(RandomBox randomBox) {

        int num1;
        int num2;
        while (true) {
            num1 = randomBox.random();
            num2 = randomBox.random();

            if(num1 == 1 && num2 == 0) {
                return 1;
            } else if(num1 == 0 && num2 == 1) {
                return 0;
            }
        }
    }

}
