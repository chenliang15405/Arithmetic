package com.github.bstation;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/15 23:20
 */
public class NQueens {

    /**
     * N皇后问题
     *   n行n列的棋盘
     *
     */

    @Test
    public void test() {
        int n = 13;
        System.out.println(num1(n));
    }

    /**
     * 未经优化的1
     */
    public int num1(int n) {
        int[] record = new int[n];
        return process1(n, record, 0);
    }

    private int process1(int n, int[] record, int row) {
        if(row == n) {
            // 当到达最后一行时，返回1中方法
            return 1;
        }
        int res = 0;
        // 这里循环的是列，因为是n行n列，行和列是相等的
        for (int i = 0; i < n; i++) {
            // 这里不用重置，因为当前位置满足，则继续递归下面的所有解法，需要将当前的列记录到棋盘数组中，如果当前列不满足
            // 则下个循环中将row位置的记录的列覆盖了，不用重置
            record[row] = i;
            // 判断当前位置是否有效
            if(isValid(record, row)) {
                // 计算当前行的当前位置，下面棋盘拥有的解法，当不满足之后，重新移动当前行的当前列，然后再次计算
                res += process1(n, record,  row+1);
            }
        }
        return res;
    }

    private boolean isValid(int[] record, int k) {
        // 肯定不同行，因为通过循环控制行，只需要判断同列或者是否同一斜线
        // 判断当前行之后的所有行和当前行是否同一列或者同一斜线， k：当前行
        for (int i = 0; i < k; i++) {
            // 判断是否同一斜线：两个行相减的绝对值 = 两个列相减的绝对值
            if(record[i] == record[k] || Math.abs(i-k) == Math.abs(record[i] - record[k])) {
                return false;
            }
        }
        return true;
    }


}
