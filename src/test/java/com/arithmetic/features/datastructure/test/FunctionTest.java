package com.arithmetic.features.datastructure.test;

import org.junit.Test;

/**
 * @author alan.chen
 * @date 2020/5/19 12:09 PM
 */
public class FunctionTest {

    @Test
    public void queueTest() {

        ArrayQueue queue = new ArrayQueue(3);
        queue.push(1);
        queue.push(2);
        queue.push(3);

        System.out.println(queue.peek());

        System.out.println(queue.pop());

        queue.push(4);

        System.out.println(queue.peek());
        System.out.println(queue.pop());
        System.out.println(queue.peek());
        System.out.println(queue.pop());

    }

    @Test
    public void stackTest() {
        ArrayStack stack = new ArrayStack(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.peek());

        System.out.println(stack.pop());
        stack.push(6);
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

    }

}
