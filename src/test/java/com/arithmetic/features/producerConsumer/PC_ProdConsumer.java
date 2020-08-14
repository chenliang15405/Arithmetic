package com.arithmetic.features.producerConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 高并发的生产者消费者阻塞队列
 * <p>
 * volatile/cas/atomicInteger/blockingQueue/线程交互/原子引用
 *
 * @author alan.chen
 * @date 2020/7/25 1:41 PM
 */
public class PC_ProdConsumer {

    public static void main(String[] args) throws InterruptedException {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            try {
                myResource.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AA").start();

        new Thread(() -> {
            try {
                myResource.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BB").start();


        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + "\t 停止生产和消费");
        myResource.stop();
    }

}

class MyResource {

    /**
     * 保证多线程可见性 volatile
     */
    private volatile boolean FLAG = true; // 默认开启，消费和生产

    private AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 使用阻塞队列的特性实现生产者和消费者同时执行
     */
    private BlockingQueue<String> blockingQueue;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }


    /**
     * 生产者生产数据，将数据放入到阻塞队列并设置超时时间，防止并发超时
     * @throws InterruptedException
     */
    public void prod() throws InterruptedException {
        String data;
        boolean returnVal;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            returnVal = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (returnVal) {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
            }
            //TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t停止");
    }


    /**
     * 消费者 消费数据，利用阻塞队列的特性获取数据，没有数据的时候阻塞获取
     *
     * @throws InterruptedException
     */
    public void consumer() throws InterruptedException {
        String data;
        while (FLAG) {
            data = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (data == null || "".equals(data)) {
                // 消费者没有获取到数据，则停止
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2s没有获取到数据，消费者停止");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费数据" + data + "成功");
        }
    }


    public void stop() {
        FLAG = false;
    }

}
