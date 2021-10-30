package com.github.左神算法.并查集;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/6/15 23:11
 */
public class UnionFind {

    /**
     * 并查集
     *   是一种特殊的集合，每个集合的头结点为代表节点
     *
     *  支持两种操作：
     *  1. 查询两个元素是否在一个集合
     *  2. 将两个元素所在的集合合并为一个集合
     *
     *  两个操作在均摊下来之后单次是O(1)的时间复杂度 ---> 并查集
     *
     */

    /**
     * 封装元素的一个对象
     */
    public static class Node<T> {
        public T value;

        public Node(T value) {
            this.value = value;
        }
    }

    /**
     * 并查集的结构定义
     */
    public static class UnionSet<T> {
        public Map<T, Node<T>> nodes; // 定义元素和节点对象的映射关系集合
        public Map<Node<T>, Node<T>> parents; // 当前元素 和 直接父元素的关系 （充当指针）
        public Map<Node<T>, Integer> sizeMap; // key - 当前结合的代表节点，value - 当前集合的大小

        public UnionSet(List<T> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            // 初始化map中的数据
            for (T value : values) {
                Node<T> node = new Node<>(value);
                nodes.put(value, node); // 映射关系
                parents.put(node, node); // 初始的状态下，每个元素的父节点是自己
                sizeMap.put(node, 1); // 初始状态，每个集合的当前节点是当前节点，集合大小是1
            }
        }

        /**
         * 给定一个节点，找到当前节点所在集合的代表节点
         *
         *   优化点： 如果某个集合的一个链路特别长，那么每次这样从一个节点一直向上找的过程比较耗费时间，做重复的动作
         *           在第一次寻找过程中，将当前寻找过程的一条链路中，每个节点都打平，将当前链路的节点都直接记录到当前集合的代表节点下
         *           这样做了之后，在后面的查找过程中，只需要找一次就可以将当前节点所在集合的代表节点找到，非常快。
         *
         *           每个链路都这样做了之后，查找的速度就会提高，每个链路只有第一次是需要查找过多的节点
         *
         */
        public Node<T> findFather(Node<T> cur) {
            // 使用一个栈，将当前寻找过程中的链路全部记录下来
            Stack<Node<T>> path = new Stack<>();
            // 如果某个节点的parent节点是自己，那么这个节点就是集合的代表节点
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            // 将当前链路上寻找的节点都直接记录到当前集合的代表节点下
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            // cur就是当前集合的代表节点
            return cur;
        }


        /**
         * 判断a元素和b元素是否在同一个集合
         *
         */
        public boolean isSameSet(T a, T b) {
            // 先找到 a 和 b对应的Node节点对象，然后找到各自所在集合的代表节点，并判断是否一致
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        /**
         * 合并两个元素所在的集合
         *
         *  优化点：将小集合挂载到大集合的下面，减少链变长的速度
         *
         */
        public void union(T a, T b) {
            Node<T> aHead = findFather(nodes.get(a));
            Node<T> bHead = findFather(nodes.get(b));
            // 判断当前 a 和 b是否已经是一个集合了，不是一个集合才需要合并
            if(aHead != bHead) {
                // 获取 a 集合 和 b集合的大小
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                // 将小集合挂载到大集合的下面
                Node<T> big = aSetSize > bSetSize ? aHead : bHead;
                Node<T> small = big == aHead ? bHead : aHead;
                // 更新维护的关系
                parents.put(small, big);
                // 更新 size map中的关系
                sizeMap.put(big, aSetSize + bSetSize);
                // 删除sizeMap中小集合的维护的关系，sizeMap中只维护当前集合的代表结点的大小
                sizeMap.remove(small);
            }
        }
    }

}
