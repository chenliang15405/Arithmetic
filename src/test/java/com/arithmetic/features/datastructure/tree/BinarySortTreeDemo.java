package com.arithmetic.features.datastructure.tree;

import org.junit.Test;

/**
 * 二叉排序树(BST)：左子节点小于当前节点，右子节点大于当前节点（如果有相等的值，可以放在左边或者右边）
 * 搜索二叉树
 *
 * 怎么判断是二叉排序树：中序遍历是升序的则是二叉排序树
 *
 * 能够高效的对数据实现查询和增删
 *
 * 二叉排序树的增删、查都很快，因为在查询的时候会查询根节点，比较大小，如果大则查找右子树，如果小则查找左子树，
 * 所以每次都是二分树的节点查找
 *
 *
 * 二叉排序树的创建（添加）、遍历
 *
 * 二叉排序树的删除：
 *  1. 如果删除的是叶子节点
 *  2. 如果删除的是只有一棵子树的节点
 *  3. 如果删除的是有两颗子树的节点（可以使用两种方式实现）
 *
 *
 * @author alan.chen
 * @date 2020/5/31 5:59 PM
 */
public class BinarySortTreeDemo {

    @Test
    public void test() {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};

        BinarySortTree binarySortTree = new BinarySortTree();

        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        System.out.println("中序遍历");  //1 ,2 ,3, 5,7,9,10,12
        binarySortTree.infixOrder();


        // 删除节点
        binarySortTree.delNode(5);
        binarySortTree.delNode(10);
        binarySortTree.delNode(2);
        binarySortTree.delNode(3);

        binarySortTree.delNode(9);
        binarySortTree.delNode(1);
        binarySortTree.delNode(7);

        System.out.println("删除结点后");
        binarySortTree.infixOrder();

    }


    // 二叉排序树
    class BinarySortTree {
        private Node root;


        /**
         * 创建二叉排序树
         * @param node 需要添加的节点
         */
        public void add(Node node) {
            if(root == null) {
                root = node;
            } else {
                root.add(node);
            }
        }


        /**
         * 中序遍历
         */
        public void infixOrder() {
            if(root != null) {
                root.infixOrder();
            } else {
                System.out.println("空树");
            }
        }


        /**
         * 删除节点
         * //1. 删除的是叶子节点，则直接删除
         * //2. 删除的是节点上只有一个叶子节点，则将该节点放在当前节点
         * //3. 如果删除的是有两颗子树的节点（可以使用两种方式实现）
         *       // 将该节点的右子树的最小值删除，将该值放在删除节点上
         *       // 或者将该节点的左子树的最大值节点删除，将该值放在删除节点上
         *
         *  思路： 先找到需要删除的节点
         *        找到需要删除的节点的父节点
         *        根据该节点是否有子树决定如何删除节点
         *        根据该节点是否是root节点做处理
         *
         *
         *
         * @param value 需要删除的值
         */
        public void delNode(int value) {
            if(root == null) {
                System.out.println("空树");
                return;
            }
            // 查找需要的删除节点
            Node node = root.search(value);
            if(node == null) {
                return;
            }
            // 查询需要删除节点的父节点
            Node parentNode = root.searchParent(value);
            if(parentNode == null) {
                System.out.println("当前删除节点为Root节点");
            }

            // 判断当前删除的节点是否是叶子节点
            if(node.left == null && node.right == null) {
                // 判断当前删除的节点是父节点的左子节点还是右子节点
                if(parentNode != null && parentNode.left != null && parentNode.left.value == node.value) {
                    parentNode.left = null;
                }
                if(parentNode != null && parentNode.right != null && parentNode.right.value == node.value) {
                    parentNode.right = null;
                }
            } else if(node.left != null && node.right != null) {
                // 删除节点有2棵子树
                int minValue = delRightMinNode(node);
                // 将该值替换当前删除节点的值
                node.value = minValue;
            } else {
                // 如果删除的节点是root节点，并且root节点有一棵子树
                if(parentNode == null) {
                    if(node.left != null) {
                        root = node.left;
                    }else {
                        root = node.right;
                    }
                    return;
                }
                // 如果删除节点有一棵子树，则将该节点替换到删除节点位置
                if(parentNode.left != null && parentNode.left.value == node.value) {
                    // 如果父节点的左子树是当前节点
                    // 如果该节点的子树是左子树
                    if(node.left != null) {
                        parentNode.left = node.left;
                    }
                    // 如果该节点的子树是左子树
                    if(node.right != null) {
                        parentNode.left = node.right;
                    }
                } else if(parentNode.right != null && parentNode.right.value == node.value) {
                    // 如果父节点的右子树是当前节点
                    // 如果该节点还有右子树
                    if(node.left != null) {
                        parentNode.right = node.left;
                    }
                    if(node.right != null) {
                        parentNode.right = node.right;
                    }
                }

            }


        }

        /**
         * 找到当前节点的右子树的最小节点，并删除，记录该值，并返回
         * @param node
         */
        private Integer delRightMinNode(Node node) {
            if(node.right != null) {
                Node minNode = node.right;
                while (minNode.left != null) {
                    minNode = minNode.left;
                }

                // 找到最小节点并记录
                int minValue = minNode.value;
                delNode(minValue);
                return minValue;
            }
            return null;
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
