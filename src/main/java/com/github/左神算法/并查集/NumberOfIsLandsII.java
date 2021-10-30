package com.github.左神算法.并查集;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/6/16 22:58
 */
public class NumberOfIsLandsII {

    /**
     * 岛问题（扩展）
     *
     * 在岛问题的基础上做扩展，给定 m, n, 分别代表一个二维矩阵的行数和列数, 并给定一个大小为 k 的二元数组A，
     * 初始二维矩阵全0. 二元数组A内的k个元素代表k次操作
     * 矩阵中的元素，行和列对应，将n和m对应的矩阵的位置修改为1，在操作所有的矩阵A之后，n和m对饮的矩阵中的岛数量
     *
     */


    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        // write your code here
        List<Integer> list = new ArrayList<>();
        UnionFind unionFind = new UnionFind(m, n);
        for (int[] position : positions) {
            // 每次都计算一个岛的数量，将合并之后当前的集合数量加入到list中记录
            list.add(unionFind.connect(position[0], position[1]));
        }
        return list;
    }

    /**
     * 并查集
     *
     *   初始化时不进行数据的初始化，只初始化数组，在合并时再初始化
     *   因为不确定有多少个1，只能在合并的时候，才可以知道需要将指定的位置变为1
     *   但是需要记录某个位置是否已经设置过1，防止重复合并
     *
     */
    public static class UnionFind {
        public int[] parents;
        public int[] size; // size[i] = 0 表示没有被初始化过，可以进行一次合并，如果不等于0则表示该位置已经设置过一次1了，不需要再次设置
        public int[] help;
        public int row;
        public int col;
        public int sets;

        public UnionFind(int m, int n) {
            int len = m * n;
            int row = m;
            int col = n;
            parents = new int[len];
            size = new int[len];
            help = new int[len];
        }

        public int find(int cur) {
            int hi = 0;
            while (cur != parents[cur]) {
                // 记录寻找代表元素过程中的每个元素
                help[hi++] = cur;
                cur = parents[cur];
            }
            for (int i = 0; i < hi; i++) {
                // 将寻找过程中的每个元素都直接挂载到代表节点下
                parents[help[i]] = cur;
            }
            return cur;
        }

        public int index(int i, int j) {
            return i * col + j;
        }


        public void union(int r1, int c1, int r2, int c2) {
            if(r1 < 0 || c1 < 0 || r2 < 0 || c2 < 0 || r1 >= row || r2 >= row || c1 >= col || c2 >= col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            // 校验当前的两个元素有1个没有初始化过，则不继续合并，只有设置过为1的才合并，这个是递归过程，寻找的过程并不知道周围是否一定都是1
            if(size[i1] == 0 || size[i2] == 0) {
                return;
            }
            // 找到当前两个元素的代表节点
            int aHead = find(i1);
            int bHead = find(i2);
            if(aHead != bHead) {
                int aSize = size[aHead];
                int bSize = size[bHead];
                if(aSize >= bSize) {
                    parents[bHead] = aHead;
                    size[aHead] += bSize;
                } else {
                    parents[aHead] = bHead;
                    size[bHead] += aSize;
                }
                sets--;
            }
        }

        /**
         * 连接方法
         *    将当前位置尝试和周围进行连接，是否可以合并为一个集合的方法
         */
        public int connect(int i, int j) {
            // 计算当前位置在一维数组的下标
            int index = index(i, j);
            // 如果size[index] = 0 则表示这个位值没有重复设置过为1，则可以开始合并
            if(size[index] == 0) {
                parents[index] = index; // 将集合自己的父节点设置为自己
                size[index] = 1;
                sets++;
                // 开始和上、下、左、右进行递归合并，因为每次都是随机的一个点，只能每次去看是否可以和周围的店进行合并
                union(i, j, i + 1, j);
                union(i, j, i - 1, j);
                union(i, j, i, j + 1);
                union(i, j, i, j - 1);
            }
            // 连接一次之后，就返回改变当前位置之后，当前的集合数量
            return sets;
        }

    }

}
