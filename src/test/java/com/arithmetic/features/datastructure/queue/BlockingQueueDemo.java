package com.arithmetic.features.datastructure.queue;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue 有界阻塞队列
 * LinkedBlockingQueue （或者是无界阻塞队列，因为界是Integer.MAX_VALUE）有界阻塞队列（队列的容量是Integer.MAX_VALUE）
 * SynchronousQueue 同步阻塞队列，只存储一个元素，再消费之后再插入元素
 *
 *
 *
 * @author alan.chen
 * @date 2020/6/14 10:48 AM
 */
public class BlockingQueueDemo {

    @Test
    public void test() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        // add方法如果queue满了，会抛出异常
        queue.add("1");
        queue.add("2");
        queue.add("3");
        // remove方法，如果queue为空，则会抛出异常
        queue.remove("a");


        // offer方法，如果queue满了，则会返回false
        queue.offer("a");

        // 如果queue为空，则返回null
        queue.poll();


        // 如果queue满了，则会阻塞，直到队列为空
        queue.put("a");

        // 如果queue为空，则阻塞，直到队列有数据
        queue.take();

        // 如果queue满了，则等待指定时间内再插入
        queue.offer("a", 5, TimeUnit.SECONDS);


    }


}
