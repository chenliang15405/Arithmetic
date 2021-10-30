package com.github.bstation.other;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/6 18:44
 */
public class RandToRand {

    /**
     * 给定一个随机函数f,等概率返回1-5中的一个数字，这是你唯一可以使用的随机机制，如何实现等概率返回1~7中的一个数字
     *
     * 给你一个随机函数f，等概率返回a~b中的一个数字，这是你唯一可以使用的随机机制，如何实现等概率返回c~d中的一个数字
     *
     * 下面的题和上面的一样的，将字符转换为ASCII即可，还是数字的随机问题
     *
     * 解法：
     *  1. 根据随机函数f封装一个随机函数，该随机函数等概率的只返回0 和 1
     *  2. 然后计算当前的范围需要使用多少个二进制位，并对每个二进制位调用上面的随机函数，就可以获得一个数字，如果超过了实际的范围就重新随机，
     *     如果没有超过就可以随机返回这个数字，这个数字就是随机的数字
     *
     * https://www.bilibili.com/video/BV18i4y1K7JK?p=55
     */
    @Test
    public void test() {
        RandomBox randomBox = new RandomBox(1, 7);

        for (int i = 0; i < 10; i++) {
            System.out.println(random(randomBox, 1, 10));
        }


    }



    public static class RandomBox {
        private final int min;
        private final int max;

        public RandomBox(int min, int max) {
            this.min = min;
            this.max = max;
        }

        // 返回一个随机数，在[min,max]
        public int random() {
            // 后面的随机数+1是为了保证[0,size] 前后都是闭区间
            return min + (int)(Math.random() * (max - min + 1));
        }

        public int min() {
            return min;
        }

        public int max() {
            return max;
        }
    }


    /**
     *
     * @param randomBox 系统给定的随机函数
     * @param from  给定的最小值
     * @param to    给定的最大值
     * @return  返回在[from, to]之间的一个随机数
     */
    public static int random(RandomBox randomBox, int from, int to) {
        if(from == to) {
            return from;
        }
        // 计算当前随机的范围
        int range = to - from;
        // 计算当前的随机范围需要多少个二进制数
        int num = 1;
        // 1 << 1 就是2， 2-1=1，所以这里需要-1
        while ((1 << num) - 1 < range) {
            num++;
        }

        int res = 0;
        // 对每一个二进制位进行随机，随机函数返回0或者1，每个二进制位进行随机之后就会获取到一个整数
        // 如果这个数大于当前的随机范围，那么就重新随机，保证随机数落在随机范围中
        do {
            res = 0;
            // 对每一个二进制位进行随机
            for (int i = 0; i < num; i++) {
                res += (rand0(randomBox) << i);
            }
        } while (res > range); // 如果res > 需要计算的范围，则继续随机

        // 结果还要加上from，因为之前的随机是在范围之内
        return res + from;
    }

    /**
     * 手动封装一个随机函数， 这个随机函数随机返回0或者1
     *
     *   根据系统给定的随机函数进行封装，获取到系统随机的范围
     *    1. 偶数个数字：小于mid则是0，大于mid则是1
     *    2. 奇数个数字：小于mid则是0，大于mid则是1，等于mid则重新随机
     *
     */
    private static int rand0(RandomBox randomBox) {
        int min = randomBox.min;
        int max = randomBox.max;
        int size = max - min + 1;
        // 判断当前的范围是否为奇数个数字
        // 通过与1进行&位运算， &：相同就是1，不同就是0，奇数的二进制位就是1，所以需要不等于0就是奇数
        boolean odd = (size & 1) != 0;

        int mid = size / 2;
        int res;
        // 如果是奇数，并且随机得到的数字是中位数，那么再次随机，否则返回0或者1
        do {
            res = randomBox.random() - min;
        } while (odd && res == mid);

        return res > mid ? 1 : 0;
    }

}
