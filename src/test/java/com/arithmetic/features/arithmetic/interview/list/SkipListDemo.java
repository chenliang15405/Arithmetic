package com.arithmetic.features.arithmetic.interview.list;


import java.util.ArrayList;
import java.util.List;

/**
 * 跳跃表
 *
 * @author tangsong
 * @date 2021/3/28 19:15
 */
public class SkipListDemo {

    /**
     * 跳跃表的节点对象
     */
    public static class SkipListNode {
        public Integer value;
        public List<SkipListNode> nextNodes;  // 索引表示层级，元素表示每个层级指向的next node节点

        public SkipListNode(Integer value) {
            this.value = value;
            this.nextNodes = new ArrayList<>();
        }
    }


    public static class SkipList {
        private SkipListNode head; // 系统最大， 表示头结点，只是用来记录当前跳跃表的最大层和做查找使用
        private int maxLevel;
        private int size;
        private static final double PROBABILITY = 0.5;

        public SkipList() {
            this.maxLevel = 0;
            this.size = 0;
            head = new SkipListNode(null);
            head.nextNodes.add(null);
        }

        public SkipListNode getHead() {
            return head;
        }

        public void add(Integer newValue) {
            if(!contains(newValue)) {
                size++;
                int level = 0;
                // 随机决定当前的数有多少层
                while (Math.random() > PROBABILITY) {
                    level++;
                    head.nextNodes.add(null); // 头结点也需要增加一层对应的节点
                }

                // 查找当前节点的位置，从最大的层数开始找，因为从最大层开始找会跳跃过多个节点，不能直接从当前节点随机出来的层数开始找，否则时间复杂度较高
                // 从最大的节点开始找，可以直接找到当前数需要的位置
                SkipListNode newNode = new SkipListNode(newValue);
                SkipListNode current = head;
                int allLevel = maxLevel;
                do {
                    current = findNext(newValue, current, allLevel);
                    // 当最大层数已经循环到当前节点的层数了，那么开始建立当前节点的跳跃表每一层的节点位置
                    if(level >= allLevel) {
                        // 因为是从大层数到小层数，那么就获取到当前层数的next节点，将新的节点插入到中间
                        // 这里使用的是add(0, xx) 每次对0位置进行插入，那么先插入的会被放入到列表的后面，对应的就是较高的层数，所以高层数的跑到上面了
                        newNode.nextNodes.add(0, current.nextNodes.get(level));
                        current.nextNodes.set(level, newNode); // 将新节点插入到当前节点的下一个，并且指定对应的层级
                        level--;
                    }
                } while (allLevel-- > 0);
            }
        }

        private boolean contains(Integer value) {
            // 找到比value大的节点的前一个节点，这个节点可能小于value也可能等于value
            SkipListNode node = find(value);
            return node != null && node.value != null && node.value.equals(value);
        }

        private SkipListNode find(Integer value) {
            return find(value, head, maxLevel);
        }

        private SkipListNode find(Integer e, SkipListNode current, int maxLevel) {
            do {
                // 从最大层开始找
                current = findNext(e, current, maxLevel);
            } while (maxLevel-- > 0);
            return current;
        }

        private SkipListNode findNext(Integer e, SkipListNode current, int level) {
            SkipListNode next = current.nextNodes.get(level);
            while (next != null) {
                Integer value = next.value;
                if(e < value) {
                    break;
                }
                current = next;
                next = current.nextNodes.get(level);
            }
            return current;
        }

    }


}
