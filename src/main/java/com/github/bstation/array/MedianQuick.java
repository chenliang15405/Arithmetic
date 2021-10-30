package com.github.bstation.array;

import sun.nio.cs.ext.MacArabic;

import java.util.PriorityQueue;

/**
 * @author tangsong
 * @date 2021/8/28 1:10
 */
public class MedianQuick {

    /**
     *  实现一个结构，返回数据流中的中位数
     *
     *  数据流的长度不确定，会调用add方法添加元素，并且任意时刻获取中位数时，需要正确的返回当前元素列表的中位数
     *
     *  [2,3,4] 的中位数是 3
     *  [2,3] 的中位数是 (2 + 3) / 2 = 2.5
     *
     *  两种方式：
     *   1. 使用数组将所有的数保存，在需要的时候排序并返回中位数，时间复杂度O(N*logN)，并且每次查询都需要排序
     *   2. 使用大根堆和小根堆用于保存每次吐出来的数据，大根堆中保存小于堆顶的所有的数，小根堆保存的是大于堆顶的所有的数，
     *      并保证大根堆和小根堆的数量不超过1，每次在计算中位数逇时候，只需要判断当前的数量是偶数还是奇数，如果是偶数就是
     *      堆顶的和/2，如果是奇数，则是数量较多的堆的堆顶
     */



    /**
     *  数据流中位数
     */
    public static class MedianFinder {
        // 小根堆
        PriorityQueue<Integer> minQueue = new PriorityQueue<>((a, b) -> a - b);
        // 大根堆
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>((a, b) -> b - a);


        public void addNum(int num) {
            // 判断大根堆是否为空
            if(maxQueue.isEmpty()) {
                maxQueue.offer(num);
                return;
            }
            // 判断当前数是否比大根堆堆顶小，如果小则插入到大根堆
            if(num <= maxQueue.peek()) {
                maxQueue.offer(num);
            } else {
                // 判断小根堆是否为空
                if(minQueue.isEmpty()) {
                    minQueue.offer(num);
                    return;
                }

                // 否则判断当前数是否大于小根堆的堆顶，因为小根堆中只维护大于堆顶的数
                if(num > minQueue.peek()) {
                    minQueue.offer(num);
                } else {
                    maxQueue.offer(num);
                }
            }
            // 调整两个堆的大小
            modifyHeapSize();
        }

        public Integer getMedia() {
            if(minQueue.isEmpty() && maxQueue.isEmpty()) {
                return null;
            }
            if(minQueue.isEmpty() || maxQueue.isEmpty()) {
                return minQueue.isEmpty() ? maxQueue.peek() : minQueue.peek();
            }
            int minQueueSize = minQueue.size();
            int maxQueueSize = maxQueue.size();
            Integer minQueueHead = minQueue.peek();
            Integer maxQueueHead = maxQueue.peek();

            // 判断当前是奇数还是偶数
            if(((minQueueSize + maxQueueSize) & 1) == 0) {
                // 偶数，则堆顶数和/2
                return (minQueueHead + maxQueueHead) / 2;
            }
            // 奇数，则堆元素多的堆顶就是中位数
            return maxQueueSize > minQueueSize ? maxQueueHead : minQueueHead;
        }


        private void modifyHeapSize() {
            while(minQueue.size() >= maxQueue.size() + 2) {
                maxQueue.offer(minQueue.poll());
            }
            while(minQueue.size() + 2 <= maxQueue.size()) {
                minQueue.offer(maxQueue.poll());
            }
        }
    }


}
