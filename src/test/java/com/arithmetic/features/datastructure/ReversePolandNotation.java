package com.arithmetic.features.datastructure;

import org.junit.Test;

import java.util.*;

/**
 *
 * 逆波兰计算器
 *
 * 中缀表达式转后缀表达式（逆波兰表达式）
 *
 * @author alan.chen
 * @date 2020/5/26 6:05 PM
 */
public class ReversePolandNotation {


    /**
     * 逆波兰计算器
     */
    @Test
    public void test1() {
        // 4 * 5 - 8 + 60 + 8 / 2 => 4 5 * 8 - 60 + 8 2 / +
        String str = "4 5 * 8 - 60 + 8 2 / +"; // 76

        List<String> listString = getListString(str);
        int calculate = calculate(listString);

        System.out.println("计算结果=" + calculate);
    }


    @Test
    public void test2() {
        // 中缀表达式：1+((2+3)×4)-5 后缀表达式：1 2 3 + 4 × + 5 –
        //1+((2+3)×4)-5 => 转成  1 2 3 + 4 × + 5 –
        //String infixStr = "100+((2+3)*20)-50";
        String infixStr = "3+6/(3-2)";
        List<String> suffixExpressionList = toSuffixExpressionList(infixStr);
        System.out.println("后缀表达式: " + suffixExpressionList);

        // 使用波兰计算器计算
        int calculate = calculate(suffixExpressionList);
        System.out.println("逆波兰计算器计算结果：" + calculate);

    }

    /**
     * 中缀表达式转换后缀表达式
     *
     * 还可以将中缀表达式先转换为List集合（先处理多位数），然后再直接转化为后缀表达式
     *
     * @param infixStr 中缀表达式字符串
     * @return
     */
    private List<String> toSuffixExpressionList(String infixStr) {
        if(infixStr.length() <= 0) {
            return null;
        }
        // 使用栈保存运算符
        Stack<String> symbolStack = new Stack<>();
        // 使用集合保存数字和符号作为后缀表达式集合
        List<String> list = new ArrayList<>();

        int index = 0;
        // 开始扫描字符串
        while (index < infixStr.length()) {
            String sn = String.valueOf(infixStr.charAt(index));
            while (sn.matches("\\d+") && index + 1 < infixStr.length() && String.valueOf(infixStr.charAt(index + 1)).matches("\\d+")) {
                sn += String.valueOf(infixStr.charAt(index + 1));
                index++;
            }
            if(sn.matches("\\d+")) {
                // 如果是数字，则直接添加到集合
                list.add(sn);
            } else {
                // 如果是运算符，则判断
                // 1.如果stack为空或者栈顶是"("则直接入栈, 当前运算符优先级大于栈顶运算符，则直接入栈
                if(symbolStack.empty() || symbolStack.peek().equals("(") || priority(sn) > priority(symbolStack.peek())) {
                    symbolStack.push(sn);
                } else if(sn.equals("(")) {
                    // 如果当前运算符是( 直接入栈
                    symbolStack.push(sn);
                } else if(sn.equals(")")) {
                    // 2. 如果当前运算符为")"，则弹出运算符栈中直到"("的所有运算符入数字列表
                    while (symbolStack.size() > 0 && !symbolStack.peek().equals("(")) {
                        list.add(symbolStack.pop());
                    }
                    // 将栈中的"("弹出
                    symbolStack.pop();
                } else {
                    // 3. 如果当前运算符优先级小于等于栈顶运算符，则弹出栈顶运算符进入数字列表，并继续比较当前运算符与栈顶大小
                    while(symbolStack.size() > 0 && priority(sn) <= priority(symbolStack.peek())) {
                        list.add(symbolStack.pop());
                    }
                    symbolStack.push(sn);
                }
            }
            index++;
        }
        while (symbolStack.size() > 0) {
            list.add(symbolStack.pop());
        }
        return list;
    }


    /**
     * 使用逆波兰表达式进行计算
     *
     * 遍历后缀表达式，将数字入栈，如果是运算符则弹出栈顶进行运算，运算结果入数栈，最后只有一个结果在数栈
     *
     * @param listString 后缀表达式（逆波兰表达式）
     * @return
     */
    private int calculate(List<String> listString) {
        Stack<Integer> stack = new Stack<>();
        // 4 5 * 8 -
        for (String item : listString) {
            if(item.matches("\\d+")) {
                stack.push(Integer.valueOf(item));
            } else {
                // 如果是运算符，则弹出数栈的栈顶2个数进行运算，然后计算结果入栈
                int num1 = stack.pop();
                int num2 = stack.pop();
                int result = operateVal(num2, num1, item);

                stack.push(result);
            }
        }
        return stack.pop();
    }

    private int operateVal(int num2, int num1, String item) {
        int result = 0;
        switch (item) {
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

    private List<String> getListString(String str) {
        if(str == null || str.length() <= 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        String[] split = str.split(" ");
        for (String s : split) {
            list.add(s);
        }
        return list;
    }

    private int priority(String operator) {
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

