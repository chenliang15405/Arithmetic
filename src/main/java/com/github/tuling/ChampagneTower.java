package com.github.tuling;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/4 20:59
 */
public class ChampagneTower {

    /**
     *  香槟塔，多个被子组成一个塔的形状，第1行1个杯子，第二行2个杯子，第三行3个杯子，这样的形状，然后向第一个杯子中倒酒，
     *  每个杯子只能装1杯酒，多了就会溢出，当杯子的酒溢出之后，会向下面的杯子中装入溢出的酒，每次都会平分为一半，如果下面有2个杯子，
     *  如果溢出1杯，那么每个杯子就装入0.5
     *  最多的层数为100层
     *
     *  如果倒入n杯酒，那么查询第n行第m列的杯子中的酒是多少
     */

    @Test
    public void test() {
        System.out.println(champagneTower(5, 3, 2));
    }


    /**
     * 因为杯子的数量每行和每列是相对应的，第一行有1列，第二行有2个。。。
     *
     * 并且每个杯子的溢出量为 (n-1)，如果计算下面的杯子能够分到的量为：（n-1）/2，因为一个杯子满了，则会将溢出的量均分为下面的两个杯子
     *
     */
    private double champagneTower(int n, int query_rows, int query_glass) {
        // 计算每个杯子的酒量，初始化二维数组，并且0,0位置的酒量就是 n
        double[][] count = new double[100][100];
        // 第一个位置的就酒量就是全部的倒入的杯子的量，根据这个
        count[0][0] = n;
        // 计算需要查询的行即可
        for (int i = 0; i < query_rows; i++) {
            // 每列的数量和行的数量相等
            for (int j = 0; j <= i; j++) {
                // 计算当前位置溢出的酒量，下面的每个杯子可以获取到的酒量
                double d = (count[i][j] - 1) / 2;
                if(d > 0) {
                    // 第一个杯子的酒量
                    count[i+1][j] += d;
                    count[i+1][j+1] += d;
                }
            }
        }
        return count[query_rows-1][query_glass-1];
    }
    


}
