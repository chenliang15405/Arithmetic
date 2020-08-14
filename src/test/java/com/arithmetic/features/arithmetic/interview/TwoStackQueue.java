package com.arithmetic.features.arithmetic.interview;


import java.util.Stack;

/**
 * 栈实现队列
 *
 * @author alan.chen
 * @date 2020/6/7 4:19 PM
 */
public class TwoStackQueue {

    private Stack<Integer> stack;
    private Stack<Integer> help;


    public static void main(String[] args) {
        TwoStackQueue queue = new TwoStackQueue();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());

    }



    public TwoStackQueue() {
        this.stack = new Stack<>();
        this.help = new Stack<>();
    }


    public void add(int num) {
        stack.push(num);
    }

    public Integer poll() {
        if(stack.isEmpty()) {
            return null;
        }
        // 将stack栈中的数据倒入help栈中
        while (!stack.isEmpty()) {
            help.push(stack.pop());
        }
        // 获取help栈中栈顶，就是最先进入stack的数据
        Integer pop = help.pop();

        // 交换引用变量
        //swap(stack, help);

        // 将help数据重新入栈stack
        while (!help.isEmpty()) {
            stack.push(help.pop());
        }

        return pop;
    }

    public Integer peek() {
        if(stack.isEmpty()) {
            return null;
        }
        // 将stack栈中的数据倒入help栈中
        while (!stack.isEmpty()) {
            help.push(stack.pop());
        }
        // 获取help栈中栈顶，就是最先进入stack的数据
        Integer pop = help.peek();

        // 交换引用变量
        //swap(stack, help);
        // 将help数据重新入栈stack
        while (!help.isEmpty()) {
            stack.push(help.pop());
        }

        return pop;
    }



    private void swap(Stack<Integer> stack, Stack<Integer> help) {
        this.help = stack;
        this.stack = help;
    }

}
