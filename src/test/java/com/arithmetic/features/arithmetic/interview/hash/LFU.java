package com.arithmetic.features.arithmetic.interview.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/3/21 19:52
 */
public class LFU {

    public static class Node {
        public Integer key;
        public Integer value;
        public Node up;
        public Node down;
        public Integer times; // 该Node的访问的频率

        public Node(Integer key, Integer value, Integer times) {
            this.key = key;
            this.value = value;
            this.times = times;
        }
    }

    public static class LFUCache {

        public static class NodeList {
            public Node head;
            public Node tail;
            public NodeList next;
            public NodeList last;

            public NodeList(Node node) {
                head = node;
                tail = node;
            }

            public void deleteNode(Node node) {
                if(head == null) {
                    return;
                }
                if(head == tail) {
                    head = null;
                    tail = null;
                } else {
                    if(head == node) {
                        head = node.down;
                        head.up = null;
                    } else if(tail == node) {
                        tail = node.up;
                        tail.down = null;
                    } else {
                        node.up.down = node.down;
                        node.down.up = node.up;
                    }
                    node.down = null;
                    node.up = null;
                }
            }

            public boolean isEmpty() {
                return head == null;
            }

            public void addNodeFromHead(Node node) {
                if(head == null) {
                    head = node;
                    tail = node;
                } else {
                    node.down = head;
                    head.up = node;
                    head = node;
                }
            }

        }

        public Integer capacity;
        public Integer size;
        public Map<Integer, Node> records; // key Node  通过key可以找到对应的Node节点，否则无法找到key对应的node
        public Map<Node, NodeList> heads;  // node nodeList 可以通过node找到该node所属的nodeList
        public NodeList headList;

        public LFUCache(Integer capacity) {
            this.capacity = capacity;
            records = new HashMap<>();
            heads = new HashMap<>();
        }

        public void set(int key, int value) {
            if(records.containsKey(key)) {
                Node node = records.get(key);
                node.value = value;
                node.times++;
                NodeList nodeList = heads.get(node);
                move(node, nodeList);
            } else {
                if(size > capacity) {
                    Node tail = headList.tail;
                    headList.deleteNode(tail);
                    modifyHeadList(headList); // 删除节点之后该NodeList是否为空，是否需要调整
                    records.remove(tail.key);
                    heads.remove(tail);
                    size--;
                }
                Node node = new Node(key, value, 1);
                if(headList == null) {
                    headList = new NodeList(node);
                } else {
                    if(headList.head.times.equals(node.times)) {
                        headList.addNodeFromHead(node);
                    } else {
                        NodeList newNodeList = new NodeList(node);
                        newNodeList.next = headList;
                        headList.last = newNodeList;
                        headList = newNodeList;
                    }
                }
                records.put(key, node);
                heads.put(node, headList);
                size++;
            }
        }


        public Integer get(int key) {
            if(!records.containsKey(key)) {
                return null;
            }
            Node node = records.get(key);
            node.times++;
            NodeList nodeList = heads.get(node);
            move(node, nodeList);
            return node.value;
        }


        /**
         * 将当前node从当前nodeList中移除，并加入到下一个nodeList中
         */
        private void move(Node node, NodeList curNodeList) {
            curNodeList.deleteNode(node);
            NodeList preNodeList = modifyHeadList(curNodeList) ? curNodeList.last : curNodeList;
            NodeList nextNodeList = curNodeList.next;
            if(nextNodeList == null) {
                NodeList nodeList = new NodeList(node);
                if(preNodeList != null) {
                    preNodeList.next = nodeList;
                    nodeList.last = preNodeList;
                }
                if(headList == null) {
                    headList = nodeList;
                }
                heads.put(node, nodeList);
            } else {
                if(nextNodeList.head.times.equals(node.times)) {
                    nextNodeList.addNodeFromHead(node);
                    heads.put(node, nextNodeList);
                } else {
                    NodeList nodeList = new NodeList(node);
                    nodeList.next = nextNodeList;
                    nextNodeList.last = nodeList;
                    if(preNodeList != null) {
                        preNodeList.next = nodeList;
                        nodeList.last = preNodeList;
                    }
                    if (headList == nodeList) {
                        headList = nodeList;
                    }
                    heads.put(node, nodeList);
                }
            }
        }

        /**
         * 判断当前链表是否为空链表, 并调整当前headList指针指向的链表
         */
        private boolean modifyHeadList(NodeList curNodeList) {
            if(curNodeList.isEmpty()) {
                if(headList == curNodeList) {
                    // 如果当前链表节点是头链表
                    headList = curNodeList.next;
                    curNodeList.next = null;
                    if(headList != null) {
                        headList.last = null;
                    }
                } else {
                    // 如果当前链表不是头链表
                    curNodeList.last.next = curNodeList.next;
                    if(curNodeList.next != null) {
                        curNodeList.next.last = curNodeList.last;
                    }
                }
                return true;
            }
            return false;
        }
    }

}
