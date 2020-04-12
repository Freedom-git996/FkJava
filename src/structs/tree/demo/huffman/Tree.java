package structs.tree.demo.huffman;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Tree<V> implements Serializable {

    private Integer key;

    private V value;

    public Node<V> create(List<Node<V>> nodes) {
        Collections.sort(nodes);
        while (nodes.size() > 1) {
            nodes = createAndReplace(nodes);
        }
        return nodes.get(0);
    }

    private List<Node<V>> createAndReplace(List<Node<V>> nodes) {
        Node<V> left = nodes.get(0);
        Node<V> right = nodes.get(1);

        Node<V> parent = new Node<>(left.getKey() + right.getKey(), null, left, right);

        nodes.remove(0);
        nodes.remove(0);
        nodes.add(parent);
        Collections.sort(nodes);
        return nodes;
    }

    public void printTree(Node<V> node) {
        Node<V> left = null;
        Node<V> right = null;
        if (node != null) {
            System.out.print(node.getValue());
            left = node.getLeft();
            right = node.getRight();
            System.out.println("(" + (left != null ? left.getValue() : " ") + "," + (right != null ? right.getValue() : " ") + ")");
        }
        if (left != null)
            printTree(left);
        if (right != null)
            printTree(right);
    }
}
