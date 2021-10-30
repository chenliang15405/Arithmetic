package com.github.左神算法.图;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author tangsong
 * @date 2021/6/19 19:02
 */
public class BFS {

    /**
     * 从图的某一个节点开始BFS
     *
     */
    public static void bfs(Node start) {
        if(start == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        // 使用set集合去重，防止重复bfs
        Set<Node> sets = new HashSet<>();
        queue.offer(start);
        sets.add(start);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.println(node.value);
            // 遍历相邻的节点
            for (Node next : node.nexts) {
                if(!sets.contains(next)) {
                    queue.offer(next);
                    sets.add(next);
                }
            }
        }
    }

}
