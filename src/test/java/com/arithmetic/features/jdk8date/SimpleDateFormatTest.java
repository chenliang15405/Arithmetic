package com.arithmetic.features.jdk8date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 测试simpledateformat为线程不安全demo
 */
public class SimpleDateFormatTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 线程安全问题
        //        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");


        // 创建线程池
        ExecutorService es = Executors.newFixedThreadPool(10);

        // 创建callable线程对象
        Callable<Date> task = () -> DateFormatThreadLocal.convert("20190909");

        List<Future<Date>> future = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            future.add(es.submit(task));
        }

        for (Future<Date> dateFuture : future) {
            System.out.println(dateFuture.get());
        }
        // 关闭线程池
        es.shutdown();

        // SimpleDateFormat是线程不安全的，所以多线程会出现问题，解决方案： 通过ThreadLocal解决

    }

    @Test
    public void testJdk8Date() throws ExecutionException, InterruptedException {
        // 使用jdk8的时间api
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 创建线程池
        ExecutorService es = Executors.newFixedThreadPool(10);

        // 创建callable线程对象
        Callable<LocalDate> task = () -> LocalDate.parse("20190909", df);

        List<Future<LocalDate>> future = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            future.add(es.submit(task));
        }

        for (Future<LocalDate> dateFuture : future) {
            System.out.println(dateFuture.get());
        }
        // 关闭线程池
        es.shutdown();

    }

}
