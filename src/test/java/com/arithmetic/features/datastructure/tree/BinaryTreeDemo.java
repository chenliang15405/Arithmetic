package com.arithmetic.features.datastructure.tree;

import org.junit.Test;

import java.util.*;

/**
 * 二叉树： 树中每个节点至多有两个子节点
 *
 * 概念：每个节点最多拥有2个子节点的树称为二叉树
 *
 * 完全二叉树: 节点都集中在左边，不会存在左边没有节点而右边有节点的情况
 * 满二叉树：高度为H,则树的节点为2^h -1，树中除了叶子节点，每个节点都有两个子节点
 * 完美二叉树：满足完全二叉树性质，树的叶子节点均在最后一层（也就是形成了一个完美的三角形
 *
 * 前序遍历: 遍历父节点，遍历左子树，遍历右子树
 * 中序遍历：遍历左子树，遍历父节点，遍历右子树
 * 后序遍历：遍历左子树，遍历右子树，遍历父节点
 *
 * 前序遍历查找：和前序遍历相同，遍历的时候查找
 * 中序遍历查找：和中序遍历相同，遍历的时候查找
 * 后序遍历查找：和后序遍历相同，遍历的时候查找
 *
 *
 * @author alan.chen
 * @date 2020/5/30 12:20 PM
 */
public class BinaryTreeDemo {

    @Test
    public void test() {
        Node root = new Node(1, "宋江");
        Node left = new Node(2, "吴用");
        Node leftRight = new Node(2, "吴用1111");
        Node right = new Node(3, "卢俊义");
        Node righ2 = new Node(4, "林冲");
        Node left2 = new Node(5, "玉麒麟");

        root.setLeft(left);
        left.setRight(leftRight);
        root.setRight(right);
        right.setRight(righ2);
        right.setLeft(left2);

        BinaryTree binaryTree = new BinaryTree(root);

        System.out.println("前序遍历");
        binaryTree.preOrder(); // 1,2,3,4

        System.out.println("中序遍历");
        binaryTree.infixOrder(); // 2,1,3,4

        System.out.println("后序遍历");
        binaryTree.sufixOrder(); // 2,4,3,1


        System.out.println("前序遍历查找");
        Node node = binaryTree.preOrderSearch(3); // 查找次数3次
        System.out.println(node);

        System.out.println("中序遍历查找");
        Node node2 = binaryTree.infixOrderSearch(3); // 查找次数3次
        System.out.println(node2);

        System.out.println("后序遍历查找");
        Node node3 = binaryTree.sufixOrderSearch(3); // 查找次数3
        System.out.println(node3);


        System.out.println("二叉树删除节点");
        binaryTree.delNode(2);

        System.out.println("删除之后——前序遍历查找");
        binaryTree.preOrder();

        System.out.println("二叉树删除节点--不删除子树");
        binaryTree.delNode2(3);

        System.out.println("删除之后——前序遍历查找222");
        binaryTree.preOrder();

    }

    @Test
    public void treeBFS() {
        Node root = new Node(1, "宋江");
        Node left = new Node(2, "吴用");
        Node leftRight = new Node(2, "吴用1111");
        Node right = new Node(3, "卢俊义");
        Node righ2 = new Node(4, "林冲");
        Node left2 = new Node(5, "玉麒麟");

        root.setLeft(left);
        left.setRight(leftRight);
        root.setRight(right);
        right.setRight(righ2);
        right.setLeft(left2);

        List<Node> bfs = bfs(root);
        bfs.forEach(System.out::println); // 1,2,3,2,5,4
    }


    @Test
    public void treeDFS() {
        Node root = new Node(1, "宋江");
        Node left = new Node(2, "吴用");
        Node leftRight = new Node(2, "吴用1111");
        Node right = new Node(3, "卢俊义");
        Node righ2 = new Node(4, "林冲");
        Node left2 = new Node(5, "玉麒麟");

        root.setLeft(left);
        left.setRight(leftRight);
        root.setRight(right);
        right.setRight(righ2);
        right.setLeft(left2);

        List<Node> dfs = dfs(root);
        dfs.forEach(System.out::println); // 1,2,2,3,5,4
    }

