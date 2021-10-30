package com.github.左神算法.图;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/6/19 21:19
 */
public class TopologicalOrderDFS1 {

    /**
     * lintcode 拓扑排序
     *
     */


    // 给定义的图结构
    public static class DirectedGraphNode {
        public int label;
        public List<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int label) {
            this.label = label;
            neighbors = new ArrayList<>();
        }
    }

    /**
     * 使用对象记录每个点以及这个点下面包含的所有点数量
     *
     * 如果A点包含一共包含5个点数量，B点一共包含3个点数量，那么拓扑排序中，A <= B
     *
     */
    public static class Record {
        public DirectedGraphNode node; // 当前节点
        public long nodes; // 当前节点直接出发可以走到的所有点的数量

        public Record(DirectedGraphNode node, long nodes) {
            this.node = node;
            this.nodes = nodes;
        }
    }


    /**
     * 拓扑排序
     *
     */
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // 构建一个Map, 这个map保存每个节点对应的包含的数量，并且可以用作在递归中的缓存，防止重复计算
        Map<DirectedGraphNode, Record> map = new HashMap<>();
        List<Record> list = new ArrayList<>();
        for (DirectedGraphNode node : graph) {
            // 计算图中每个节点的Record对象
            list.add(f(node, map));
        }
        // 根据节点的数量进行排序，大的节点则拓扑排序较小
        list.sort((o1, o2) -> {
            return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
        });
        ArrayList<DirectedGraphNode> result = new ArrayList<>();
        for (Record record : list) {
            // 将排好序的节点放入集合
            result.add(record.node);
        }
        return result;
    }

    /**
     * 计算当前节点对应的Record对象
     */
    private Record f(DirectedGraphNode node, Map<DirectedGraphNode, Record> map) {
        // 如果缓存中有，则直接从缓存中获取
        if(map.containsKey(node)) {
            return map.get(node);
        }
        // 如果需要计算当前节点所包含的节点数量，那么就获取当前节点的所有相邻节点所包含的节点数量
        long nodes = 0;
        for (DirectedGraphNode next : node.neighbors) {
            nodes += f(next, map).nodes;
        }
        // 构建自己的对象
        Record record = new Record(node, nodes + 1);
        map.put(node, record);
        return record;
    }


}
