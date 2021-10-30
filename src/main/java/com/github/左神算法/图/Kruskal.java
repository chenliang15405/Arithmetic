package com.github.左神算法.图;

import java.util.*;

/**
 * @author tangsong
 * @date 2021/6/19 23:01
 */
public class Kruskal {

    /**
     * Kruskal
     *   最小生成树算法
     *
     *   1. 总是从权值最小的边开始考虑，依次考察权值依次变大的边
     *   2. 当前的边要么进入最小的生成树集合，要么丢弃
     *   3. 如果当前的边进入最小的生成树集合中不会形成环，就要当前边
     *   4. 如果当前边进入最小生成树的集合中会形成环，就不要当前边
     *   5. 考察完所有边之后，最小生成树的集合也得到了
     *
     *  使用并查集表示节点是否在同一个集合，判断是否会形成环
     *
     */

    public static Set<Edge> kruskalMst(Graph graph) {
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        // 构建堆，用来放权值最小的边
        PriorityQueue<Edge> queue = new PriorityQueue<>((o1, o2) -> o1.weight - o2.weight);
        for (Edge edge : graph.edges) {
            queue.offer(edge);
        }

        Set<Edge> result = new HashSet<>();
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            // 判断当前这个边的两个节点是否已经在同一个集合，如果在同一个集合则不能使用，会成环
            if(!unionFind.isSameSet(edge.from, edge.to)) {
                result.add(edge);
                // 将这两个节点合并为一个集合
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }

    public static class UnionFind {
        public Map<Node, Node> parents;
        public Map<Node, Integer> sizeMap;

        public UnionFind() {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        /**
         * 构建并查集
         */
        public void makeSets(Collection<Node> nodes) {
            parents.clear();
            sizeMap.clear();
            for (Node node : nodes) {
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(Node a, Node b) {
            Node aHead = find(a);
            Node bHead = find(b);
            return aHead == bHead;
        }

        public void union(Node a, Node b) {
            Node aHead = find(a);
            Node bHead = find(b);
            if(aHead != bHead) {
                Integer aSize = sizeMap.get(aHead);
                Integer bSize = sizeMap.get(bHead);
                if(aSize >= bSize) {
                    parents.put(bHead, aHead);
                    sizeMap.put(aHead, aSize + bSize);
                    sizeMap.remove(bHead);
                } else {
                    parents.put(aHead, bHead);
                    sizeMap.put(bHead, aSize + bSize);
                    sizeMap.remove(aHead);
                }
            }
        }

        private Node find(Node node) {
            List<Node> list = new ArrayList<>();
            while (node != parents.get(node)) {
                list.add(node);
                node = parents.get(node);
            }
            for (Node cur : list) {
                parents.put(cur, node);
            }
            return node;
        }

    }

}
