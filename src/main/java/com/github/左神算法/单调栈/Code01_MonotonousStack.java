package com.github.左神算法.单调栈;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/7/18 1:30
 */
public class Code01_MonotonousStack {

    /**
     * 单调栈是什么？
     *   一种特别设计的栈结构，为了解决如下的问题：
     *
     *   给定一个可能含有重复值的数组arr，i位置的数一定存在如下两个信息
     *   1> arr[i]的左侧离i最近并且小于（或者大于）arr[i]的数在哪？
     *   2> arr[i]的右侧离i最近并且小于（或者大于）arr[i]的数在哪？
     *   如果想得到arr中所有位置的两个信息，怎么能让得到信息的过程尽量快。
     *
     *   单调栈就是为了解决这个问题
     *
     */
    @Test
    public void test() {
        int size = 10;
        int max = 20;
        int testTimes = 2000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            int[][] nearLessNoRepeat = getNearLessNoRepeat(arr1);
            if (!isEqual(nearLessNoRepeat, rightWay(arr1))) {
                System.out.println("Oops!");
                printArray(arr1);
                System.out.println(Arrays.deepToString(nearLessNoRepeat));
                break;
            }
			if (!isEqual(getNearLssRepeat(arr2), rightWay(arr2))) {
				System.out.println("Oops!");
				printArray(arr2);
				break;
			}
        }
        System.out.println("测试结束");

    }


    /**
     * arr = [3,1,2,3]
     *        0,1,2,3
     * [
     *     0: [-1, 1]
     *     1: [-1,-1]
     *     2: [1, -1]
     *     3: [2, -1]
     * ]
     *
     * 返回每个位置的元素左边比它小的位置，右边比它小的位置
     *
     * 这个方法不处理含有重复值的数组
     */
    public int[][] getNearLessNoRepeat(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int[][] ans = new int[arr.length][2];

        for (int i = 0; i < arr.length; i++) {
            // 如果当前加入的数小于栈顶的数，那么就结算栈顶的数
            while (!stack.isEmpty() && arr[i] < arr[stack.peek()]) {
                int j = stack.pop();
                int pre = stack.isEmpty() ? - 1 : stack.peek();
                ans[j][0] = pre;
                ans[j][1] = i;
            }
            stack.push(i);
        }
        // 如果栈中海油元素没有结算
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int pre = stack.isEmpty() ? -1 : stack.peek();
            ans[j][0] = pre;
            ans[j][1] = -1; // 所有的元素都加入到栈中了，当前元素还没有被弹出去，则表示右边没有比当前元素小的元素，那么就是-1
        }
        return ans;
    }


    /**
     * 和上面的功能相同，只不过可以处理数组中含有重复值
     *
     * 如果有相等的元素需要入栈，那么使用一个链表（集合）来保存所有相同的元素的位置，并且在弹出元素的时候，弹出的是集合的最后一个
     *
     */
    public int[][] getNearLssRepeat(int[] arr) {
        // 单调栈，使用链表保存每个位置上的元素，因为可能有相同的元素，使用集合保存相同的元素，不过也有相对的先后顺序，方便弹出
        Stack<List<Integer>> stack = new Stack<>();
        int[][] result = new int[arr.length][2];

        for (int i = 0; i < arr.length; i++) {
            // 判断单调栈中是否有元素以及是否当前的元素小于栈顶的元素，如果小于栈顶的元素，那么将栈顶元素弹出，进行结算，当前元素入栈
            // 此处定义这个单调栈的顺序为从小到大
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                // 获取到前一个元素的下标，如果有多个元素，那么获取最后加入的元素的下标
                List<Integer> pops = stack.pop();
                // 获取当前栈顶元素的下标，如果有多个元素，那么获取最后加入的元素的下标
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer pop : pops) {
                    result[pop][0] = leftLessIndex;
                    result[pop][1] = i;
                }
            }
            // 判断是否当前元素的值和栈顶元素的值相同，如果相同，那么直接加入到一个list中
            if(!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {
                // 如果不相等，那么新建集合加入
                List<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }
        while (!stack.isEmpty()) {
            // 获取到前一个元素的下标，如果有多个元素，那么获取最后加入的元素的下标
            List<Integer> pops = stack.pop();
            // 获取当前栈顶元素的下标，如果有多个元素，那么获取最后加入的元素的下标
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer pop : pops) {
                result[pop][0] = leftLessIndex;
                result[pop][1] = -1;
            }
        }
        return result;
    }




    // for test
    public int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
