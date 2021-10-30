package com.github.左神算法.图;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 图结构描述
 *
 * @author tangsong
 * @date 2021/6/19 18:54
 */
public class Graph {
    public Map<Integer, Node> nodes; // 节点值和节点Node对象的映射
    public Set<Edge> edges; // 图中所有的边

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
