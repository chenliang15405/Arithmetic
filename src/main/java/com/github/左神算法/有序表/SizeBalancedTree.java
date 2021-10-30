package com.github.左神算法.有序表;

import org.junit.Test;

/**
 * SizeBanlancedTree（SB树）
 *
 * @author tangsong
 * @date 2021/8/28 11:10
 */
public class SizeBalancedTree {

    /**
     * SB树是针对叔叔节点和侄子节点的节点数量的关系进行平衡操作
     *
     *  其中只有维护平衡是SBTree的特性，和其他的树不同
     *  查找、删除、获取某些节点都是二叉排序树的特性，没有什么关系
     *
     */
    @Test
    public void test() {
        SizeBalancedTreeMap<String, Integer> sbt = new SizeBalancedTreeMap<String, Integer>();
        sbt.put("d", 4);
        sbt.put("c", 3);
        sbt.put("a", 1);
        sbt.put("b", 2);
        // sbt.put("e", 5);
        sbt.put("g", 7);
        sbt.put("f", 6);
        sbt.put("h", 8);
        sbt.put("i", 9);
        sbt.put("a", 111);
        System.out.println(sbt.get("a"));
        sbt.put("a", 1);
        System.out.println(sbt.get("a"));
        for (int i = 0; i < sbt.size(); i++) {
            System.out.println(sbt.getIndexKey(i) + " , " + sbt.getIndexValue(i));
        }
        printAll(sbt.root);
        System.out.println(sbt.firstKey());
        System.out.println(sbt.lastKey());
        System.out.println(sbt.floorKey("g"));
        System.out.println(sbt.ceilingKey("g"));
        System.out.println(sbt.floorKey("e"));
        System.out.println(sbt.ceilingKey("e"));
        System.out.println(sbt.floorKey(""));
        System.out.println(sbt.ceilingKey(""));
        System.out.println(sbt.floorKey("j"));
        System.out.println(sbt.ceilingKey("j"));
        sbt.remove("d");
        printAll(sbt.root);
        sbt.remove("f");
        printAll(sbt.root);

    }



    // SB树的节点
    public static class SBTNode<K extends Comparable<K>, V> {
        public K k;
        public V v;
        public SBTNode<K, V> l;
        public SBTNode<K, V> r;
        public int size; // 以当前节点为头结点的子树的节点个数

        public SBTNode(K k, V v) {
            this.k = k;
            this.v = v;
            this.size = 1;
        }
    }


    public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
        public SBTNode<K, V> root;

        public int size() {
            return root == null ? 0 : root.size;
        }

        /**
         *  是否包含某个key
         *
         */
        public boolean containsKey(K key) {
            if(key == null) {
                throw new RuntimeException("invalid parameter");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.k) == 0;
        }

        /**
         * 添加新的节点
         *
         */
        public void put(K key, V value) {
            if(key == null) {
                throw new RuntimeException("invalid parameter");
            }
            // 判断节点是否已经存在树中了
            SBTNode<K, V> lastNode = findLastIndex(key);
            if(lastNode != null && key.compareTo(lastNode.k) == 0) {
                lastNode.v = value;
            } else {
                // 将节点添加到root为头节点的树，并返回新的头节点
                root = add(root, key, value);
            }
        }

        /**
         * 删除节点
         *
         */
        public void remove(K key) {
            if(key == null) {
                throw new RuntimeException("invalid parameter");
            }
            // 先判断key是否存在
            if(containsKey(key)) {
                // 删除节点, 并返回新的头结点
                root = delete(root, key);
            }
        }

        /**
         * 获取第index个节点的key（从小到大的顺序）
         *
         */
        public K getIndexKey(int index) {
            if(index < 0 || index > this.size()) {
                throw new RuntimeException("invalid parameter");
            }

            return getIndex(root, index + 1).k;
        }


