package com.github.左神算法.有序表;

import com.sun.xml.internal.bind.v2.TODO;

/**
 * AVL（平衡二叉树）
 *
 * @author tangsong
 * @date 2021/8/22 17:43
 */
public class AVLTree {

    /**
     * AVL树是根据左子节点和右子节点的子树的高度进行平衡
     *
     */

    /**
     * AVL树的节点
     */
    public static class AVLTreeNode<K extends Comparable<K>, V> {
        public K k;
        public V v;
        public AVLTreeNode<K, V> l;
        public AVLTreeNode<K, V> r;
        public int h;

        public AVLTreeNode(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }


    /**
     * AVL树结构
     *
     */
    public static class AVLTreeMap<K extends Comparable<K>, V> {
        public AVLTreeNode<K, V> root;
        public int size; // 树的所有节点个数



        /**
         * AVL树是否包含key
         */
        public boolean containsKey(K key) {
            if(key == null) {
                return false;
            }
            // 按照搜索二叉树的方式查询
            AVLTreeNode<K, V> lastNode = findLastIndex(key);
            if(lastNode != null && key.compareTo(lastNode.k) == 0) {
                return true;
            }
            return false;
        }


        /**
         * 添加节点
         *
         */
        public void put(K key, V value) {
            if(key == null) {
                return;
            }
            // 查找key是否已经在AVL树中
            AVLTreeNode<K, V> lastNode = findLastIndex(key);
            if(lastNode != null && key.compareTo(lastNode.k) == 0) {
                // replace
                lastNode.v = value;
            } else {
                // 新增节点，加入到lastNode后面
                size++;
                // 需要调用add方法添加，因为需要将key添加到合适的位置并且平衡树
                // add返回调整之后的头结点
                root = add(root, key, value);
            }
        }


        /**
         *  从树中删除指定key
         *
         */
        public void remove(K key) {
            if(key == null) {
                return;
            }
            // 需要先判断树中是否存在该节点
            if(containsKey(key)) {
                size--;
                // 删除之后会平衡树，并返回新的节点
                root = delete(root, key);
            }
        }


        /**
         * 获取key对应的value
         *
         */
        public V getKey(K key) {
            if(key == null) {
                return null;
            }
            AVLTreeNode<K, V> lastNode = findLastIndex(key);
            if(lastNode != null && key.compareTo(lastNode.k) == 0) {
                return lastNode.v;
            }

            return null;
        }

