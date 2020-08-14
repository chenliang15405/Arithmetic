package com.arithmetic.features.datastructure;


import org.junit.Test;

import java.util.Stack;

/**
 * 使用栈实现计算器
 *
 * 使用2个栈作为辅助，一个栈装数据，一个栈装运算符
 *
 * 遍历当前字符串，当是数字直接装入数栈
 *  //如果是运算符，则根据情况判断
 *    // 1. 如果栈为空，则直接入栈
 *    // 2. 如果当前运算符大于栈顶的运算符，则直接入栈
 *    // 3. 如果当前运算符小于等于栈顶运算符，则使用栈顶的运算符并且将数栈的栈顶弹出2个数进行计算，将计算结果入数栈
 *    // 4. 字符串遍历完成之后，继续对运算符的栈进行运算，最后数栈中只有1个结果数
 *
 *
 *  缺点就是：目前实现的没有考虑带有括号的运算符
 *
 *
 * @author alan.chen
 * @date 2020/5/25 11:46 PM
 */
public class Calculator {

    @Test
    public void test1() {
        String str = "1+20*3+120/3-10";

        int calcuate = calcuate(str);

        System.out.println(calcuate);
    }

    private int calcuate(String str) {
        // 使用两个栈，一个装数，一个装运算符
        Stack<Integer> nums = new Stack<>();
        Stack<String> symbols = new Stack<>();

        int index = 0; // 使用指针，遍历字符串
        while (index < str.length()) {
            String si = String.valueOf(str.charAt(index));

            // 判断是否是多位数，如果是多位数，则拼接字符
            while (si.matches("\\d+") && index+1 < str.length()) {
                String s = String.valueOf(str.charAt(index + 1));
                if(!s.matches("\\d+")) {
                    break;
                }
                si += s;
                index++;
            }

            if(si.matches("\\d+")) {
                // 如果是数字，则直接装入栈
                nums.push(Integer.valueOf(si));
            } else {
                //如果是运算符，则根据情况判断
                // 1. 如果栈为空，则直接入栈
                // 2. 如果当前运算符大于栈顶的运算符，则直接入栈
                // 3. 如果当前运算符小于等于栈顶运算符，则使用栈顶的运算符并且将数栈的栈顶弹出2个数进行计算，将计算结果入数栈
                // 4. 字符串遍历完成之后，继续对运算符的栈进行运算，最后数栈中只有1个结果数
                if(symbols.empty()) {
                    symbols.push(si);
                } else {
                    if(priority(si) > priority(symbols.peek())) {
                        symbols.push(si);
                    } else {
                        String operator = symbols.pop();
                        int num1 = nums.pop();
                        int num2 = nums.pop();
                        int result = numCalculate(num2, num1, operator);
                        nums.push(result);
                        symbols.push(si);
                    }
                }
            }
            index++;
        }

        while (symbols.size() > 0) {
            String operator = symbols.pop();
            int num1 = nums.pop();
            int num2 = nums.pop();
            int result = numCalculate(num2, num1, operator);
            nums.push(result);
        }

        System.out.println("size= " + nums.size());
        return nums.pop();
    }

    private int numCalculate(int num2, int num1, String operator) {
        int result = 0;
        switch (operator) {
            case "+":
                result = num2 + num1;
                break;
            case "-":
                result = num2 - num1;
                break;
            case "*":
                result = num2 * num1;
                break;
            case "/":
                result = num2 / num1;
                break;
            default:
                System.out.println("运算符不合法");
                break;
        }
        return result;
    }


    public int priority(String operator) {
        int val = 0;
        switch (operator) {
            case "+":
                val = 1;
                break;
            case "-":
                val = 1;
                break;
            case "*":
                val = 2;
                break;
            case "/":
                val = 2;
                break;
            default:
                System.out.println("运算符不合法");
                break;
        }
        return val;
    }

}
