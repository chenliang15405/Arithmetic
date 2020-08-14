package com.arithmetic.features.jdk8date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;

/**
 *  LocalDate   LocalTime   LocalDateTime
 *
 *  Instant 时间戳，从19700101000000  到某个时间的毫秒数
 *
 *  Duration 两个“时间”之间的间隔
 *
 *  Period 两个“日期” 之间的间隔(相差几年几月几天这样子，不能获取具体的相差多少天)
 *
 *  ChronoUnit.DAYS、ChronoUnit.MONTHS 等 可以直接获取日期之间相差的天数、月数等
 *
 *  TemporalAdjuster 时间校正器
 *      TemporalAdjusters  该类通过静态方法提供了大量的常用TemporalAdjuster的实现
 *
 *  DateTimeFormatter  格式化时间/日期
 *
 *  ZonedDate    ZonedTime   ZonedDateTIme   时区
 *
 */
public class LocalDateTimeTest {

    @Test
    public void test1() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime of = LocalDateTime.of(2019, 10, 1, 1, 15, 15);
        System.out.println(of);

        System.out.println(now.getYear());
        System.out.println(now.getMonth());
    }

    @Test
    public void test2(){
        Instant now = Instant.now();  // 默认获取的UTC时区
        System.out.println(now); // 获取时间

        OffsetDateTime offset = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offset);

        System.out.println(now.toEpochMilli()); //毫秒值

        Instant instant = Instant.ofEpochSecond(60); //相对于1970的加毫秒数的时间值
        System.out.println(instant);
    }

    @Test
    public void test3() throws InterruptedException {
        Instant now = Instant.now();

        Thread.sleep(2000);

        Instant now1 = Instant.now();

        Duration between = Duration.between(now, now1);
        System.out.println(between.toMillis());
    }

    @Test
    public void test4() {
        LocalDateTime ldt = LocalDateTime.now();

        LocalDateTime l1 = ldt.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(l1);

        LocalDateTime with = ldt.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        System.out.println(with);

        // 自定义接口实现
        LocalDateTime localDateTime = ldt.with((l) -> {
            LocalDateTime ldt3 = (LocalDateTime) l;

            DayOfWeek dayOfWeek = ldt3.getDayOfWeek();

            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return ldt3.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return ldt3.plusDays(2);
            } else {
                return ldt3.plusDays(1);
            }
        });

        System.out.println(localDateTime);
    }

    @Test
    public void test5(){
        DateTimeFormatter isoDate = DateTimeFormatter.ISO_DATE;
        LocalDateTime now = LocalDateTime.now();

        System.out.println(now.format(isoDate));

        DateTimeFormatter par = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println(now.format(par));

    }

    @Test
    public void test6(){
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter par = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println(now.parse("2019-01-01 11:12:32", par));
        System.out.println(LocalDateTime.parse("2019-01-01 11:12:32", par));
    }

    @Test
    public void test9() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        System.out.println(now.format(df));
    }

    /**
     * Duration 获取两个时间间隔，只可以获取LocalDateTime 和Instant 这种带秒的
     */
    @Test
    public void test10() {
        LocalDateTime ld1 = LocalDateTime.now();
        LocalDateTime ld2 = LocalDateTime.of(2020, 6, 1, 12, 30,30);

        Duration between = Duration.between(ld1, ld2);
        System.out.println(between.toDays());
    }

    /**
     * Period 用法 获取两个时间间隔
     */
    @Test
    public void test11() {
        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2021, Month.JUNE, 1);
        Period between = Period.between(ld2, ld1);

        // 注意，这里Period获取的相差时间是已经换算成了指定的年月日了
        // 就是说：现在距离2021-06-01 相差了 1年 1月 2 日，不可以直接获取相差多少天
        System.out.printf("相差： %d年 %d月 %d天", Math.abs(between.getYears()), Math.abs(between.getMonths()), Math.abs(between.getDays()));

        System.out.println();

    }

    /**
     * ChronoUnit 用法
     */
    @Test
    public void test12() {
        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2021, Month.JUNE, 1);
        long betweenday = ChronoUnit.DAYS.between(ld1, ld2);
        long betweenMonth = ChronoUnit.MONTHS.between(ld1, ld2);

        System.out.println("相隔天数" + betweenday);
        System.out.println("相隔月数" + betweenMonth);
    }

    @Test
    public void test13() {
        LocalDateTime ld1 = LocalDateTime.now();
        LocalDateTime ld2 = LocalDateTime.of(2021, Month.JUNE, 1, 12, 30 ,30);
        long betweenday = ChronoUnit.DAYS.between(ld1, ld2);
        long betweenSecond = ChronoUnit.SECONDS.between(ld1, ld2);

        System.out.println("相隔秒数" + betweenSecond);
        System.out.println("相隔天数" + betweenday);
    }

    @Test
    public void test7() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        System.out.println(now);

    }

    /**
     * 获取昨天日期
     */
    @Test
    public void test8() {
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.systemDefault()).format(Instant.now().minus(1, ChronoUnit.DAYS));
        System.out.println(date);
    }


    @Test
    public void test15() {
        LocalDate now = LocalDate.now();
        LocalDate date = now.plusDays(3);

        LocalTime min = LocalTime.MIN;
        LocalTime max = LocalTime.MAX;

        LocalDateTime start = LocalDateTime.of(now, min);
        LocalDateTime end = LocalDateTime.of(date, max);

        System.out.println(start);
        System.out.println(end);
    }


}
