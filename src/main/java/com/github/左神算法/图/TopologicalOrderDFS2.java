package com.github.左神算法.图;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/6/19 21:19
 */
public class TopologicalOrderDFS2 {

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
     *  如果A节点在图中递归的深度比B节点的深度更深，那么A节点的拓扑排序： A <= B
     *
     */
    public static class Record {
        public DirectedGraphNode node; // 当前节点
        public int deep; // 当前节点直接出发可以走到的所有点的数量

        public Record(DirectedGraphNode node, int nodes) {
            this.node = node;
            this.deep = nodes;
        }
    }


    /**
     * 拓扑排序
     *
     */
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        Map<DirectedGraphNode, Record> map = new HashMap<>();
        List<Record> list = new ArrayList<>();
        for (DirectedGraphNode node : graph) {
            list.add(f(node, map));
        }
        list.sort((o1, o2) -> {
            return o2.deep - o1.deep;
        });
        ArrayList<DirectedGraphNode> result = new ArrayList<>();
        for (Record record : list) {
            result.add(record.node);
        }
        return result;
    }

    /**
     * 计算当前节点对应的Record对象
     */
    private Record f(DirectedGraphNode node, Map<DirectedGraphNode, Record> map) {
        if(map.containsKey(node)) {
            return map.get(node);
        }
        int maxDeep = 0;
        // 如果要计算当前节点的深度，那么就计算当前节点的相邻节点的深度，+1就是当前节点的深度
        for (DirectedGraphNode next : node.neighbors) {
            maxDeep = Math.max(maxDeep, f(next, map).deep);
        }
        // 构建当前节点的深度
        Record record = new Record(node, maxDeep + 1);
        map.put(node, record);
        return record;
    }


}
