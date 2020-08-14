package com.arithmetic.features.issue.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author alan.chen
 * @date 2020/6/18 10:56 AM
 */
public class Calculator {

    @Test
    public void test1() {
        String str = "3+6/(3-2)";
        //String str = "100+((2+3)*20)-50";

        List<String> list = suffixExpression(str);
        System.out.println("后缀表达式" + list); // [3, 6, 3, 2, -, /, +]

        int result = calculate(list);
        System.out.println("计算结果：" + result);
    }

    private int calculate(List<String> list) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if(s.matches("\\d+")) {
                stack.push(Integer.valueOf(s));
            } else {
                Integer num1 = stack.pop();
                Integer num2 = stack.pop();
                int cal = cal(num2, num1, list.get(i));
                stack.push(cal);
            }
        }
        return stack.pop();
    }

    private int cal(int num1, int num2, String s) {
        int result = 0;
        switch (s) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
        }
        return result;
    }

    private List<String> suffixExpression(String str) {
        Stack<String> stack = new Stack<>();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            String s = String.valueOf(str.charAt(i));
            if(s.matches("\\d+")) {
                list.add(s);
            } else {
                if(stack.isEmpty() || stack.peek().equals("(") || priority(stack.peek()) < priority(s)) {
                    stack.push(s);
                } else if(s.equals("(")) {
                    stack.push(s);
                } else if(s.equals(")")) {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        list.add(stack.pop());
                    }
                    stack.pop();
                } else if(priority(stack.peek()) >= priority(s)) {
                    while (!stack.isEmpty() && priority(stack.peek()) >= priority(s)) {
                        list.add(stack.pop());
                    }
                    stack.push(s);
                }
            }
        }

        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        return list;
    }

    private int priority(String peek) {
        int flag = 0;
        switch (peek) {
            case "+":
                flag = 1;
                break;
            case "-":
                flag = 1;
                break;
            case "*":
                flag = 2;
                break;
            case "/":
                flag = 2;
                break;
        }
        return flag;
    }

}
