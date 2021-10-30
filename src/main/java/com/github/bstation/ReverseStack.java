package com.github.bstation;

import org.junit.Test;

import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/5/20 22:42
 */
public class ReverseStack {

    /**
     * 逆序栈
     *   不适用额外数据结构时，通过递归逆序一个栈
     */

    @Test
    public void test() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        System.out.println(stack);

        reverse(stack);

        System.out.println(stack);
    }



    public void reverse(Stack<Integer> stack) {
        if(stack.isEmpty()) {
            return;
        }
        // 定义一个函数，返回栈的最底下的数据，并在栈中去掉该数据
        int last = f(stack);
        // 递归调用自己，将栈中的数据全部弹出
        reverse(stack);
        // 将当前弹出的数据Push到栈中，因为递归到最底层，弹出来的数据是栈顶的数据，所以这里Push，再回溯回去就是反转了栈
        stack.push(last);
    }

    /**
     * 弹出栈底的元素，并返回
     */
    private int f(Stack<Integer> stack) {
        Integer result = stack.pop();
        // 判断当前栈是否为空
        if(stack.isEmpty()) {
            return result;
        } else {
            // 递归，每次递归弹出一个，那么递归到最后就弹出栈底的元素
            int last = f(stack);
            // 将原本自己的元素push重新Push到栈中，因为从递归的最下面开始push，所以原本的顺序不变
            stack.push(result);
            // 将栈底的元素返回
            return last;
        }
    }
}
