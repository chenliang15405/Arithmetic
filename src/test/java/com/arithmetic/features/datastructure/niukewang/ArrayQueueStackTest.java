package com.arithmetic.features.datastructure.niukewang;

import org.junit.Test;

/**
 * @author alan.chen
 * @date 2020/5/10 7:35 PM
 */
public class ArrayQueueStackTest {


    @Test
    public void testQUeue() {
        ArrayQueue queue = new ArrayQueue(3);
        queue.push(1);
        queue.push(2);
        queue.push(3);

        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        queue.push(5);

        System.out.println(queue.peek());

    }

}
