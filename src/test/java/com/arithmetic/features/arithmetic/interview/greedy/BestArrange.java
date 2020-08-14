package com.arithmetic.features.arithmetic.interview.greedy;

import org.junit.Test;

import java.util.Arrays;

/**
 * 会议室安排会议，每次最多安排一个，每个会议有开始时间和结束时间
 *
 * 最多可以安排多少个会议
 *
 * 贪心算法
 *
 * @author alan.chen
 * @date 2020/6/27 2:16 PM
 */
public class BestArrange {


    @Test
    public void test() {
        Program[] programs = new Program[3];
        programs[0] = new Program(1, 5);
        programs[1] = new Program(3, 8);
        programs[2] = new Program(6, 9);

        int i = bestArrange(programs, 1);

        System.out.println(i);
    }


    /**
     * 计算最多可以安排的数组
     *
     * 贪心算法：根据会议的结束时间点计算贪心，按照最早结束的会议则可以安排
     *
     * @param programs 所有的会议集合
     * @param start 从指定的时间点开始
     * @return
     */
    public int bestArrange(Program[] programs, int start) {
        // 按照结束时间升序排序，最早结束的在前面（也可以使用小根堆按照结束时间排序）
        Arrays.sort(programs, (Program a, Program b) -> a.end - b.end);

        int count = 0;
        // 遍历所有会议
        for (int i = 0; i < programs.length; i++) {
            if(programs[i].start >= start) {
                count++;
                // 替换变量，将结束时间赋值，后面的会议和结束时间进行比较
                start = programs[i].end;
            }
        }
        return count;
    }


    static class Program {
        int start;
        int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

}
