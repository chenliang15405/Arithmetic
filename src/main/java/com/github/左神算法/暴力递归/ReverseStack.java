package com.github.左神算法.暴力递归;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/6/20 21:46
 */
public class ReverseStack {

    /**
     * 逆序一个栈
     *   不能申请额外的数据结构，只能使用递归函数
     *
     */

    @Test
    public void test() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack);

        reverse(stack);

        System.out.println(stack);
    }


    public void reverse(Stack<Integer> stack) {
        if(stack.isEmpty()) {
            return;
        }
        // 获取栈底的元素
        Integer last = f(stack);
        // 递归
        reverse(stack);
        // 将当前元素弹出的元素重新入栈，递归到最后一个的时候，拿出来的是原本栈顶的元素，但是最先push到栈中，那么回溯的时候就会逆序过来
        stack.push(last);
    }

    /**
     * 返回栈底的元素，并保持栈中其他元素顺序不变
     */
    private Integer f(Stack<Integer> stack) {
        if(stack.isEmpty()) {
            return null;
        } else {
            // 如果不等于空，则弹出当前值
            Integer cur = stack.pop();
            // 递归，并获取到栈底的元素
            Integer last = f(stack);
            if(last == null) {
                // 如果是最后一个元素，直接返回
                return cur;
            }
            // 将当前元素重新入栈
            stack.push(cur);
            return last;
        }
    }


}
