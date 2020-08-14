package com.arithmetic.features.arithmetic.interview.summary;


/**
 * @author alan.chen
 * @date 2020/7/23 6:36 PM
 */
public class TreeNodeDemo {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);
        TreeNode node9 = new TreeNode(9);

        root.left = node2;
        root.right = node3;

        node2.left = node4;
        node2.right = node5;
        node2.father = root;

        node3.left = node6;
        node3.right = node6;
        node3.father = root;

        node4.left = node8;
        node4.father = node2;
        node5.father = node2;

        node6.right = node9;
        node6.father = node3;

        node7.father = node3;

        node8.father = node4;
        node9.father = node3;


        TreeNode treeNode = lowestCommonFatherNode(node5, node9);

        System.out.println(treeNode);
    }

    /**
     * 最近公共父节点
     * @param node1
     * @param node2
     * @return
     */
    private static TreeNode lowestCommonFatherNode(TreeNode node1, TreeNode node2) {
        if(node1 == null || node2 == null) {
            return null;
        }
        if(node1.father == node2.father) {
            return node1.father;
        }
        if(node1.father == node2) {
            return node2;
        }
        if(node1 == node2.father) {
            return node1;
        }
        TreeNode treeNode1 = lowestCommonFatherNode(node1.father, node2);
        if(treeNode1 != null) {
            return treeNode1;
        }
        TreeNode treeNode2 = lowestCommonFatherNode(node1, node2.father);
        return treeNode2;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode father;

        public TreeNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    '}';
        }
    }


}
