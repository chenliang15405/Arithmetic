package com.github.左神算法.动态规划;

/**
 * @author tangsong
 * @date 2021/6/27 17:03
 */
public class HorseJump {

    /**
     *  给定一个象棋的棋盘
     *   然后把整个棋盘放入到第一象限，期盼的最左下角是（0,0）位置，那么整个棋盘就是横坐标上9条线，纵坐标上10条线的区域，给你三个参数x,y,k
     *   返回“马”从（0,0）位置触发，必须走k步，最后落在（x,y）上的方法数有多少种
     */

    /**
     * 暴力递归
     * <p>
     * a，b目标位置
     * k：走的步数
     */
    public int way1(int a, int b, int k) {
        return process(0, 0, a, b, k);
    }

    // 从（x,y）到（a,b）使用rest步最多有多少种方法
    private int process(int x, int y, int a, int b, int rest) {
        if (x < 0 || x >= 10 || y < 0 || y >= 9) {
            return 0;
        }
        if (rest == 0) {
            return (x == a && y == b) ? 1 : 0;
        }
        // 计算可以跳动的每个格子的可能性
        int way = process(x + 2, y + 1, a, b, rest - 1);
        way += process(x + 1, y + 2, a, b, rest - 1);
        way += process(x - 1, y + 2, a, b, rest - 1);
        way += process(x - 2, y + 1, a, b, rest - 1);
        way += process(x - 2, y - 1, a, b, rest - 1);
        way += process(x - 1, y - 2, a, b, rest - 1);
        way += process(x + 1, y - 2, a, b, rest - 1);
        way += process(x + 2, y - 1, a, b, rest - 1);

        return way;
    }

    public int way2(int a, int b, int k) {
        // 3个变量，所以使用3维数组
        int[][][] dp = new int[10][9][k + 1];
        // 初始化dp数组
        dp[a][b][0] = 1;
        // 因为都是依赖rest-1的数据，需要从rest的每一层开始计算
        for (int i = 1; i <= k; i++) {
            for (int x = 1; x < 10; x++) {
                for (int y = 1; y <9; y++) {
                    int way = pick(dp, x + 2, y + 1, i - 1);
                    way += pick(dp, x + 1, y + 2, i - 1);
                    way += pick(dp, x - 1, y + 2, i - 1);
                    way += pick(dp, x - 2, y + 1, i - 1);
                    way += pick(dp, x - 2, y - 1, i - 1);
                    way += pick(dp, x - 1, y - 2, i - 1);
                    way += pick(dp, x + 1, y - 2, i - 1);
                    way += pick(dp, x + 2, y - 1, i - 1);
                    dp[x][y][i] = way;
                }
            }
        }
        return dp[a][b][k];
    }

    // 通过定义一个函数表示当前是否超过边界
    private int pick(int[][][] dp, int x, int y, int rest) {
        if (x < 0 || x >= 10 || y < 0 || y >= 9) {
            return 0;
        }
        return dp[x][y][rest];
    }

}
