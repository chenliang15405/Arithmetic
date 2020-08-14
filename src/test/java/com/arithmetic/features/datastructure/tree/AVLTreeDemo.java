package com.arithmetic.features.datastructure.tree;

import org.junit.Test;

/**
 * 基于二叉排序树，如果是1,2,3,4,5,6这种连续的数据，那么二叉排序树就会变为只有右子树的结构，查询效率慢
 * 所以需要将这种不平衡的树调整为平衡二叉树（AVL树）
 *
 * 平衡二叉树：基于二叉排序树，所以也包含二叉排序的特点，它是一棵空树或者它的左右两个子树的的高度差不超过1，并且左右两颗
 *          子树都是一棵平衡二叉树
 *
 *
 * 平衡二叉树左旋转、右旋转、双旋转
 *
 * 左旋转的条件：当前节点（root节点）的（右子树的高度 - 左子树的高度）> 1，则需要左旋转
 * 右旋转的条件：当前节点（root节点）的（左子树的高度 - 右子树的高度）> 1，则需要右旋转
 * 双旋转的条件：（基于左旋转或者右旋转时，再做一次旋转）
 *    1.当前节点（root节点）的（右子树的高度 - 左子树的高度）> 1，并且当前节点的右子树的左子树高度 > 当前节点的右子树的右子树高度，则需要先对当前节点的右子节点发生右旋转，然后在对当前节点左旋转
 * 或者2. 当前节点（root节点）的（左子树高度 - 右子树的高度）> 1，并且当前节点的左子树的右子树高度 > 当前节点的左子树的左子树高度，则需要先对当前节点的左子节点发生左旋转，然后在对当前节点右旋转
 *
 *
 * 为什么在左旋转和旋转的时候还需要双旋转取旋转子树，因为左旋转或者右旋转时都会将当前节点（root节点）的右子树的左子树或者左子树的右子树移动到另外一边，所以可能会总是不平衡
 *
 *
 * @author alan.chen
 * @date 2020/5/31 9:22 PM
 */
public class AVLTreeDemo {

    @Test
    public void test() {
        //int[] arr = {4,3,6,5,7,8};
        //int[] arr = { 10, 12, 8, 9, 7, 6 };
        int[] arr = { 10, 11, 7, 6, 8, 9 }; // 这种情况需要双旋转才可以，因为需要右旋，但是

        AVLTree avlTree = new AVLTree();

        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }

        System.out.println("中序遍历");
        avlTree.infixOrder();

        System.out.println("在平衡处理~~");
        System.out.println("树的高度=" + avlTree.getRoot().height()); //3
        System.out.println("树的左子树高度=" + avlTree.getRoot().leftHeight()); // 2
        System.out.println("树的右子树高度=" + avlTree.getRoot().rightHeight()); // 2
        System.out.println("当前的根结点=" + avlTree.getRoot());//8
    }


    // 平衡二叉树
    class AVLTree {
        private Node root;

        public void add(Node node) {
            if(root == null) {
                root = node;
            } else {
                root.add(node);
            }
        }

        public Node getRoot() {
            return root;
        }

        public void infixOrder() {
            root.infixOrder();
        }


    }


    class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        /**
         * 二叉排序树节点添加
         * @param node
         */
        public void add(Node node) {
            if(node == null) {
                return;
            }
            // 如果需要添加的节点信息小于当前节点
            if(node.value < this.value) {
                // 则向左递归添加
                if(this.left == null) {
                    this.left = node;
                    return;
                }
                this.left.add(node);
            } else {
                // 如果需要添加的节点信息大于等于当前节点
                if(this.right == null) {
                    this.right = node;
                    return;
                }
                this.right.add(node);
            }

            /**
             * 双旋转
             */
            //当添加完一个结点后，如果: (右子树的高度-左子树的高度) > 1 , 左旋转
            if(rightHeight() - leftHeight() > 1) {
                if(this.right != null && this.right.leftHeight() > this.right.rightHeight()) {
                    // 如果当前节点的右子树的左子树高度>右子树的左子树高度
                    // 则需要以当前节点的右子树节点进行一次右旋转，否则将该子树旋转到右子树还是不平衡的状态
                    this.right.rightRotate();
                }
                leftRotate(); //左旋转
                return ; //必须要!!!
            }

            //当添加完一个结点后，如果 (左子树的高度 - 右子树的高度) > 1, 右旋转
            if(leftHeight() - rightHeight() > 1) {
                if(this.left != null && this.left.rightHeight() > this.left.leftHeight()) {
                    // 如果当前节点的左子节点的右子树高度>它左子树高度，先对当前节点的左子节点进行左旋一次
                    this.left.leftRotate();
                }
                //再对当前结点进行右旋转
                rightRotate();
            }
        }


        /**
         * 左旋转
         */
        public void leftRotate() {
            // 创建新节点，权值等于当前节点
            Node newNode = new Node(this.value);
            // 当前节点的左节点设置为第节点的左节点
            newNode.left = this.left;
            // 当前节点的右子树的左子树设置为新节点的右子树
            newNode.right = this.right.left;
            // 将当前节点的右子树设置为当前节点
            value = right.value;
            right = right.right;
            // 当前节点（已经设置为原子节点的右子节点了）的左子树设置为新节点，因为这样始终保证了二叉排序树特点，左边小右边大
            left = newNode;
        }

        /**
         * 右旋转
         */
        public void rightRotate() {
            // 创建新节点，权值等于当前节点
            Node newNode = new Node(this.value);
            // 当前节点的右节点设置为新节点的右节点
            newNode.right = this.right;
            // 当前节点的左子树的右子树设置为新节点的左子节点
            newNode.left = this.left.right;
            // 将当前节点的左子树的值和指向设置为当前节点
            value = left.value;
            left = left.left;
            // 将当前节点的右子树设置为新节点
            right = newNode;
        }


        /**
         * 计算当前节点树的高度
         */
        public int height() {
            return  Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
        }

        /**
         * 左子树高度
         * @return
         */
        public int leftHeight() {
            if(this.left == null) {
                return 0;
            }
            return this.left.height();
        }

        /**
         * 右子树高度
         * @return
         */
        public int rightHeight() {
            if(this.right == null) {
                return 0;
            }
            return this.right.height();
        }


        /**
         * 中序遍历
         */
        public void infixOrder() {
            if(this.left != null) {
                this.left.infixOrder();
            }
            System.out.println(this);
            if(this.right != null) {
                this.right.infixOrder();
            }
        }

        public Node search(int value) {
            Node searchNode = null;
            if(this.value == value) {
                return this;
            }
            if(value < this.value) {
                if(this.left != null) {
                    searchNode = this.left.search(value);
                }
            } else {
                if(this.right != null) {
                    searchNode = this.right.search(value);
                }
            }
            return searchNode;
        }

        /**
         * 查询需要删除节点的父节点
         * @param value
         */
        public Node searchParent(int value) {
            // 判断当前节点是否是删除节点的父节点
            if((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
                return this;
            }
            if(this.left != null && this.value > value) {
                // 如果该值是在左子树，则递归查找左子树
                return this.left.searchParent(value);
            } else if(this.right != null && this.value <= value){
                return this.right.searchParent(value);
            }
            // 没有找到父节点
            return null;
        }
    }
}
