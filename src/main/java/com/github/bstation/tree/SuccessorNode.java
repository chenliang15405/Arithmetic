package com.github.bstation.tree;

import java.lang.reflect.Parameter;

/**
 * @author tangsong
 * @date 2021/5/20 23:08
 */
public class SuccessorNode {

    /**
     * 获取某个节点的后继节点
     *  后继节点：中序遍历中的下一个节点
     *
     *  一个节点的后继节点，如果当前节点有右子节点，那么右子树的最左节点就是他的后续节点，因为中序遍历下，当前节点的下一个节点就是右子树的最左节点
     *  如果没有右子节点，那么就判断他是否是父节点的左子节点，如果是左子节点，那么后继节点就是父节点，如果是右子节点，那么后继节点就是父节点的父节点（判断父节点是否是父节点的父节点的左子节点或者右子节点）
     *
     */

    static class Node {
        public Node left;
        public Node right;
        public int value;
        public Node parent;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 找到某个节点的后继节点
     *
     */
    public Node getSuccessorNode(Node node) {
        if(node == null) {
            return node;
        }
        if(node.right != null) {
            // 右子树的最左子节点就是后继节点
            return getRightMostLeft(node.right);
        } else {
            // 判断当前节点是否为父节点的左子节点
            Node parent = node.parent;
            // 如果没有到达根节点，并且当前节点不是父节点的左子节点（如果是左子节点就直接返回parent，parent就是当前节点的后继节点）
            while (parent != null && parent.left != node) {
                // 如果当前节点是父节点的右子节点，那么就继续向上找
                node = parent;
                // 判断父节点是否是父节点的父节点的左子节点，因为只有当当前节点是父节点的左子节点，那么根据中序遍历，父节点就是当前节点的后继节点，否则
                // 根据中序遍历，右子节点的后继节点需要找到父节点的父节点（在父节点是父节点的左子节点的情况下才是，如果是右子节点，那么就需要继续向上找）
                parent = parent.parent;
            }
            return parent;
        }
    }

    private Node getRightMostLeft(Node node) {
        if(node == null) {
            return node;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

}
