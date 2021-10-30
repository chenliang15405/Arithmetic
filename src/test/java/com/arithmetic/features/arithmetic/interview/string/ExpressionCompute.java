package com.arithmetic.features.arithmetic.interview.string;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个字符串str，str表示一个公式，公式里可能有整数、加减乘除符号和左右括号，返回公式的计算结果
 * str = "3+1*4" 返回7。str="3+(1*4)" 返回7
 *
 * @author tangsong
 * @date 2021/3/27 20:26
 */
public class ExpressionCompute {

    @Test
    public void test() {
        String exp = "48*((70-65)-43)+8*1";
        System.out.println(getValue(exp));

        exp = "4*(6+78)+53-9/2+45*8";
        System.out.println(getValue(exp));

        exp = "10-5*3";
        System.out.println(getValue(exp));

        exp = "-3*4";
        System.out.println(getValue(exp));

        exp = "3+1*4";
        System.out.println(getValue(exp));
    }


    /**
     * 思路：
     *    1. 遍历字符串，将数字和+、-符号都放入到队列中
     *    2. 如果遇到*、/ 则直接计算，并将计算结果放入到队列中
     *    3. 如果遇到（，则通过递归的子函数进行计算，子函数返回（）括号中的计算结果和）括号的下一个数的位置
     *    4. 将队列中的所有值进行计算，返回结果
     */
    public int getValue(String str) {
        return value(str.toCharArray(), 0)[0];
    }

    /**
     * 计算函数
     *
     */
    private int[] value(char[] str, int i) {
        LinkedList<String> que = new LinkedList<>();
        int pre = 0;
        int[] bra = null;
        // 当前字符如果没有达到最后和遇到）则遍历
        while (i < str.length && str[i] != ')') {
            if(str[i] >= '0' && str[i] <= '9') { // 当前字符是数字
                pre = pre * 10 + str[i] - '0';
                i++;
            } else if(str[i] != '(') { // 表示当前字符是 + - * /
                // 对前一个值进行计算
                addNum(que, pre);
                // 将当前符号加入到队列中
                que.addLast(String.valueOf(str[i]));
                i++;
                // 计算之后将pre置0
                pre = 0;
            } else {
                // 如果当前是 （，则进行子函数计算括号中的值
                bra = value(str, i+ 1);
                pre = bra[0]; // 记录子函数计算的值
                i = bra[1] + 1; // 将i移动到括号的下一位
            }
        }
        // 将前一位数字进行计算，防止循环完成还有数字没有计算
        addNum(que, pre);
        // 返回计算结果，包含子函数的计算结果和索引位置
        return new int[]{getNum(que), i};
    }


    /**
     * 计算队列中所有值的结果
     *
     */
    private int getNum(LinkedList<String> que) {
        int res = 0;
        boolean add = true;
        while (!que.isEmpty()) {
            String cur = que.pollFirst(); // 从队列头开始计算
            if(cur.equals("+")) {
                add = true;
            } else if(cur.equals("-")) {
                add = false;
            } else {
                int num = Integer.parseInt(cur);
                res += add ? num : (-num);
            }
        }
        return res;
    }


    private void addNum(LinkedList<String> que, int num) {
        if(!que.isEmpty()) {
            int cur = 0;
            String top = que.pollLast();
            if(top.equals("+") || top.equals("-")) {
                que.addLast(top);
            } else {
                // 如果是 * /，那么先进行计算
                cur = Integer.parseInt(que.pollLast());
                num = top.equals("*") ? (cur * num) : (cur / num);
            }
        }
        que.addLast(String.valueOf(num));
    }


}
