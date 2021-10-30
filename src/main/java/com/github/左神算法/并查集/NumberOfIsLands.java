package com.github.左神算法.并查集;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/16 22:56
 */
public class NumberOfIsLands {

    /**
     * 岛问题
     *
     * 给定一个二维数组matrix，里面的值不是1就是0，上下左右相邻的1认为是一片岛，返回matrix中岛的数量
     *
     */

    @Test
    public void test() {
        int[][] arr = new int[][]{
                { 0, 1, 1, 1, 1, 0, 0 },// 0
                { 0, 1, 0, 0, 0, 0, 0 },// 1
                { 0, 1, 0, 1, 1, 1, 0 },// 2
                { 0, 1, 0, 0, 0, 0, 1 },// 3
                { 0, 0, 0, 1, 1, 1, 1 },// 4
        };
        System.out.println(countLand(arr));
    }

    @Test
    public void test2() {
        int[][] arr = new int[][]{
                { 0, 1, 1, 1, 1, 0, 0 },// 0
                { 0, 1, 0, 0, 0, 0, 0 },// 1
                { 0, 1, 0, 1, 1, 1, 0 },// 2
                { 0, 1, 0, 0, 0, 0, 1 },// 3
                { 0, 0, 0, 1, 1, 1, 1 },// 4
        };
        System.out.println(countLand2(arr));
    }

    /**
     * 递归
     *
     */
    public int countLand(int[][] matrix) {
        if(matrix == null || matrix.length <= 0) {
            return 0;
        }
        int count = 0;
        // 遍历矩阵，只有当前位置为1的时候才进行递归计算
        int N = matrix.length;
        int M = matrix[0].length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(matrix[i][j] == 1) {
                    // 开始递归，将所有是1的位置修改为2
                    infect(matrix, i, j, N, M);
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 对所有的位置进行递归
     *
     */
    private void infect(int[][] matrix, int i, int j, int n, int m) {
        if(i < 0 || j < 0 || i >= n || j >= m || matrix[i][j] != 1) {
            return;
        }
        // 将当前位置修改为2
        matrix[i][j] = 2;
        // 开始上下左右进行递归
        infect(matrix, i + 1, j, n, m);
        infect(matrix, i - 1, j, n, m);
        infect(matrix, i, j + 1, n, m);
        infect(matrix, i, j - 1, n, m);
    }


    /**
     * 并查集
     *
     */
    public int countLand2(int[][] matrix) {
        if(matrix == null || matrix.length <= 0) {
            return 0;
        }
        // 初始化并查集
        UnionFind unionFind = new UnionFind(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(i == 0 && j == 0) {
                    continue;
                }
                // 每判断一个点的时候，如果当前位置等于1，则在合并的时候只需要将当前位置和上面、左边合并集合，每个位置都是这样，就不会漏掉
                if(matrix[i][j] == 1) {
                    // 对于第一行，没有上，边界条件判断
                    if(i == 0 && matrix[i][j-1] == 1) {
                        unionFind.union(i, j, i, j - 1);
                    }
                    if(j == 0 && matrix[i-1][j] == 1) {
                        unionFind.union(i, j, i - 1, j);
                    }
                    // 如果不是第一行，也不是第一列，并且当前位置的上面也是1，则合并
                    if(i != 0 && j != 0 && matrix[i-1][j] == 1){
                        unionFind.union(i, j, i - 1, j);
                    }
                    // 如果不是第一行，也不是第一列， 并且当前位置的左边也是1，则合并
                    if(i != 0 && j != 0 && matrix[i][j-1] == 1) {
                        unionFind.union(i, j, i, j - 1);
                    }
                }
            }
        }
        return unionFind.sets();
    }


    public static class UnionFind {
        public int[] parents; //
        public int[] size; //
        public int[] help;
        public int sets;
        public int col; // 主要记录列的多少，需要转换二维数组的坐标为一维数组的下标

        public UnionFind(int[][] matrix) {
            int N = matrix.length;
            int M = matrix[0].length;
            int length = N * M;
            // 初始化, 将parent的数组设计为，将二维数组扁平化为一维数组，总的长度是矩阵的总长度，每个位置计算为 r*c + c
            parents = new int[length];
            size = new int[length];
            help = new int[length];
            sets = 0;
            col = M;
            // 遍历数组，只有1才算作是一个集合，并且将1的位置加入到数组中
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if(matrix[i][j] == 1) {
                        int k = index(i, j);
                        parents[k] = k;
                        size[k] = 1;
                        sets++;
                    }
                }
            }

        }

        /**
         * 将二维数组中的位置转换为一维数组的下标
         */
        public int index(int r, int c) {
            return r * col + c;
        }

        public int find(int cur) {
            int hi = 0;
            while (cur != parents[cur]) {
                help[hi++] = cur;
                cur = parents[cur];
            }
            // 优化，将寻找的链都直接挂到代表节点下
            for (int i = 0; i < hi; i++) {
                parents[help[i]] = cur;
            }
            return cur;
        }

        /**
         * 将两个位置合并
         */
        public void union(int r1, int c1, int r2, int c2) {
            int index1 = index(r1, c1); // 先转换为一维数组的下标
            int index2 = index(r2, c2);
            int aHead = find(index1);
            int bHead = find(index2);
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


        public int sets() {
            return sets;
        }

    }


}
