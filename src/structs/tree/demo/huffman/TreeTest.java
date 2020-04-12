package structs.tree.demo.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeTest {

    public static void main(String[] args) {
        List<Node<String>> list = new ArrayList<>();
        Node<String> node1 = new Node<>(4, "aa");
        Node<String> node2 = new Node<>(7, "bb");
        Node<String> node3 = new Node<>(2, "cc");
        Node<String> node4 = new Node<>(5, "dd");
        Node<String> node5 = new Node<>(1, "ff");
        list.add(node1);
        list.add(node2);
        list.add(node3);
        list.add(node4);
        list.add(node5);
        Collections.sort(list);

        Tree<String> tree = new Tree<>();
        Node<String> root = tree.create(list);

        list.forEach(System.out::println);

        tree.printTree(root);
    }
}
