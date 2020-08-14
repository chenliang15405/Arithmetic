package com.arithmetic.features.datastructure.graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 图：解决多对多的关系，用数组来表示：邻接矩阵（二维数组），用单链表表示：邻接表（数组+连表）
 *
 * 图中的概念：顶点、边、路径、有向图、无向图、带权图
 *
 * 深度优先遍历  纵向的遍历方式
 * 广度优先遍历  横向的遍历方式
 *
 *
 * @author alan.chen
 * @date 2020/6/2 9:44 PM
 */
public class GraphDemo {

    @Test
    public void test() {
        int n = 8;  //结点的个数
        //String[] Vertexs = {"A", "B", "C", "D", "E"};
        String Vertexs[] = {"1", "2", "3", "4", "5", "6", "7", "8"};

        Graph graph = new Graph(n);

        for (String vertex : Vertexs) {
            graph.insertVertex(vertex);
        }
        // 构建边的关系
        //graph.insertEdge(0, 1, 1); // A-B
        //graph.insertEdge(0, 2, 1); //
        //graph.insertEdge(1, 2, 1); //
        //graph.insertEdge(1, 3, 1); //
        //graph.insertEdge(1, 4, 1); //

        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.insertEdge(3, 7, 1);
        graph.insertEdge(4, 7, 1);
        graph.insertEdge(2, 5, 1);
        graph.insertEdge(2, 6, 1);
        graph.insertEdge(5, 6, 1);

        // 展示构建的邻接矩阵
        graph.showGraph();

        System.out.println("深度优先遍历");
        // 图的深度遍历 dfs
        graph.dfs();

        System.out.println();
        System.out.println("广度优先遍历");
        // 图的广度遍历 bfs
        graph.bfs();
    }

}

class Graph {
    // 表示所有顶点的集合
    private List<String> vertexList;
    // 表示边的数目
    private int numsOfEdges;
    // 表示图对应的邻接矩阵
    private int[][] edges;
    // 表示该节点是否访问过
    private boolean[] isVisited;

    public Graph(int n) {
        vertexList = new ArrayList<>();
        edges = new int[n][n];
        isVisited = new boolean[n];
        numsOfEdges = 0;
    }

    public List<String> getList() {
        return vertexList;
    }

    public void setList(List<String> list) {
        this.vertexList = list;
    }

    public int getNumsOfEdges() {
        return numsOfEdges;
    }

    public void setNumsOfEdges(int numsOfEdges) {
        this.numsOfEdges = numsOfEdges;
    }

    public int[][] getEdges() {
        return edges;
    }

    public void setEdges(int[][] edges) {
        this.edges = edges;
    }

    public boolean[] getIsVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean[] isVisited) {
        this.isVisited = isVisited;
    }

    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    public void insertEdge(int i, int j, int weight) {
        // 设置一个边，则两个点都会有关系
        edges[i][j] = weight;
        edges[j][i] = weight;
        numsOfEdges++;
    }

    public void showGraph() {
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].length; j++) {
                System.out.print(edges[i][j]);
            }
            System.out.println();
        }
    }

    public void bfs() {
        isVisited = new boolean[vertexList.size()];
        // 递归遍历每个顶点进行深度遍历
        // 相当于回溯
        for (int i = 0; i < vertexList.size(); i++) {
            // 有可能第一次遍历中已经遍历过了，就是上一个节点和下一个节点有边的关系就会遍历过，下一次就不用再遍历
            if(!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }

    // 广度优先遍历
    // 找到当前节点的所有邻接节点，直到没有的时候，从队列中取出下一个节点，继续遍历
    // 相当于回溯
    public void bfs(boolean[] isVisited, int index) {
        // 遍历当前节点
        System.out.print(getValueByIndex(index) + "->");
        // 创建队列，保存每次遍历的节点你
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(index);
        // 将已遍历的节点设置为已访问
        isVisited[index] = true;

        int cur;
        int w;
        // 循环输出队列是否为空，如果不为空，则从队列中取出节点，遍历该节点的邻接节点
        while(!queue.isEmpty()) {
            cur = queue.removeFirst();
            // 获取该节点的第一个邻接节点
            w = getFirstNeighbor(cur);
            // 如果邻接节点不为空，则遍历该邻接节点，并获取下一个邻接节点
            while (w != -1) {
                if(!isVisited[w]) {
                    System.out.print(getValueByIndex(w) + "->");
                    // 设置已访问
                    isVisited[w] = true;
                    // 将该节点加入到队列中
                    queue.addLast(w);
                }
                // 找到当前节点的下一个邻接节点
                w = getNextNeighbor(cur, w);
            }
            // 当找不到当前节点的邻接节点时，获取队列中下一个
        }



    }

    public void dfs() {
        isVisited = new boolean[vertexList.size()];
        // 递归遍历每个顶点进行深度遍历
        // 相当于回溯
        for (int i = 0; i < vertexList.size(); i++) {
            // 有可能第一次遍历中已经遍历过了，就是上一个节点和下一个节点有边的关系就会遍历过，下一次就不用再遍历
            if(!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
    }
    // 深度优先遍历
    // 遍历就是通过一个顶点开始，找到该顶点的所有邻接节点，然后输出邻接节点，并且不重复输出
    // 深度优先遍历和广度优先遍历的方式不一致，所以输出的结果也不一致（除了特殊情况）
    public void dfs(boolean[] isVisited, int index) {
        // 输出当前节点
        System.out.print(getValueByIndex(index) + "->");
        // 将当前节点设置为访问, 访问数据的下标表示当前节点
        isVisited[index] = true;
        // 获取当前节点的邻接节点
        int w = getFirstNeighbor(index);
        while(w != -1) {
            // 递归深度优先遍历
            if(!isVisited[w]) {
                // 以邻接节点为当前节点继续遍历
                dfs(isVisited, w);
            }
            // 如果获取不到邻接节点或者邻接节点已经访问过，则获取下一个邻接节点
            w = getNextNeighbor(index, w);
        }

    }

    private int getNextNeighbor(int index, int w) {
        // 获取除了第一个邻接节点的下一个邻接节点
        for (int i = w + 1; i < vertexList.size(); i++) {
            if(edges[index][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    private int getFirstNeighbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            // 这两个节点具有边的关系，则认为是邻接节点
            if(edges[index][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    private String getValueByIndex(int index) {
        return vertexList.get(index);
    }

}
