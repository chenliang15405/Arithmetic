package com.github.左神算法.图;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/6/19 19:01
 */
public class DFS {

    /**
     * 从图的某一个节点开始DFS
     *
     */
    public static void dfs(Node start) {
        if(start == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        // set用来去重
        Set<Node> sets = new HashSet<>();
        stack.push(start);
        sets.add(start);
        // 入栈的时候就打印
        System.out.println(start.value);

        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                // 判断相邻的节点是否已经入栈过
                if(!sets.contains(next)) {
                    // 如果有新的节点，则将当前节点重入加入栈中，后续回来的时候再判断是否有其他节点
                    stack.push(cur); // 需要将当前节点也加入，否则dfs后面的就找不到当前节点了
                    stack.push(next); // 将新的节点加入
                    System.out.println(next.value);
                    break; // 停止当前节点其他邻居的遍历，直接遍历下一个节点的相邻节点
                }
            }
        }
    }

}
