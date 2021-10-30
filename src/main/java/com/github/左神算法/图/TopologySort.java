package com.github.左神算法.图;

import java.util.*;

/**
 * @author tangsong
 * @date 2021/6/19 19:35
 */
public class TopologySort {

    /**
     * 图的拓扑排序算法
     * 拓扑排序
     *
     *  1. 在图中找到所有入度为0的点输出
     *  2. 把所有入度为0的点在图中删除，继续找入度为0的点输出，周而复始
     *  3. 图的所有点被删除之后，依次输出的顺序就是拓扑排序
     *
     *  如果一个图是拓扑结构，那么这个图一定是：有向图并且没有坏！！！
     *
     */


    /**
     * 给定一个图，返回这个图中的拓扑排序
     *
     *  拓扑结构，那么这个图就是一个有序无环图
     *
     *  先找到入度为0的点，然后再图中删除这个点的影响，然后继续找到入度为0的点，这个顺序就是拓扑排序
     *
     */
    public List<Node> topologySort(Graph graph) {
        // 使用map记录每个节点的点次
        Map<Node, Integer> map = new HashMap<>();
        // 使用队列记录当前入度为0的点，即拓扑排序中优先的点
        Queue<Node> queue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            map.put(node, node.in);
            if(node.in == 0) {
                queue.offer(node);
            }
        }
        List<Node> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            result.add(temp);
            // 遍历当前节点的相邻的节点，并消除当前节点的影响
            for (Node next : temp.nexts) {
                // 将相邻节点的入度 - 1
                map.put(next, map.get(next) - 1);
                if(map.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }
        return result;
    }



}
