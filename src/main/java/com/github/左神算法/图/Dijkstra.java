package com.github.左神算法.图;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author tangsong
 * @date 2021/6/20 0:06
 */
public class Dijkstra {

    /**
     * Dijkstra
     *
     *  最小生成树算法
     *
     *
     */


    /**
     * 选择某一个点开始，依次记录当前点到所有相邻的点的距离，如果比当前记录的小就更新，否则就不更新
     * 当前点的所有相邻的点统计完成之后，将当前的点锁定，然后挑选一个距离最小的点重复上述过程，每次统计完一个点，
     * 则将这个点就锁定，表示后续不会再选择到这个点
     *
     *  记录的距离就是初识开始遍历的点到这个点的距离
     *
     */
    public Map<Node, Integer> dijkstra1(Node from) {
        // 建立from到每个点的距离集合
        Map<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0); // from -> from 默认为0
        Set<Node> selectedNode = new HashSet<>();
        Node minNode = getMinDistanceAndUnSelectedNode(distanceMap, selectedNode);
        while (minNode != null) {
            // 获取from节点到当前节点的距离，后续到相邻点的距离都要加上这个距离，因为记录的本来就是from到每个点的距离
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if(!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                } else {
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            // 当前点统计完所有的相邻节点之后，加入到已选择集合中
            selectedNode.add(minNode);
            minNode = getMinDistanceAndUnSelectedNode(distanceMap, selectedNode);
        }
        return distanceMap;
    }

    private Node getMinDistanceAndUnSelectedNode(Map<Node, Integer> distanceMap, Set<Node> selectedNode) {
        Node minNode = null;
        int minStance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            Integer instance = entry.getValue();
            if(!selectedNode.contains(node) && instance < minStance) {
                minStance = instance;
                minNode = node;
            }
        }
        return minNode;
    }

}
