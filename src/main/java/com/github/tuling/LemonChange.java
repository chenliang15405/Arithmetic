package com.github.tuling;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/3 16:38
 */
public class LemonChange {

    /**
     * 柠檬水找零   贪心算法
     *   每杯柠檬水5美金，顾客排队进行购买，初始没有零钱，顾客的美金大小为5、10、20面额，是否能够每位用户都能找零，如果可以返回true，如果不可以返回false
     */

    @Test
    public void test() {
        int[] arr = new int[]{5,5,10,10};

        System.out.println(change(arr));
    }


    /**
     *  贪心时，如果可以给10元则给10元，否则才给5元
     *  记录每个面额的个数
     *
     */
    private boolean change(int[] bills) {
        int five = 0;
        int ten = 0;

        for (int i = 0; i < bills.length; i++) {
            if(bills[i] == 5) {
                // 面额的张数增加
                five++;
            } else if(bills[i] == 10) {
                // 判断当前是否有5的个数
                if(five > 0) {
                    // 记录当前的金额个数
                    five--;
                    ten++;
                } else {
                    // 否则不能找零
                    return false;
                }
            } else if(bills[i] == 20) {
                // 判断当前是否符合找零条件
                if(five >= 3) {
                    five -= 3;
                } else if(five > 0 && ten > 0) {
                    five--;
                    ten--;
                } else {
                    return false;
                }
            }
        }

        return true;
    }




}
