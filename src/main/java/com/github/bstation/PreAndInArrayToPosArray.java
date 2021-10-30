package com.github.bstation;

import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;

/**
 * @author tangsong
 * @date 2021/5/27 23:39
 */
public class PreAndInArrayToPosArray {

    /**
     *
     * 已知一棵二叉树中没有重复节点，并且给定了这棵树的中序遍历数组和先序遍历数组，返回后序遍历数组
     * 比如给定：
     * int[] pre = {1,2,4,5,3,6,7}
     * int[] in = {4,2,5,1,6,3,7}, 返回
     * {4,5,2,6,7,3,1}
     *
     *  需要递归进行解决，宏观思维，正确的base case
     *  (主要是根据前序、中序、后序的特点操作，因为前序的第一个数是根节点，那么根节点在后序中是最后一个，根节点在中序中是中间的值)
     *
     */

    @Test
    public void test() {
        int [] pre = new int[] {1,2,4,5,3,6,7};
        int [] in = new int [] {4,2,5,1,6,3,7};

        System.out.println(Arrays.toString(getPosOrder(pre, in)));

        System.out.println(Arrays.toString(getPosWithCache(pre, in)));

    }


    private int[] getPosOrder(int[] pre, int[] in) {
        if(pre == null || in == null) {
            return null;
        }
        int N = pre.length;
        int[] pos = new int[N];

        process1(pre, 0, N-1, in, 0, N-1, pos, 0, N-1);

        return pos;
    }

    /**
     * 暴力递归
     *
     */
    private void process1(int[] pre, int L1, int R1,
                           int[] in, int L2, int R2,
                           int[] pos, int L3, int R3) {

        if(L1 > R1) {
            return;
        }
        // 当只有1个元素的时候
        if(L1 == R1) {
            pos[L3] = pre[L1];
            return;
        }
        // 先序遍历的第一个元素（根元素）就是后序遍历的最后一个元素
        pos[R3] = pre[L1];
        // 找到根元素在中序遍历的位置
        int mid = 0;
        for (; mid <= R2; mid++) {
            if(pre[L1] == in[mid]) {
                break;
            }
        }
        // 计算左子树的节点个数，这样就知道了左子树和右子树的节点个数，是以哪个元素作为根节点的
        int leftSize = mid - L2;
        // 开始构建左子树
        process1(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1);
        // 构建右子树
        process1(pre, L1 + leftSize + 1, R1, in, mid + 1, R2, pos, L3 + leftSize, R3 - 1);
    }


    /**
     * 利用map做缓存，优先内部循环
     *
     */
    private int[] getPosWithCache(int[] pre, int[] in) {
        int N = pre.length;
        int[] pos = new int[N];

        Map<Integer, Integer> map = new HashMap<>();
        // 使用map的key保存中序数组的值和索引，为了在递归内部不每次都循环查询，直接O(1)获取
        for (int i = 0; i < N; i++) {
            // 将值和索引放入map
            map.put(in[i], i);
        }
        process2(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1, map);

        return pos;
    }

    private void process2(int[] pre, int L1, int R1,
                          int[] in, int L2, int R2,
                          int[] pos, int L3, int R3,
                          Map<Integer, Integer> map) {
        if(L1 > R1) {
            return;
        }
        if(L1 == R1) {
            pos[L3] = pre[L1];
            return;
        }
        // 将头结点放到最后一位
        pos[R3] = pre[L1];
        // 获取当前头节点的位置, 使用map做优化
        int mid = map.get(pre[L1]);

        // 获取头结点的左子树的节点数量
        int leftSize = mid - L2;

        // 递归构建左子树和右子树，因为每颗子树都是一个小型的树，所以构建逻辑都是相同的，按照最小的树写第一级的代码，然后递归即可
        process2(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1, map);
        // 构建右子树
        process2(pre, L1 + leftSize + 1, R1, in, mid + 1, R2, pos, L3 + leftSize, R3 - 1, map);
    }

}
