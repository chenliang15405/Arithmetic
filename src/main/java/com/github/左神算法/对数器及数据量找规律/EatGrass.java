package com.github.左神算法.对数器及数据量找规律;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/9/2 0:57
 */
public class EatGrass {

    /**
     * 给定一个正整数N，表示有N份青草统一堆放在仓库里，有一只牛和一只羊，牛先吃，羊后吃，它两个轮流吃草
     * 不管是牛还是羊，每一轮能吃的草量必须是：1,4,16,64...（4的某次方）
     * 谁最先把草吃完，谁获胜
     * 假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定，根据唯一的参数N,返回谁会赢
     *
     */
    @Test
    public void test() {
        for (int i = 0; i < 50; i++) {
            System.out.println(i + "=" + whoWin(i));
        }

        for (int i = 0; i < 50; i++) {
            System.out.println(i + "=" + whoWin1(i));
        }

    }

    /**
     * 暴力递归
     *
     */
    public String whoWin(int N) {
        if(N < 5) {
            return N == 0 || N == 2 ? "后手" : "先手";
        }
        int want = 1;
        while (want <= N) {
            // 当前的先手是下一轮的后手
            if(whoWin(N - want).equals("后手")) {
                return "先手";
            }
            if(want <= (N / 4)) {
                want *= 4;
            } else {
                break;
            }
        }
        return "后手";
    }

    /**
     * 0=后手
     * 1=先手
     * 2=后手
     * 3=先手
     * 4=先手
     *
     *  优化
     *   O(1)
     */
    public String whoWin1(int N) {
        int i = N % 5;
        return i == 0 || i == 2 ? "后手" : "先手";
    }

}
