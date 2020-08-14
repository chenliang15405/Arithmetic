package com.arithmetic.features.datastructure.graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alan.chen
 * @date 2020/7/13 5:32 PM
 */
public class GraphTest {

    @Test
    public void test() {
        int n = 8;  //结点的个数
        //String[] Vertexs = {"A", "B", "C", "D", "E"};
        String Vertexs[] = {"1", "2", "3", "4", "5", "6", "7", "8"};

        Graph graph = new Graph(n);

        for (String vertex : Vertexs) {
            graph.add(Integer.valueOf(vertex));
        }
        // 构建边的关系
        //graph.insertEdge(0, 1, 1); // A-B
        //graph.insertEdge(0, 2, 1); //
        //graph.insertEdge(1, 2, 1); //
        //graph.insertEdge(1, 3, 1); //
        //graph.insertEdge(1, 4, 1); //

        graph.addEdges(0, 1, 1);
        graph.addEdges(0, 2, 1);
        graph.addEdges(1, 3, 1);
        graph.addEdges(1, 4, 1);
        graph.addEdges(3, 7, 1);
        graph.addEdges(4, 7, 1);
        graph.addEdges(2, 5, 1);
        graph.addEdges(2, 6, 1);
        graph.addEdges(5, 6, 1);

        // 展示构建的邻接矩阵
        graph.show();


        System.out.println("dfs----");
        graph.dfs();
    }




    static class Graph {
        private List<Integer> vertex;

        private int[][] egdes;

        private int sideNums;


        public Graph(int n) {
            vertex = new ArrayList<>();
            egdes = new int[n][n];
        }

        public void add(int x) {
            vertex.add(x);
        }

        public void addEdges(int i, int j, int weight) {
            egdes[i][j] = weight;
            egdes[i][i] = weight;
            sideNums++;
        }


        public void show() {
            for (int i = 0; i < egdes.length; i++) {
                for (int j = 0; j < egdes[i].length; j++) {
                    System.out.print(egdes[i][j] + "->");
                }
                System.out.println();
            }
        }

        public void dfs() {
            boolean[] isVisited = new boolean[vertex.size()];

            for (int i = 0; i < vertex.size(); i++) {
                if(!isVisited[i]) {
                    dfs(isVisited, i);
                }
            }
        }

        public void dfs(boolean[] isVisited, int index) {
            System.out.print(vertex.get(index) + "->");

            isVisited[index] = true;

            int w = getFirstNeighboard(index);

            while(w != -1) {
                if(!isVisited[w]) {
                    dfs(isVisited, w);
                }
                w = getNeighboard(index, w);
            }

        }

        private int getNeighboard(int index, int w) {
            for (int i = w + 1; i < vertex.size(); i++) {
                if(egdes[index][i] > 0) {
                    return i;
                }
            }
            return -1;
        }

        private int getFirstNeighboard(int index) {
            for (int i = 0; i < vertex.size(); i++) {
                if(egdes[index][i] > 0) {
                    return i;
                }
            }
            return -1;
        }

    }
}
