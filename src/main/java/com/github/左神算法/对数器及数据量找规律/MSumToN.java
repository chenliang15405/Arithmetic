package com.github.左神算法.对数器及数据量找规律;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/9/2 1:17
 */
public class MSumToN {

    /**
     * 定义一种数：可以表示成若干（数量>1）连续正数和的数
     * 比如：
     *  5=2+3,5就是这样的数
     *  12=3+4+5 12就是这样的数
     *  1不是这样的数，因为要求的数量大于1个、连续正数和
     *  2=1+1,2也不是，因为等号右边的数不是连续正数
     *  给定一个参数N,返回是不是可以表示成若干连续正数和的数
     *
     */
    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i + "=" + toN(i));
        }
        System.out.println("-------------------------");
        for (int i = 0; i < 100; i++) {
            System.out.println(i + "=" + toN1(i));
        }
    }

    /**
     * 暴力法
     *
     */
    public boolean toN(int N) {
        // 从1开始尝试累加，每个数向后累加查看是否可以凑成N
        for (int i = 1; i < N; i++) {
            int sum = i; // 因为必须是连续的组成，所以直接从每个数开始累加进行尝试
            for (int j = i + 1; j <= N; j++) {
                if(sum == N) {
                    return true;
                }
                if(sum > N) {
                    break;
                }
                sum += j;
            }
        }
        return false;
    }


    /**
     * 根据暴力法的结果得到的规律： 2的n次方都是false
     *
     */
    public boolean toN1(int N) {
        // 判断一个数是否为2的n次方
       // (N & (N - 1)) == 0 则N是2的n次方
        return (N & (N - 1)) != 0;
    }

}
