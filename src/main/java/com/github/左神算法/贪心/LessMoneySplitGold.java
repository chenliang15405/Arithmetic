package com.github.左神算法.贪心;

import java.util.PriorityQueue;

/**
 * @author tangsong
 * @date 2021/6/14 18:26
 */
public class LessMoneySplitGold {

    /**
     * 切割金条问题
     *
     *  一个金条切成两半，是需要花费和长度数值一样的铜板的。比如长度为20的金条，不管怎么切，都需要花费20个铜板，一群人想整分金条，怎么分最省铜板？
     *
     *  例如：给定数组{10,20,30} 代表一共三个人，整块金条长度为60，金条要分成10,20,30三个部分。
     *
     *  如果先把长度60的金条分为10和50，花费60，再把长度为50的金条分成20和30，花费50;一共花费110铜板
     *  但是如果先把长度60的金条分为20和30，花费60；再把长度30金条分成10和20，花费30，一共花费90铜板
     *
     * 输入一个数组，返回分割的最小代价
     */


    /**
     * 类似于哈夫曼编码
     *
     */
    public int splitGold(int[] arr) {
        if(arr == null || arr.length < 2) {
            return 0;
        }
        // 创建一个小根堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            queue.offer(arr[i]);
        }
        int sum = 0;
        int cur = 0;
        // 先从堆中拿到2个数，进行相加，然后将这个数重新加入到堆中，然后再继续，最后堆中只有一个数，那么就停止，这个数就是第一次切割时的数
        // 每次两个数之和相加的总和就是总的代价
        while (queue.size() > 1) {
            Integer num1 = queue.poll();
            Integer num2 = queue.poll();
            cur = num1 + num2;
            sum += cur;
            queue.offer(cur);
        }

        return sum;
    }

}
