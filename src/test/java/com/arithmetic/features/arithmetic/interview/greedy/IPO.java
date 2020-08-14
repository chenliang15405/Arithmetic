package com.arithmetic.features.arithmetic.interview.greedy;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 有一个花费数组，有一个利润数组，并且给定初始资金W，最多做的项目K，每次只能做一个项目
 *  花费数组和利润数组是一对一关系
 *
 *  每次做项目，会赚取到利润，只有当资金到达之后，才可以做指定花费的项目
 *
 *  如何在做k个项目转你最多的钱
 *
 *  贪心算法
 *
 * @author alan.chen
 * @date 2020/6/27 12:39 PM
 */
public class IPO {


    @Test
    public void test() {
        int[] cost = {10, 20, 30 , 40, 50, 60};
        int[] profit = {20, 10, 10, 10, 10, 5};
        int w = 10;
        int k = 6;

        int i = maxProfit(w, k, cost, profit);

        System.out.println(i);
    }


    /**
     * 做k次项目，最大的利润
     *
     * 小根堆根据话费进行排序，大根堆根据利润进行排序，每做一次项目，则将当前可以做的项目弹出到大根堆中
     *
     * @param w 本金
     * @param k 做k次项目
     * @param cost 项目话费数组
     * @param profit 项目利润数组
     * @return
     */
    public int maxProfit(int w, int k, int[] cost, int[] profit) {
        Node[] nodes = new Node[profit.length];
        // 构建对象
        for (int i = 0; i < profit.length; i++) {
            nodes[i] = new Node(cost[i], profit[i]);
        }

        // 构建花费小根推和利润大根堆
        PriorityQueue<Node> minCostQueue = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Node> maxProfitQueue = new PriorityQueue<>((Node a, Node b) -> {
            return b.profit - a.profit;
        });
        // 添加元素
        for (int i = 0; i < nodes.length; i++) {
            minCostQueue.add(nodes[i]);
        }

        // 做k次项目
        for (int i = 0; i < k; i++) {
            // 如果当前的本金大于等于最小的话费的项目，则弹出该项目添加到可以做的项目中
            while (!minCostQueue.isEmpty() && w >= minCostQueue.peek().cost) {
                maxProfitQueue.add(minCostQueue.poll());
            }
            // 判断可以做的项目是否为null
            if(maxProfitQueue.isEmpty()) {
                return w;
            }
            // 计算每次做项目的最大利润
            w += maxProfitQueue.poll().profit;
        }

        return w;
    }

    // 比较器
    public static class MinCostComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.cost - o2.cost;
        }
    }


    static class Node {
        int cost;
        int profit;

        public Node(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }





}
