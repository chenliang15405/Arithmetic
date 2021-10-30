package com.github.tuling;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/2 18:27
 */
public class ArrangeCoin {

    /**
     *  排列硬币，第一行排列1个，第二行排列2个，以此类推，给定n个硬币，计算最多能排列的完整的行数
     */

    @Test
    public void test() {
        System.out.println(arrangeCoin1(10));

        System.out.println(arrangeCoin2(10));

    }


    /**
     * 暴力迭代
     */
    private int arrangeCoin1(int n) {
        int total = n;
        for (int i = 1; i <= total; i++) {
            n = n - i;
            // 判断当前的硬币数量是否大于当前的行数需要的硬币数量，如果<=，则表示下一行无法排列完整，当前行就是最大完整的行
            if(n <= i) {
                return i;
            }
        }
        return 0;
    }


    /**
     * 二分法查询
     */
    private int arrangeCoin2(int n) {
        int left = 0;
        int right = n;

        while (left < right) {
            int mid = (right - left) / 2 + left;
            // 计算某一行需要的硬币数， 公式为 1+2+3+4+5+。。+n = (x^2 + x) / 2
            int costCoin = (mid + 1) * mid / 2;

            if(costCoin == n) {
                return mid;
            } else if(costCoin > n) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }


}