    /**
     * 二叉树深度优先遍历 DFS（其实就是前序遍历，非递归方式）
     *
     * 使用栈保存每个几点，先遍历当前节点和左子树，然后再遍历右子树
     *
     * @param root
     * @return
     */
    private List<Node> dfs(Node root) {
        if(root == null) return new ArrayList<>();

        Stack<Node> stack = new Stack<>();
        List<Node> list = new ArrayList<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            list.add(node);
            // 先将右子节点加入stack，则表示先向左子节点深度遍历
            if(node.getRight() != null) {
                stack.push(node.getRight());
            }
            if(node.getLeft() != null) {
                stack.push(node.getLeft());
            }
        }
        return list;
    }


    /**
     * 二叉树广度优先遍历 BFS
     *
     * 使用队列遍历，将每一层级遍历完毕在开始遍历下一层级
     *
     * @param root
     * @return
     */
    private List<Node> bfs(Node root) {
        if(root == null) return new ArrayList<>();

        List<Node> list = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Node temp = queue.poll();
                list.add(temp);
                if(temp.getLeft() != null) {
                    queue.add(temp.getLeft());
                }
                if(temp.getRight() != null) {
                    queue.add(temp.getRight());
                }
            }
        }
        return list;
    }

}

class BinaryTree {
    // 树对象中只需要维护root节点，就是根节点
    private Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    /**
     * 树对象直接调用节点的遍历方法
     */
    public void preOrder() {
        if(root != null) {
            this.root.preOrder();
        } else {
            System.out.println("空树");
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if(root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("空树");
        }
    }

    /**
     * 后序遍历
     */
    public void sufixOrder() {
        if(root != null) {
            this.root.sufixOrder();
        } else {
            System.out.println("空树");
        }
    }

    /**
     * 中序遍历查找
     *
     */
    public Node preOrderSearch(int id) {
        if(root != null) {
            return this.root.preOrderSearch(id);
        } else {
            System.out.println("空树");
            return null;
        }
    }

    /**
     * 前序遍历查找
     *
     */
    public Node infixOrderSearch(int id) {
        return this.root.infixOrderSearch(id);
    }

    /**
     * 后序遍历查找
     */
    public Node sufixOrderSearch(int id) {
        return this.root.sufixOrderSearch(id);
    }

    /**
     * 二叉树删除节点
     *
     */
    public void delNode(int id) {
        // 判断根节点是否是需要删除节点
        if(root.getId() == id) {
            this.root = null;
        } else {
            this.root.delNode(id);
        }
    }

    /**
     * 二叉树删除节点2
     */
    public void delNode2(int id) {
        if(root.getId() == id) {
            this.root = null;
        } else {
            this.root.delNode2(id);
        }
    }

}

class Node {
    private int id;
    private String name;
    private Node left;
    private Node right;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * 二叉树前序遍历
     *
     * 都是从根节点开始遍历
     *
     */
    public void preOrder() {
        // 先输出根节点
        System.out.println(this);
        // 开始遍历左子树
        if(this.left != null) {
            this.left.preOrder();
        }
        // 遍历右子树
        if(this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if(this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if(this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 后序遍历
     */
    public void sufixOrder() {
        if(this.left != null) {
            this.left.sufixOrder();
        }
        if(this.right != null) {
            this.right.sufixOrder();
        }
        System.out.println(this);
    }

    /**
     * 前序遍历查找
     */
    public Node preOrderSearch(int id) {
        System.out.println("前序遍历次数～");
        Node temp = null;
        // 先查找当前节点
        if(this.id == id) {
            return this;
        }
        // 当前节点查找不到则递归遍历左子树查找
        if(this.left != null) {
            temp = this.left.preOrderSearch(id);
        }
        // 如果左子树找到，则直接返回
        if(temp != null) {
            return temp;
        }
        // 然后递归再查找右子树
        if(this.right != null) {
            temp = this.right.preOrderSearch(id);
        }
        return temp;
    }

    /**
     * 中序遍历查找
     *
     */
    public Node infixOrderSearch(int id) {
        Node temp = null;
        // 先查找左树
        if(this.left != null) {
            temp = this.left.infixOrderSearch(id);
        }
        // 如果左树找到，则直接返回
        if(temp != null) {
            return temp;
        }

        System.out.println("中序遍历次数～");
        // 如果左树没有找到，则匹配当前节点
        if(this.id == id) {
            return this;
        }
        // 然后查找右子树
        if(this.right != null) {
            temp = this.right.infixOrderSearch(id);
        }
        return temp;
    }

    /**
     * 后序遍历查找
     *
     */
    public Node sufixOrderSearch(int id) {
        Node temp = null;
        if(this.left != null) {
            temp = this.left.sufixOrderSearch(id);
        }
        // 如果再左子树找到，则直接返回
        if(temp != null) {
            return temp;
        }
        // 如果在左子树没有找到，则在右子树查找
        if(this.right != null) {
            temp = this.right.sufixOrderSearch(id);
        }
        if(temp != null) {
            return temp;
        }

        System.out.println("后序遍历次数～");
        // 如果左右子树都没有找到，则匹配当前节点是否相等
        if(this.id == id) {
            return this;
        }
        return temp;
    }


    /**
     * 二叉树删除节点
     * 递归删除节点
     *
     * 1.如果删除的是叶子节点，则直接删除该节点
     * 2.如果删除的不是叶子节点，则直接删除该子树
     *
     */
    public void delNode(int id) {
        // 判断root节点是否是删除的节点在树对象中实现，这里无法操作root对象
        // 因为这个二叉树是单向的，所以无法删除当前节点，需要判断子节点是否是需要删除的节点，才可以删除

        // 1. 先判断左子节点是否是删除节点
        if(this.left != null && this.left.id == id) {
            this.left = null;
            return;
        }

        // 2. 判断右子节点是否是需要删除节点
        if(this.right != null && this.right.id == id) {
            this.right = null;
            return;
        }

        // 3.如果左子节点不是删除节点，则递归遍历左子树
        if(this.left != null) {
            this.left.delNode(id);
        }
        // 4.递归遍历右子树
        if(this.right != null) {
            this.right.delNode(id);
        }
    }


    /**
     * 二叉树删除节点
     *
     * 1. 如果删除是叶子节点，则直接删除
     * 2. 如果删除的是非叶子节点，如果该非叶子节点只有1个节点，则使用该子节点代替删除节点
     *                         如果该非叶子节点有2个节点，则使用左子节点代替删除节点
     *
     */
    public void delNode2(int id) {
        // 判断删除的节点是否是左子节点
        if(this.left != null && this.left.id == id) {
            // 如果该节点是叶子节点
            if(this.left.left == null && this.left.right == null) {
                this.left = null;
                return;
            }
            // 如果该节点的左子节点和右子节点都不为null
            if(this.left.left != null && this.left.right != null) {
                // 当前节点链接子节点的左节点
                this.left = this.left.left;
                // TODO 子节点的左子节点的右子节点怎么办？
                this.left.left.right = this.left.right;
                return;
            }
            // 如果只有一个子节点，则直接替换
            if(this.left.left != null) {
                this.left = this.left.left;
            } else {
                // 如果是右子节点不为空
                this.left = this.left.right;
            }
            return;
        }
        // 判断删除的节点是否为右子节点
        if(this.right != null && this.right.id == id) {
            // 如果该节点是叶子节点
            if(this.right.left == null && this.right.right == null) {
                this.right = null;
                return;
            }
            // 如果该子节点的左子节点和右子节点都不为null
            if(this.right.left != null && this.right.right != null) {
                // TODO 子节点的左子节点的右子节点怎么办？
                this.right.left.right = this.right.right;
                // 当前节点链接子节点的左节点
                this.right = this.right.left;
                return;
            }
            // 如果只有一个子节点，则直接替换
            if(this.right.left != null) {
                this.right = this.right.left;
            } else {
                // 如果是右子节点不为空
                this.right = this.right.right;
            }
            return;
        }

        // 递归遍历左树
        // 3.如果左子节点不是删除节点，则递归遍历左子树
        if(this.left != null) {
            this.left.delNode2(id);
        }
        // 4.递归遍历右子树
        if(this.right != null) {
            this.right.delNode2(id);
        }

    }

}
