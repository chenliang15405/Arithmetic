package com.github.左神算法.图;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author tangsong
 * @date 2021/6/19 23:44
 */
public class Prim {

    /**
     * Prim算法
     *   最小生成树算法
     *
     *  1. 可以从任意节点触发来寻找最小生成树
     *  2. 某个点加入到被选取的点中后，解锁这个点触发的所有新的边
     *  3. 在所有解锁的边中选最小的边，然后看看这个边会不会形成环
     *  4. 如果会，不要当前边，继续考察剩下解锁的变种最小的边，重复3
     *  5. 如果不会，要当前边，将该边指向点加入到被选取的点中，重复2
     *  6. 当所有点被选取，最小生成树就得到了
     *
     */

    /**
     * 从某一个点开始，找到最小的边，并解锁这个点对应的所有边，然后再选取最小的，但是需要保证不会选择重复的点，否则就构成了环
     *  也是贪心
     *
     */
    public Set<Edge> primMST(Graph graph) {
        // 记录每次选取的最小的边
        PriorityQueue<Edge> queue = new PriorityQueue<>((o1, o2) -> o1.weight - o2.weight);
        // 记录不重复的点
        Set<Node> set = new HashSet<>();
        // 记录结果
        Set<Edge> result = new HashSet<>();

        // 从任意的一个点开始
        for (Node node : graph.nodes.values()) {
            if(!set.contains(node)) {
                set.add(node);
                // 找到这个node对应的所有边
                for (Edge edge : node.edges) {
                    queue.offer(edge);
                }
                while (!queue.isEmpty()) {
                    Edge edge = queue.poll();
                    Node toNode = edge.to;
                    if(!set.contains(toNode)) {
                        result.add(edge);
                        set.add(toNode);
                        // 找到这个node的所有边并加入队列
                        for (Edge edge1 : toNode.edges) {
                            queue.offer(edge1);
                        }
                    }
                }
            }
            // 遍历完一次就可以break了，因为里面的过程已经将所有的点找到了
            break;
        }
        return result;
    }


}
