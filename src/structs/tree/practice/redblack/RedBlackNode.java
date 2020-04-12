package structs.tree.practice.redblack;

import java.io.Serializable;
import java.util.Objects;

public class RedBlackNode<K, V> implements Serializable, Comparable<RedBlackNode<K, V>> {

    private K key;

    private V value;

    private int color;

    private RedBlackNode<K, V> pre;

    private RedBlackNode<K, V> left;

    private RedBlackNode<K, V> right;

    public RedBlackNode() {

    }

    public RedBlackNode(K key, V value, int color) {
        this.key = key;
        this.value = value;
        this.color = color;
    }

    public RedBlackNode(K key, V value, int color,
                        RedBlackNode<K, V> pre, RedBlackNode<K, V> left, RedBlackNode<K, V> right) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.pre = pre;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(RedBlackNode<K, V> o) {
        return this.hashCode() - o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj != null && obj.getClass() == getClass()) {
            RedBlackNode objNode = (RedBlackNode)obj;
            if(objNode.getKey().equals(getKey()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public RedBlackNode<K, V> getPre() {
        return pre;
    }

    public void setPre(RedBlackNode<K, V> pre) {
        this.pre = pre;
    }

    public RedBlackNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(RedBlackNode<K, V> left) {
        this.left = left;
    }

    public RedBlackNode<K, V> getRight() {
        return right;
    }

    public void setRight(RedBlackNode<K, V> right) {
        this.right = right;
    }
}
