package structs.tree.demo.huffman;

import java.io.Serializable;
import java.util.Objects;

public class Node<V> implements Serializable, Comparable {

    private Integer key;

    private V value;

    private Node<V> left;

    private Node<V> right;

    public Node(Integer key, V value) {
        this.key = key;
        this.value = value;
    }

    public Node(Integer key, V value, Node<V> left, Node<V> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }


    @Override
    public int compareTo(Object o) {
        return this.hashCode() - o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj != null && obj.getClass() == this.getClass()) {
            Node objNode = (Node)obj;
            return Objects.equals(this.key, objNode.getKey());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<V> getLeft() {
        return left;
    }

    public void setLeft(Node<V> left) {
        this.left = left;
    }

    public Node<V> getRight() {
        return right;
    }

    public void setRight(Node<V> right) {
        this.right = right;
    }
}
