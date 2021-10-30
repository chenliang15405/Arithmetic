package com.github.左神算法.蓄水池算法;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/8/28 22:48
 */
public class ReservoirSampling {

    /**
     * 蓄水池算法
     *
     *  解决的问题：
     *    假设有一个源源不断吐出不同球的机器，只有装下10个球的袋子，每一个吐出的求球，要么放入到袋子中，要么
     *    永远丢掉，如何做到机器吐出每一个球后，所有吐出的球都等概率被放进袋子中
     *
     *
     *  解题思路：
     *    1. 当袋子中的球没有满10个，则继续加入到袋子中
     *    2. 当袋子的球满了之后，则对当前的球计算随机值，如果随机值小于等于10，则表示可以进入到袋子中，那么还需要从袋子中
     *    随机选择一个球淘汰，对10个球进行随机选择。10/120 * 1/10，那么袋子里的每个球，在N个数之后仍然在袋子里的概率为
     *    1*10/11 * 11/12 * 12/13 = 10/120, 每个数都是这个概率
     *
     *
     */
    @Test
    public void test() {
        System.out.println("hello");
        int test = 10000;
        int ballNum = 17;
        int[] count = new int[ballNum + 1];
        for (int i = 0; i < test; i++) {
            int[] bag = new int[10];
            int bagi = 0;
            for (int num = 1; num <= ballNum; num++) {
                if (num <= 10) {
                    bag[bagi++] = num;
                } else { // num > 10
                    if (random(num) <= 10) { // 一定要把num球入袋子
                        bagi = (int) (Math.random() * 10);
                        bag[bagi] = num;
                    }
                }

            }
            for (int num : bag) {
                count[num]++;
            }
        }
        for (int i = 0; i <= ballNum; i++) {
            System.out.println(count[i]);
        }

        System.out.println("hello");
        int all = 100;
        int choose = 10;
        int testTimes = 50000;
        int[] counts = new int[all + 1];
        for (int i = 0; i < testTimes; i++) {
            RandomBox box = new RandomBox(choose);
            for (int num = 1; num <= all; num++) {
                box.add(num);
            }
            int[] ans = box.choices();
            for (int j = 0; j < ans.length; j++) {
                counts[ans[j]]++;
            }
        }

        for (int i = 0; i < counts.length; i++) {
            System.out.println(i + " times : " + counts[i]);
        }
    }


    static class RandomBox {
        public int[] bag;
        public int N;
        public int size;

        public RandomBox(int capacity) {
            bag = new int[capacity];
            N = capacity;
            size = 0;
        }

        public void add(int num) {
            // 需要先进行自增，因为下面计算随机的时候，需要使用当前球的数量进行随机
            size++;
            if(size <= N) {
                bag[size - 1] = num;
            } else {
                // 先对当前的球的数量随机，查看是否可以放入到袋子中
                if(rand(size) <= 10) {
                    // 如果可以放入袋子中，则对当前的袋子的球随机判断淘汰
                    bag[rand(N) - 1] = num;
                }
            }
        }

        /**
         * 返回袋中的球
         */
        public int[] choices() {
            int[] ans = new int[N];
            for(int i = 0; i < N; i++) {
                ans[i] = bag[i];
            }
            return ans;
        }

        private int rand(int n) {
            return (int)(Math.random() * n + 1);
        }
    }



    // for test
    // 请等概率返回1~i中的一个数字
    public static int random(int i) {
        return (int) (Math.random() * i) + 1;
    }
}
