package com.github.左神算法.图;

/**
 * 图结构中的边结构描述
 *
 * @author tangsong
 * @date 2021/6/19 18:53
 */
public class Edge {
    public int weight; // 边的权重
    public Node from; // 边的from点
    public Node to; // 边的to点

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
