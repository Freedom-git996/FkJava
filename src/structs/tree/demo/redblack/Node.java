package structs.tree.demo.redblack;

import java.io.Serializable;
import java.util.Objects;

public class Node<K, V> implements Serializable, Comparable<Node<K, V>> {

    private int color;

    private K key;

    private V value;

    private Node<K, V> pro;

    private Node<K, V> left;

    private Node<K, V> right;

    public Node() {}

    public Node(int color, K key, V value,
                Node<K, V> pro, Node<K, V> left, Node<K, V> right) {
        this.color = color;
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
        if(this == obj) return true;
        if(obj != null && obj.getClass() == this.getClass()) {
            Node nodeObj = (Node)obj;
            return Objects.equals(key, nodeObj.key);
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
