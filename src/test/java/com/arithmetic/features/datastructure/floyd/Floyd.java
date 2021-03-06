package com.arithmetic.features.datastructure.floyd;

/**
 *
 * 弗洛伊德算法：（每个顶点到终点的距离最小）
 *  对中间节点进行遍历，在遍历中间节点时，遍历所有的开始节点经过中间节点到终点的距离，并获取一个最小的距离
 *  然后依次遍历中间节点数组，通过比较Lik+Ljk和Lij的大小获取每一个中间节点的距离最小的。就可以获取每个顶点到其他顶点的最小距离
 *
 * 普里姆算法：（修路最短路径（一个顶点到终点的路径最小））
 *  取一个点开始找到它的最小权值的另一个顶点，然后找到这两个顶点所有的联通的权值边，取最小的权值的顶点，然后在找这3个顶点的
 *  所有权值最小的边，最后达到找到的综合权值边最小
 *
 *  克鲁斯卡尔算法：
 *    将所有的权值边进行排序，从最小权值边的顶点开始计算，如果这几个顶点构成回路，则不取该顶点，继续向后，最终的路径最小
 *
 *  迪杰斯特拉算法：
 *     从一个顶点开始，找到最小的权值边的顶点，然后从这个顶点开始找到它联通的最小权值边的顶点排除访问过的顶点和不联通的顶点，
 *     最终的路径最小（相当于BFS）
 *
 * @author alan.chen
 * @date 2020/6/6 4:16 PM
 */
public class Floyd {
}
