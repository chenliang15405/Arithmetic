package com.github.bstation.tpok;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/5/23 17:08
 */
public class TopRecord {

    /**
     * 请实现以下的结构：
     *  TopRecord {
     *      public TopRecord(int k) : 构造时可以事先指定好k的大小，构造后就固定不变了
     *      public void add(String str) : 向该结构中加入一个字符串，可以重复加入
     *      public List<String> top() : 返回之前加入的所有字符串中，词频最大的前K个
     *  }
     *
     *  要求：
     *  add方法，复杂度O(logK)
     *  top方法 复杂度O(K)
     *
     */


    public static class Node {
        public String str;
        public int times;

        public Node(String str, int times) {
            this.str = str;
            this.times = times;
        }
    }

    public static class TopKRecord {
        public Node[] heap; // 小根堆
        public int heapSize; // 堆的大小
        public Map<String, Node> strNodeMap; // 字符串 节点map, 记录当前的字符串对应的词频
        public Map<Node, Integer> nodeIndexMap; // 字符串 堆位置map, 记录指定的字符串在堆上的位置

        public TopKRecord(int K) {
            this.heap = new Node[K];
            this.heapSize = K;
            this.strNodeMap = new HashMap<>();
            this.nodeIndexMap = new HashMap<>();
        }

        /**
         * 添加到堆中，判断当前的字符串是否在词频表中，如果不在，则进入到词频表中，并且不在堆上，那么就判断当前的词频和小根堆的堆顶的词频的大小
         * 如果大于小根堆的堆顶，则交换位置，并调整小根堆
         */
        public void add(String str) {
            Node curNode = null;
            int preIndex = -1;
            // 词频表中是否有该字符串
            if(!strNodeMap.containsKey(str)) {
                curNode = new Node(str, 1);
                strNodeMap.put(str, curNode);
                nodeIndexMap.put(curNode, -1);
            } else {
                // 词频增加
                curNode = strNodeMap.get(str);
                curNode.times++;
                preIndex = nodeIndexMap.get(curNode);
            }
            // 调整堆中的词频字符串
            if (preIndex == -1) {
                // 表示当前是新的字符串，没有在堆上
                if(heapSize == heap.length) {
                    // 堆已经满了
                    if(heap[0].times < curNode.times) {
                        nodeIndexMap.put(heap[0], -1);
                        nodeIndexMap.put(curNode, 0);
                        heap[0] = curNode;
                        heapify(0, heapSize);
                    }
                } else {
                    // 堆没有满
                    nodeIndexMap.put(curNode, heapSize);
                    heap[heapSize] = curNode;
                    heapInsert(heapSize++);
                }
            } else {
                // 当前字符串已经在堆上了，词频已经新增了，调整小根堆
                heapify(preIndex, heapSize);
            }

        }

        private void heapInsert(int i) {

        }


        private void heapify(int index, int heapSize) {
            int l = index * 2 + 1;
            int r = index * 2 + 2;
            int smallest = index;
            while (l < heapSize) {
                if(heap[l].times < heap[index].times) {
                    smallest = l;
                }
                if(r < heapSize && heap[r].times < heap[smallest].times) {
                    smallest = r;
                }
                if(smallest != index) {
                    swap(smallest, index);
                } else {
                    break;
                }
                index = smallest;
                l = index * 2 + 1;
                r = index * 2 + 2;
            }
        }

        private void swap(int index1, int index2) {
            nodeIndexMap.put(heap[index1], index2);
            nodeIndexMap.put(heap[index2], index1);
            Node temp = heap[index1];
            heap[index1] = heap[index2];
            heap[index2] = temp;
        }


    }




}
