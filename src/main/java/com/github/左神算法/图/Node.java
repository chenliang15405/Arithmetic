package com.github.左神算法.图;

import java.util.ArrayList;
import java.util.List;

/**
 * 图上的节点结构描述
 *   节点值
 *   邻近的边
 *   相邻的节点
 *
 * @author tangsong
 * @date 2021/6/19 16:51
 */
public class Node {
    public int value;
    public int in;  // 有多少个点是直接指向当前点
    public int out; // 当前这个点直接指向多少个其他的点
    public List<Node> nexts; // 有多少个邻居点
    public List<Edge> edges; // 从当前点出发有哪些直接的边

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
