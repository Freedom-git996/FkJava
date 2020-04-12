package structs.tree.demo.binary;

public class Node {

    public Node left, right;
    public char val;

    public Node() {
    }

    public Node(char val, Node left, Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