        /**
         * 找到第一个key
         *
         */
        public K firstKey() {
            if(root == null) {
                return null;
            }
            AVLTreeNode<K, V> cur = root;
            while(cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        /**
         * 找到小于等于key的最大的节点
         *
         */
        public K floorKey(K key) {
            if(key == null) {
                return null;
            }
            AVLTreeNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }


        /**
         * 找到大于等于key的最小的节点
         *
         */
        public K ceilingKey(K key) {
            if(key == null) {
                return null;
            }
            // 找到大于等于key的最小的节点
            AVLTreeNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.k;
        }

        /**
         * 找到大于等于Key的最小的节点
         *
         */
        private AVLTreeNode<K, V> findLastNoSmallIndex(K key) {
            AVLTreeNode<K, V> cur = root;
            AVLTreeNode<K, V> maxMinNode = null;

            while(cur != null) {
                if(key.compareTo(cur.k) == 0) {
                    maxMinNode = cur;
                    break;
                } else if(key.compareTo(cur.k) < 0) {
                    maxMinNode = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }

            return maxMinNode;
        }


        /**
         * 找到小于等于Key的最大的节点
         *
         */
        private AVLTreeNode<K, V> findLastNoBigIndex(K key) {
            AVLTreeNode<K, V> cur = root;
            AVLTreeNode<K, V> minMaxNode = null;

            while(cur != null) {
                if(key.compareTo(cur.k) > 0) {
                    minMaxNode = cur;
                    cur = cur.r;
                } else if(key.compareTo(cur.k) < 0){
                    cur = cur.l;
                } else {
                    minMaxNode = cur;
                    break;
                }
            }

            return minMaxNode;
        }


        /**
         * 以cur为头节点的AVL树上添加k-v
         *   返回添加新节点之后 整棵树的新头部
         */
        private AVLTreeNode<K, V> add(AVLTreeNode<K, V> cur, K k, V value) {
            if(cur == null) {
                return new AVLTreeNode<>(k, value);
            } else {
                if(k.compareTo(cur.k) > 0) {
                    // 递归过程
                    cur.r = add(cur.r, k, value);
                } else {
                    // 递归过程，最终找到第一个合适的位置
                    cur.l = add(cur.l, k, value);
                }
                // 更新高度
                cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
                // 调整树的平衡
                return maintain(cur);
            }
        }


        /**
         * 在cur为头结点的树上，删除key节点
         *
         *  返回cur这棵树的新的头结点
         */
        private AVLTreeNode<K, V> delete(AVLTreeNode<K, V> cur, K key) {
            if(key == null) {
                return cur;
            }
            // 找到key节点
            if(key.compareTo(cur.k) > 0) {
                // 如果大于当前节点，则在右子树继续找key节点，并删除，返回新的右子树的头结点，重新连接到cur节点
                cur.r = delete(cur.r, key);
            } else if(key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else {
                // 如果找到当前节点
                if(cur.l == null && cur.r == null) {
                    // 直接删除
                    cur = null;
                } else if(cur.l == null && cur.r != null) {
                    // 如果左子节点为空，那么直接使用右子节点替换当前节点
                    cur = cur.r;
                } else if(cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else {
                    // 如果当前节点同时拥有左子节点和右子节点，则使用前驱节点或者后继节点替换即可
                    // 这里使用后继节点，找到右子树的最左节点替换当前节点，可以满足搜索二叉树的要求
                    AVLTreeNode<K, V> des = cur.r;
                    while (des.l != null) {
                        des = des.l;
                    }
                    // 因为如果直接移动des节点，可能会导致cur.r树的平衡的调整，所以这里使用直接删除方法操作，自动调整平衡
                    cur.r = delete(cur.r, des.k);
                    des.r = cur.r;
                    des.l = cur.l;
                    // 这里不需要关心上层节点连接到des的问题，因为会将当前头结点返回，上层节点在递归的上层接收这个头结点进行连接
                    cur = des;
                }
            }
            if(cur != null) {
                cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            }

            // 因为调整了cur的节点或者左右子树的节点，所以需要重新看看是否需要调整平衡
            return maintain(cur);
        }


        /**
         * 查找与key相等的节点
         *
         */
        private AVLTreeNode<K, V> findLastIndex(K key) {
            AVLTreeNode<K, V> cur = root;
            AVLTreeNode<K, V> pre = root;

            while (cur != null) {
                pre = cur;
                if(key.compareTo(cur.k) == 0) {
                    break;
                } else if(key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else if(key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                }
            }
            // 这里返回pre的目的是为了不返回空节点，返回大于或小于key的最后一个节点
            return pre;
        }


        /**
         * 调整以AVL中的cur为头节点的平衡，并返回新的头节点
         */
        private AVLTreeNode<K, V> maintain(AVLTreeNode<K, V> cur) {
            if(cur == null) {
                return null;
            }
            // 判断左右子树的高度是否打破了平衡，以及判断旋转规则
            int leftHeight = cur.l != null ? cur.l.h : 0;
            int rightHeight = cur.r != null ? cur.r.h : 0;
            if(Math.abs(leftHeight - rightHeight) > 1) {
                // 左边子树高度较大
                if(leftHeight > rightHeight) {
                    // 如果高度差大于等于2，则需要平衡调整
                    int leftLeftHeight = cur.l != null && cur.l.l != null ? cur.l.l.h : 0;
                    int leftRightHeght = cur.l != null && cur.l.r != null ? cur.l.r.h : 0;
                    // 判断是否LL型或者LR，统一右旋
                    // 如果左左高度==左右高度，那么就表示为同时满足LR和LL型，就需要直接右旋
                    if(leftLeftHeight >= leftRightHeght) {
                        cur = rightRotate(cur);
                    } else {
                        // 否则表示左右高度较大，需要先根据cur.l为头结点左旋，然后以cur为头结点右旋
                        cur.l = leftRotate(cur.l);
                        cur = rightRotate(cur);
                    }
                } else {
                    // 右边子树高度较大
                    int rightRightHeight = cur.r != null && cur.r.r != null ? cur.r.r.h : 0;
                    int rightLeftHeight = cur.r != null && cur.r.l != null ? cur.r.l.h : 0;
                    // 判断是否RR型或者RL型，两种都是直接左旋
                    if(rightRightHeight >= rightLeftHeight) {
                        cur = leftRotate(cur);
                    } else{
                        // 如果是RL型，则先右旋再左旋
                        cur.r = rightRotate(cur.r);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }

        /**
         * 以cur为头结点进行左旋
         *
         */
        private AVLTreeNode<K, V> leftRotate(AVLTreeNode<K, V> cur) {
            AVLTreeNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            right.h = Math.max(right.l != null ? right.l.h : 0, right.r != null ? right.r.h : 0) + 1;
            return right;
        }

        /**
         * 以cur为头结点进行右旋
         *
         */
        private AVLTreeNode<K, V> rightRotate(AVLTreeNode<K, V> cur) {
            AVLTreeNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            // 更新高度
            cur.h = Math.max(cur.r != null ? cur.r.h : 0, cur.l != null ? cur.l.h : 0) + 1;
            left.h = Math.max(left.r != null ? left.r.h : 0, left.l != null ? left.l.h : 0) + 1;

            return left;
        }

    }

}
