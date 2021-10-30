package com.github.tuling;

import org.junit.Test;

import java.util.*;

/**
 * @author tangsong
 * @date 2021/5/7 0:28
 */
public class HorseRacing {

    /**
     * 田忌赛马  （贪心算法）
     *
     *  给定A和B两个数组，大小相等，A相对于B的优势可以使用A[i] > B[i]的索引i的数目来描述
     *
     *   返回A的任意排列，使其相对于B的优势最大化
     *
     */


    /**
     * 实质就是只要A中的元素比B大就算是优势，不计算具体大多少，所以需要A中的元素的排列尽可能的比B中元素的大，
     *  让A中小的排列到B中大的元素的位置上，让A中等的排列到B中的小的元素上，让A中大的排列大B中中等的元素上，可以达到最多的优势
     *
     *  对A和B进行排序，然后从小到大的比较，如果A中小的元素已经小于B中当前位置的元素了，那么就记下来A元素，这个元素已经没有优势了，所以可以用来去比较B中较大的元素
     *  然后叫A中大于B的元素依次排列
     *  如果A中的元素大于B中的元素，那么就将A中的元素记录到该元素上（使用Map），并且记录时采用链表结构，防止有多个相同的B元素
     *
     */

    @Test
    public void test() {
        int[] A = new int[]{10,24,8,32};
        int[] B = new int[]{13,25,25,11};

        System.out.println(Arrays.toString(advantageCount(A, B)));
    }


    private int[] advantageCount(int[] A, int[] B) {
        // 需要对B进行clone操作，因为最后还需要B的原始顺序组合A的排列顺序
        int[] sortB = B.clone();
        Arrays.sort(sortB);
        Arrays.sort(A);

        Map<Integer, Deque<Integer>> bMap = new HashMap<>();

        Deque<Integer> aQueue = new LinkedList<>();

        // 循环A数组
        int index = 0;
        for (int a : A) {
            if(a > sortB[index]) {
                // 将B对应的比它大的元素保存到map中
                Deque<Integer> queue = bMap.getOrDefault(sortB[index], new LinkedList<>());
                queue.offer(a);
                bMap.put(sortB[index++], queue);
            } else {
                // 将不大于B中任何元素的a元素记录到此队列中，用作排列到B中最大的元素对应
                aQueue.offer(a);
            }
        }

        // 遍历原始B数组，组合A的新数组
        int[] aNew = new int[B.length];
        for (int i = 0; i < B.length; i++) {
            Deque<Integer> linkedList = bMap.get(B[i]);
           if(linkedList != null && !linkedList.isEmpty()) {
               aNew[i] = linkedList.removeLast();
           } else {
               aNew[i] = aQueue.removeLast();
           }
        }
        return aNew;
    }

}
