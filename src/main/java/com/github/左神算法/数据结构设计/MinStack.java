package com.github.左神算法.数据结构设计;

import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/6/13 17:51
 */
public class MinStack {

    /**
     * 最小栈问题
     *
     *    实现一个特殊的栈，在基础功能的基础上，在实现返回栈中最小元素的功能
     *     1> pop、push、getMin操作的时间复杂度都是O(1)
     *     2> 设计的栈类型可以使用现成的栈结构
     *
     */

    public static class GetMinStack {
        public Stack<Integer> stack;
        public Stack<Integer> minStack;

        public GetMinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int num) {
            stack.push(num);
            if(num <= this.getMin()) {
                minStack.push(num);
            } else {
                minStack.push(this.getMin());
            }
        }

        public Integer pop() {
            if(stack.isEmpty()) {
                return null;
            }
            Integer integer = stack.pop();
            minStack.pop();
            return integer;
        }

        private Integer getMin() {
            return minStack.peek();
        }

    }


}
