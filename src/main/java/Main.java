import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        // 3 -> 2 -> 1
        Node node = new Node(3);
        node.next = new Node(2);
        node.next.next = new Node(1);

        rotateRight(node, 4);

        int num = getNum(node);
        System.out.println(num);

        int numWithCurstion = getNumWithCurstion(node);
        System.out.println(numWithCurstion);

        int num2 = getNum2(node);
        System.out.println(num2);


    }

    public static int getNumWithCurstion(Node node) {
        if(node == null) {
            return 0;
        }
        return getNumWithCurstion(node.next) * 10 + node.val;
    }

    public static int getNum(Node node) {
        if(node == null) {
            return -1;
        }

        Stack<Integer> stack = new Stack<Integer>();
        while(node != null) {
            stack.push(node.val);
            node = node.next;
        }

        int sum = 0;
        while(!stack.isEmpty()) {
            sum = sum * 10 + stack.pop();
        }
        return sum;
    }


    public static int getNum2(Node node) {
        Node pre = null;
        Node next = null;

        Node cur = node;
        while(cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        int sum = 0;

        while(pre != null) {
            sum = sum * 10 + pre.val;
            pre = pre.next;
        }

        return sum;
    }

    static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    public static Node rotateRight(Node head, int k) {
        Node pre = null;
        Node cur = head;
        Node fast = head;

        for(int i = 1; i < k; i++) {
            fast = fast.next;
            if(fast == null) {
                fast = head;
            }
        }
        while(fast != null && fast.next != null) {
            pre = cur;
            cur = cur.next;
            fast = fast.next;    ;
        }
        if(pre != null) {
            pre.next = null;
            fast.next = head;
        }

        return cur;
    }
}
