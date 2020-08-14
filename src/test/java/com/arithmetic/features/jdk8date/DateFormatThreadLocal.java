package com.arithmetic.features.jdk8date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用ThreadLoal解决 SimpleDateFormat 的线程安全问题
 *
 * ThreadLocal和Synchronized都是为了解决多线程中相同变量的访问冲突问题，不同的点是：
 *
 *  1.Synchronized是通过线程等待，牺牲时间来解决访问冲突
 *  2.ThreadLocal是通过每个线程单独一份存储空间，牺牲空间来解决冲突，并且相比于Synchronized，ThreadLocal具有线程隔离的效果，
 *    只有在线程内才能获取到对应的值，线程外则不能访问到想要的值。
 *
 */
public class DateFormatThreadLocal {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    public static Date convert(String string) throws ParseException {
        return df.get().parse(string);
    }






}
