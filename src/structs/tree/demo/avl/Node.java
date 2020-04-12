package structs.tree.demo.avl;

import java.io.Serializable;
import java.util.Objects;

public class Node<K, V> implements Serializable, Comparable<Node<K, V>> {

    private K key;

    private V value;

    private Node<K, V> pro;

    private Node<K, V> left;

    private Node<K, V> right;

    public Node() {}

    public Node(K key, V value,
                Node<K, V> pro, Node<K, V> left, Node<K, V> right) {
        this.key = key;
        this.value = value;
        this.pro = pro;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node<K, V> o) {
        return this.hashCode() - o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj != null && obj.getClass() == this.getClass()) {
            Node nodeObj = (Node)obj;
            return Objects.equals(nodeObj.getKey(), this.getKey());
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

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<K, V> getPro() {
        return pro;
    }

    public void setPro(Node<K, V> pro) {
        this.pro = pro;
    }

    public Node<K, V> getLeft() {
        return left;
    }

    public void setLeft(Node<K, V> left) {
        this.left = left;
    }

    public Node<K, V> getRight() {
        return right;
    }

    public void setRight(Node<K, V> right) {
        this.right = right;
    }
}
