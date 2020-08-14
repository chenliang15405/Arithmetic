package com.arithmetic.features.datastructure.tree;

import org.junit.Test;

/**
 *
 * 顺序存储二叉树：就是数组的存储方式和树的存储方式可以转换
 *
 * 仍然可以前序遍历、中序遍历、后序遍历
 *
 * 顺序存储二叉树通常只考虑完全二叉树
 * 特点：
 *  第n个元素的父节点：(n-1) / 2
 *  第n个元素的左子节点: 2n + 1
 *  第n个元素的右子节点: 2n + 2
 *
 * @author alan.chen
 * @date 2020/5/30 8:23 PM
 */
public class ArrBinaryTreeDemo {

    @Test
    public void test() {
        int[] arr = {1, 3, 6, 8, 10, 14};

        ArrBinaryTree tree = new ArrBinaryTree(arr);

        //tree.preOrder();

        tree.infixOrder(0);
    }


}


// 顺序存储二叉树，和数组相互转换
class ArrBinaryTree {
    private int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }


    public void preOrder() {
        preOrder(0);
    }

    /**
     * 前序遍历
     *
     * index表示从哪个节点开始遍历
     */
    public void preOrder(int index) {
        if(arr == null || arr.length < 1) {
            return;
        }
        // 输出当前节点
        System.out.println(arr[index]);
        // 递归遍历左子树
        if((index * 2 + 1) < arr.length) {
            preOrder(index * 2 + 1);
        }
        // 递归遍历右子树
        if((index * 2 + 2) < arr.length) {
            preOrder(index * 2 + 2);
        }
    }

    public void infixOrder(int index) {
        if(arr == null || arr.length < 1) {
            return;
        }

        if((index * 2 + 1) < arr.length) {
            infixOrder(index * 2 + 1);
        }
        System.out.println(arr[index]);

        if((index * 2 + 2) < arr.length) {
            infixOrder(index * 2 + 2);
        }
    }


}