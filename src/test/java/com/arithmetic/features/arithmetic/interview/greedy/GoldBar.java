package com.arithmetic.features.arithmetic.interview.greedy;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * 切割数组需要使用数组长度的铜板，如何分割使用最少的铜板
 *
 * 60长度的金条，分割为[10, 20, 30]，第一次分割使用60铜板，分割为10，50或者30，30
 *  第二次可以分割50为20,30，消耗50铜板，如果是30,30则使用30铜板分割为10,20
 *  所以计算最少的铜板数是多少
 *
 *  贪心算法：在本题中，体现在每次都拿到2个最小的数据进行操作
 *
 * @author alan.chen
 * @date 2020/6/27 12:27 PM
 */
public class GoldBar {


    @Test
    public void glodBarTest() {

        int[] arr = {10, 20, 30};
        int i = lessMoney(arr);

        System.out.println(i);
    }


    /**
     * 将金条分为指定的个数，所需最小的数据
     *
     * 相当于使用huffman树原理
     *
     * @param arr
     * @return
     */
    public int lessMoney(int[] arr) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        // 构建小根堆
        for (int i = 0; i < arr.length; i++) {
            queue.offer(arr[i]);
        }
        // 每次获取2个最小的数据，并计算对应的和
        int sum = 0;
        int cur = 0;
        // 数组中最后的一个值就是树的根节点
        while(queue.size() > 1) {
            cur = queue.poll() + queue.poll();
            sum += cur;
            // 再将计算的和放入队列
            queue.offer(cur);
        }
        return sum;
    }

}
