package com.arithmetic.features.arithmetic.interview.monotonousstack;

import org.junit.Test;

import java.util.Stack;

/**
 * 单调栈问题
 *
 * @author tangsong
 * @date 2021/1/31 18:52
 */
public class NetEaseInterview {

    /**
     * 网易面试题，单调栈问题
     *
     * 给定一个环形数组，每个数表示一个山，当一个山到另一个山之间的数不大于当前两个数之间的最小数的时候，则表示一个山可以看见
     * 另一个山，否则，表示不能看见，那么一个数组中能相互看见的这样的山有多少对
     *
     * 例如：
     * 6 -> 4 -> 5
     * |<          \>
     * 3  <- 3 <- 5
     *
     * 那么 3,4之间是看不见的，因为中间有5或者6，都比3大，所以被挡住了看不见
     * 5,6是可以看见的，因为两条路中间的所有数都小于5，没有被挡住，可以看见
     */
    @Test
    public void test() {
        int[] arr = {6,4,5,5,3,3};

        long count = communications(arr);
        System.out.println(count);
    }


    /**
     * 计算可以相互看见的山峰的个数
     */
    public long communications(int[] arr) {
        if (arr == null || arr.length <= 2) {
            return 0;
        }
        // 计算出最大值的索引，从最大值开始遍历
        int maxIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            maxIndex = arr[maxIndex] < arr[i] ? i : maxIndex;
        }
        int size = arr.length;
        // 遍历数组，计算总的可以看见的对数
        int value = arr[maxIndex];
        int index = getNextIndex(size, maxIndex);
        int res = 0;
        // 单调从大到小的栈
        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair(value));
        // 当循环到初始位置的时候停止
        while (index != maxIndex) {
            value = arr[index];
            while (!stack.isEmpty() && stack.peek().value < value) {
                int times = stack.pop().times;
                // 可见山峰数量为 2 * times（因为栈下面比当前元素大，当前的需要入栈的元素比当前值大，所以可见山峰是2，并且当前元素可能出现times重复的，所以为2*times） + internal(多个自身相同的元素，相互之间是可见的)
                res += getInternalSum(times) + times * 2; // 因为栈最下面有最大值兜底，并且当大的一个值进入栈的时候，那么当前的栈中的值有2个可以看见的山峰，一个下面的最大值，一个上面的待入栈的比当前值大的值,所以是2 * times（因为可能有相等的值即当前值有多个）
            }
            // 如果不大于栈顶的值
            if(!stack.isEmpty() && stack.peek().value == value) {
                // 如果等于当前值
                stack.peek().times++;
            } else {
                // 如果比栈顶的值小，直接入栈
                stack.push(new Pair(value));
            }
            // 获取下一个索引
            index = getNextIndex(size, index);
        }

        // 当数组遍历完成，如果栈中还有元素，那么肯定是从栈底到栈顶是从大到小的排列，所以需要弹栈，计算可见山峰的数量
        while (!stack.isEmpty()) {
            int times = stack.pop().times;
            // 加上内部的可见山峰数量
            res += getInternalSum(times);
            // 判断当前元素是否为栈中的最后一个元素
            if(!stack.isEmpty()) {
                // 不是最后一个元素，那么当前的次数最少还需要加上times（表示可见山峰数量）
                // 这个times表示的是已经出栈的那个元素作为当前元素的可见山峰，因为栈时从大到小排列的，所以出栈的元素肯定比当前的元素值大
                res += times;
                // 如果不是最后一个元素，那么判断当前元素是否为倒数第二个元素（即stack.size>=2, 还有2个，那么当前的元素是倒数第3个）
                if(stack.size() > 1) {
                    // 如果栈中还有>=2个的元素，那么当前元素的可见山峰个数还是 2 * times + internal(多个自身相同的元素的相互可见的数量)
                    res += times;
                } else {
                    // 如果是倒数第二个元素，那么当前元素的数量取决于栈底的元素的次数，如果有2个相同的栈底元素，那么次数为 2 * times + internal，如果只有1个相同到的栈底元素，那么当前元素的次数为: times + internal
                    // 因为倒数第二个元素为第二大的元素，它的可见山峰的次数取决于最大的元素的个数，如果最大元素的个数>1，那么最少也是2个或者大于2个，那么可见山峰的次数除了（多个自身相同的元素之外），再加上2个最大元素的可见山峰，否则只有1个最大的元素，那么就只有1个最大的可见山峰（因为第二个元素可能有多个，所以 times个可见山峰）
                    res += stack.peek().times > 1 ? times : 0;
                }
            }
        }
        return res;
    }

    /**
     * 计算该元素的内部的可见山峰的数量, 例如多个相同的元素，每个元素之间是可见的
     *
     */
    private static int getInternalSum(int n) {
        return n == 1 ? 0 : n * (n - 1) / 2;
    }

    /**
     * 获取下一个索引，因为是环装数组，所以这样获取
     */
    private static int getNextIndex(int size, int maxIndex) {
        return maxIndex + 1 < size ? maxIndex + 1 : 0;
    }


    public static class Pair {
        public int value; // 当前元素的值
        public int times; // 当前元素出现的个数（连续重复次数），默认为1

        public Pair(int value) {
            this.value = value;
            this.times = 1;
        }
    }

}
