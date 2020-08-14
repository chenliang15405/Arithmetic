package com.arithmetic.features;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author alan.chen
 * @date 2020/4/18 3:13 PM
 */
public class ThreadLocalTest {

    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    /**
     *  <jdk8实现多线程使用安全的SimpleDateFormat
     */
    @Test
    public void threadLocalTest() {

        ExecutorService executors = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executors.submit(new DateUtil("2019-11-25 09:00:" + i % 60));
        }
    }

    static class DateUtil implements Runnable {
        private String date;

        public DateUtil(String date) {
            this.date = date;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + " 中是否有SimpleDateFormat" + threadLocal.get());
            if (threadLocal.get() == null) {
                System.out.println(Thread.currentThread() + " 保存SimpleDateFormat到ThreadLocal");
                threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            } else {
                try {
                    Date date = threadLocal.get().parse(this.date);
                    System.out.println(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  >jdk8实现多线程使用安全的SimpleDateFormat
     */
    @Test
    public void threadLocalTest2() {

        ExecutorService executors = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            String date = "2019-11-25 09:00:" + i % 60;
            executors.submit(()-> {
                System.out.println(Thread.currentThread() + " 中是否有SimpleDateFormat:  " + threadLocal.get());
                if (threadLocal.get() == null) {
                    System.out.println(Thread.currentThread() + " 保存到ThreadLocal");
                    threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                } else {
                    try {
                        Date parse = threadLocal.get().parse(date);
                        System.out.println(parse);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
