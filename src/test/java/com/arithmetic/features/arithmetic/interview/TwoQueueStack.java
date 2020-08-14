package com.arithmetic.features.arithmetic.interview;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 队列实现栈
 *
 * @author alan.chen
 * @date 2020/6/7 3:57 PM
 */
public class TwoQueueStack {

    private Queue<Integer> queue;
    private Queue<Integer> help;


    public static void main(String[] args) {
        TwoQueueStack stack = new TwoQueueStack();
        stack.push(3);
        stack.push(1);
        stack.push(2);

        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
    }


    public TwoQueueStack() {
        queue = new LinkedList<>();
        help = new LinkedList<>();
    }



    public void push(int num) {
        queue.add(num);
    }

    public Integer pop() {
        // 将数据倒入到help队列中，queue队列留一个数据
        while (queue.size() > 1) {
            help.add(queue.poll());
        }
        // 获取到数据队列中最后一个数据，作为返回数据
        int data = queue.poll();
        // 然后将两个队列的指针交换
        swap(queue, help);

        return data;
    }

    public Integer peek() {
        while (queue.size() > 1) {
            help.add(queue.poll());
        }
        Integer data = queue.poll();
        // 将最后一个数加入help队列
        help.add(data);
        // 再将队列交换
        swap(queue, help);

        return data;
    }

    private void swap(Queue<Integer> queue, Queue<Integer> help) {
        this.help = queue;
        this.queue = help;
    }

}