        /**
         * 根据key获取value
         *
         */
        public V get(K key) {
            if(key == null) {
                throw new RuntimeException("invalid parameter");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            if(lastNode != null && key.compareTo(lastNode.k) == 0) {
                return lastNode.v;
            }
            return null;
        }

        /**
         * 获取第一个key
         *
         */
        public K firstKey() {
            if(root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }

            return cur.k;
        }

        /**
         * 获取最后一个key
         *
         */
        public K lastKey() {
            if(root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }

            return cur.k;
        }

        /**
         * 获取小于等于key的最大的一个key
         *
         */
        public K floorKey(K key) {
            if(key == null) {
                throw new RuntimeException("invalid parameter");
            }
            SBTNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            if(lastNoBigNode != null) {
                return lastNoBigNode.k;
            }
            return null;
        }


        /**
         * 找到大于等于当前key的最小的一个key
         *
         */
        public K ceilingKey(K key) {
            if(key == null) {
                throw new RuntimeException("invalid parameter");
            }
            SBTNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode != null ? lastNoSmallNode.k : null;
        }

        /**
         * 找到第一个大于等于当前key的节点
         *
         */
        private SBTNode<K, V> findLastNoSmallIndex(K key) {
            SBTNode<K, V> cur = root;
            SBTNode<K, V> ans = null;

            while (cur != null) {
                if(key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if(key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                } else {
                    ans = cur;
                    cur = cur.l;
                }
            }

            return ans;
        }


        /**
         * 查询第一个小于等于key的最大的节点
         *
         */
        private SBTNode<K, V> findLastNoBigIndex(K key) {
            SBTNode<K, V> cur = root;
            SBTNode<K, V> ans = null;

            while (cur != null) {
                if(key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if(key.compareTo(cur.k) > 0) {
                    ans = cur;
                    cur = cur.r;
                } else {
                    cur = cur.l;
                }
            }
            return ans;
        }


        /**
         * 获取第index个节点的value
         *
         */
        public V getIndexValue(int index) {
            if(index < 0 || index > this.size()) {
                throw new RuntimeException("invalid parameter");
            }

            return getIndex(root, index + 1).v;
        }

        /**
         * 排序二叉树的性质 + SB树的size查找
         *
         */
        private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
            if(kth == (cur.l != null ? cur.l.size : 0) + 1) {
                return cur;
            } else if(kth <= (cur.l != null ? cur.l.size : 0)) {
                return getIndex(cur.l, kth);
            } else {
                // 向右子树查找，那么就需要减去左子树的个数和根节点个数
                return getIndex(cur.r, kth - (cur.l != null ? cur.l.size : 0) - 1);
            }
        }


        /**
         * 在cur这棵树上，删掉key所代表的节点
         * 返回cur这棵树的新头部
         *
         */
        private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            if(cur == null) {
                return null;
            }
            // 当前树的节点-1
            cur.size--;
            // 找到对应的节点并删除，二叉树性质
            if(key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.r, key);
            } else if(key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else {
                // 找到节点并判断是否可以直接删除，还是需要使用后继节点填充
                if(cur.l == null && cur.r == null) {
                    cur = null;
                } else if(cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else if(cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else {
                    // 找到后继节点并替换当前节点
                    SBTNode<K, V> pre = null;
                    SBTNode<K, V> des = cur.r;
                    des.size--;
                    // 右子树最左节点
                    while (des.l != null) {
                        pre = des;
                        des = des.l;
                        des.size--;
                    }
                    // 将des节点从树中删除，并将后续的节点连接上
                    if(pre != null) {
                        pre.l = des.r;
                        des.r = cur.r;
                    }
                    // des节点替换当前需要删除的key（即cur节点）
                    des.l = cur.l;
                    des.size = des.l.size + (des.r == null ? 0 : des.r.size) + 1;
                    cur = des;
                }
            }
            // 这里可以调整也可以不调整，因为如果不调整，最多也就是结构变成时间复杂度最高的链表，但是在下次添加节点的时候就会将整棵树调整好
            // cur = maintain(cur);
            return cur;
        }


        /**
         *  现在，以cur为头的树上，新增，加(key, value)这样的记录
         *  加完之后，会对cur做检查，该调整调整
         *  返回，调整完之后，整棵树的新头部
         *
         */
        private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
            if(cur == null) {
                return new SBTNode<>(key, value);
            } else {
                if(key.compareTo(cur.k) > 0) {
                    cur.r = add(cur.r, key, value);
                } else {
                    cur.l = add(cur.l, key, value);
                }
                return maintain(cur);
            }
        }

        /**
         * 平衡以cur为头节点的SB树
         *  返回新的头结点
         *
         */
        private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
            if(cur == null) {
                return null;
            }
            // 判断当前是LL、LR、RR、RL
            // SB树比较的是叔叔节点的子树的节点个数和侄子节点的子树的节点个数
            int leftSize = cur.l != null ? cur.l.size : 0;
            int leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int rightSize = cur.r != null ? cur.r.size : 0;
            int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;

            // 判断当前是某种不平衡的类型
            if(leftLeftSize > rightSize) {
                // LL  侄子节点个数 > 叔叔节点
                cur = rightRotate(cur);
                // 递归调整节点有改变的节点
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if(leftRightSize > rightSize) {
                // LR  先左旋，然后右旋
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if(rightRightSize > leftSize) {
                // RR
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if(rightLeftSize > leftSize) {
                // RL 先右旋 再左旋
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.r = maintain(cur.r);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            }

            return cur;
        }


        /**
         *  左旋
         *
         */
        private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> rightNode = cur.r;
            cur.r = rightNode.l;
            rightNode.l = cur;
            rightNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return rightNode;
        }

        /**
         * 右旋
         *
         */
        private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> leftNode = cur.l;
            cur.l = leftNode.r;
            leftNode.r = cur;
            // 更新size
            leftNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return leftNode;
        }


        private SBTNode<K, V> findLastIndex(K key) {
            SBTNode<K, V> cur = root;
            SBTNode<K, V> pre = root;

            while(cur != null) {
                pre = cur;
                if(key.compareTo(cur.k) == 0) {
                    break;
                } else if(key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                } else {
                    cur = cur.l;
                }
            }

            return pre;
        }

    }


    // for test
    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    // for test
    public static void printAll(SBTNode<String, Integer> head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    // for test
    public static void printInOrder(SBTNode<String, Integer> head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.r, height + 1, "v", len);
        String val = to + "(" + head.k + "," + head.v + ")" + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.l, height + 1, "^", len);
    }


}
