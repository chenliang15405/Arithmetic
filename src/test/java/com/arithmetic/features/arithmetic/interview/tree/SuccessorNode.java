package com.arithmetic.features.arithmetic.interview.tree;

/**
 * 找到二叉树的指定节点的后继节点
 *
 *
 * 后继节点：中序遍历中一个节点的后面的节点
 * 前驱节点：中序遍历中一个节点的前面的节点
 *
 *
 * @author alan.chen
 * @date 2020/6/8 11:34 PM
 */
public class SuccessorNode {


    public static class Node {
        public String value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(String value) {
            this.value = value;
        }
    }


}
