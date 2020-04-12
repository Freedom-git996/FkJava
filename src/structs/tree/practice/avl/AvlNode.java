package structs.tree.practice.avl;

import java.io.Serializable;
import java.util.Objects;

public class AvlNode<K, V> implements Serializable, Comparable<AvlNode<K, V>> {

    private K key;

    private V value;

    private AvlNode<K, V> pro;

    private AvlNode<K, V> left;

    private AvlNode<K, V> right;

    public AvlNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public AvlNode(K key, V value,
                   AvlNode<K, V> pro, AvlNode<K, V> left, AvlNode<K, V> right) {
        this.key = key;
        this.value = value;
        this.pro = pro;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(AvlNode<K, V> o) {
        return this.hashCode() - o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj != null && obj.getClass() == this.getClass()) {
            AvlNode objNode = (AvlNode)obj;
            return Objects.equals(this.getKey(), objNode.getKey());
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

    public AvlNode<K, V> getPro() {
        return pro;
    }

    public void setPro(AvlNode<K, V> pro) {
        this.pro = pro;
    }

    public AvlNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(AvlNode<K, V> left) {
        this.left = left;
    }

    public AvlNode<K, V> getRight() {
        return right;
    }

    public void setRight(AvlNode<K, V> right) {
        this.right = right;
    }
}
