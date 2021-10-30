package com.github.左神算法.图;


/**
 * @author tangsong
 * @date 2021/6/19 18:56
 */
public class GraphGenerator {

    /**
     * 根据给定的数据，生成一个图
     *
     * martix是一个矩阵，其中每一行数据表示为：第一个元素为边的权重，第二个元素表示为一个点，第三个元素表示为另一个目的地的点
     *
     * 请根据矩阵生成一个图结构
     *
     */


    /**
     * [ [3,0,2], ... ]
     *
     *  表示为当前边的权重是3，from节点的值为0，to节点的值为2
     *
     */
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            // 拿到每一行的3个元素
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];
            // 判断图中是否已经存在该元素
            if(!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if(!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            // 找到图中的点, 无论当前是否已经存在都需要更新数据
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge edge = new Edge(weight, fromNode, toNode);
            // 更新点的数据
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;

            fromNode.edges.add(edge);
            graph.edges.add(edge);
        }
        return graph;
    }

}
