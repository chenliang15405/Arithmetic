package com.arithmetic.features.producerConsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用了2个Condition对象，无法实现交替的打印数据，只能实现生产者生产到最大值，
 * 然后消费者消费所有的数据，然后生产者再次生产到最大值，消费者再次消费到0
 *
 * @author alan.chen
 * @date 2020/6/28 11:00 AM
 */
public class PC<T> {

    private volatile LinkedList<T> list = new LinkedList<T>();
    private Integer max = 10;

    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();


    /**
     * 如果要实现生产者和消费者的交替执行，生产一个数据，则消费者消费一个数据，那么就只能使用一个Condition对象
     * @param t
     */
    public void put(T t) {

        lock.lock();

        try {
            /**
             * 多线程并发中判断，不能使用if，要使用while，防止虚假唤醒
             */
            while(list.size() == max) {
                producer.await();
            }
            list.offer(t);
            System.out.println(Thread.currentThread().getName() + "\t" + t);
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void get() {
        lock.lock();

        try {
            while(list.size() == 0) {
                consumer.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + list.pop());
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
