package com.github.左神算法.贪心;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/6/14 18:17
 */
public class BestArrange {

    /**
     * 会议室时间安排
     *
     * 一些项目要占用一个会艺术宣讲 ，会议室不能同时容纳两个项目的宣讲。
     * 给你每一个项目开始的时间和结束的时间，你来安排宣讲的日程，要求会议室的进行的宣讲场次最多，返回最多的宣讲场次
     *
     */

    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 贪心策略：根据会议的结束时间进行排序，然后依次获取到可以安排的时间
     *
     */
    public int arrange(Program[] programs) {
        if(programs == null || programs.length <= 0) {
            return 0;
        }
        // 按照结束时间排序
        Arrays.sort(programs, (a, b) -> {
            return a.end - b.end;
        });

        // 遍历当前所有时间，并取满足条件的
        int count = 0;
        int timeline = 0;
        for (int i = 0; i < programs.length; i++) {
            if(timeline <= programs[i].start) {
                count++;
                // 更新timeline为当前的end
                timeline = programs[i].end;
            }
        }
        return count;
    }
}
