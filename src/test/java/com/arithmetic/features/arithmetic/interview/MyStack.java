package com.arithmetic.features.arithmetic.interview;


import java.util.Stack;

/**
 *  实现一个特殊的栈，在实现栈功能的基础上，再实现返回栈中最小元素的操作
 *
 *  要求：(1)pop push getMin操作的时间复杂度都是O(1)
 *       (2)设计栈的类型可以使用现成栈结构
 *
 * @author alan.chen
 * @date 2020/6/7 11:37 AM
 */
public class MyStack {

    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;


    public static void main(String[] args) {
        MyStack stack = new MyStack();

        stack.push(2);
        stack.push(3);
        stack.push(1);
        stack.push(8);

        System.out.println(stack.pop());
        System.out.println(stack.getMin());
    }

    public MyStack() {
        stackData = new Stack<>();
        stackMin = new Stack<>();
    }

    /**
     * 数据入栈
     * @param num
     */
    public void push(int num) {
        // 保存数据时，判断min栈是否有数据，如果没有数据直接入栈，如果有数据则判断大小
        // 如果当前数据比栈中数据小则直接入栈，如果比栈顶数据大，则将栈顶数据再次入栈
        if(stackMin.isEmpty()) {
            stackMin.push(num);
        } else if(stackMin.peek() > num) {
            stackMin.push(num);
        } else if(stackMin.peek() < num) {
            stackMin.push(stackMin.peek());
        }
        // 数据再加入data栈
        stackData.push(num);
    }


    /**
     * 弹出栈顶数据
     * @return
     */
    public Integer pop() {
        if(stackData.isEmpty()) {
            return null;
        }
        int num = stackData.pop();
        stackMin.pop();
        return num;
    }

    /**
     * 获取当前栈中最小数据
     * @return
     */
    public Integer getMin() {
        if(stackMin.isEmpty()) {
            return null;
        }
        return stackMin.peek();
    }


}
