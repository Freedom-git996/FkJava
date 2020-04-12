package structs.tree.demo.avl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Tree<K, V> implements Serializable, Iterable<Node<K, V>> {

    private static final int MAX_LEFT = 2;

    private static final int MAX_RIGHT = -2;

    private Node<K, V> root;

    private int size;

    public Tree() {
        this.size = 0;
    }

    public Node<K, V> getRoot() {
        return root;
    }

    public int size() {
        return size;
    }

    public void put(K key, V value) {
        Node<K, V> z = new Node<>();
        z.setKey(key);
        z.setValue(value);
        this.size++;

        Node<K, V> y = null;
        Node<K, V> x = root;

        while(x != null) {
            y = x;
            int sp = z.compareTo(x);
            if(sp < 0) {
                x = x.getLeft();
            }else if(sp > 0) {
                x = x.getRight();
            }else {
                x.setValue(value);
                this.size--;
                return;
            }
        }
        z.setPro(y);

        if(y == null) {
            root = z;
        }else if(z.compareTo(y) < 0) {
            y.setLeft(z);
        }else if(z.compareTo(y) > 0) {
            y.setRight(z);
        }

        fixup(z);
    }

    public void inorder(Node<K, V> x) {
        if(x != null) {
            inorder(x.getLeft());
            System.out.println(x.getKey() + ":" + x.getValue());
            inorder(x.getRight());
        }
    }

    public V get(K key) {
        Node<K, V> node = getNode(key);
        return node != null ? node.getValue() : null;
    }

    public Node<K, V> getNode(K key) {
        if(this.root == null) {
            return null;
        }
        Node<K, V> x = this.root;
        while(x != null && !x.getKey().equals(key)) {
            if(key.hashCode() - x.getKey().hashCode() < 0) {
                x = x.getLeft();
            }else if (key.hashCode() - x.getKey().hashCode() > 0) {
                x = x.getRight();
            }
        }
        return x;
    }

    public Node<K, V> getMaximum(Node<K, V> node) {
        while(node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    public Node<K, V> getMinimum(Node<K, V> node) {
        while(node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public Node<K, V> getSuccessor(Node<K, V> node) {
        if(node.getRight() != null) {
            Node<K, V> x = node.getRight();
            while(x.getLeft() != null) {
                x = x.getLeft();
            }
            return x;
        }
        Node<K, V> x = node.getPro();
        while(x != null && (node == x.getRight())) {
            node = x;
            x = x.getPro();
        }
        return x;
    }

    public Node<K, V> getPre(Node<K, V> node) {
        if(node.getLeft() != null) {
            Node<K, V> x = node.getRight();
            while(x.getLeft() != null) {
                x = x.getLeft();
            }
            return x;
        }
        Node<K, V> x = node.getPro();
        while(x != null && (node == x.getLeft())) {
            node = x;
            x = x.getPro();
        }
        return x;
    }

    public void remove(K key) {
        Node<K, V> z = getNode(key);
        Node<K, V> y = z;
        Node<K, V> x;

        if(z.getLeft() == null) {
            x = z.getRight();
            this.RBTransplant(z, z.getRight());
        }else if(z.getRight() == null) {
            x = z.getLeft();
            this.RBTransplant(z, z.getLeft());
        }else {
            y = this.getMinimum(z.getRight());
            x = y.getLeft();
            if(y.getPro().equals(z)) {
//                x.setPro(y);
            }else {
                this.RBTransplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setPro(y);
            }
            this.RBTransplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setPro(y);
        }

        this.size --;
        fixup(z);
    }

    private void RBTransplant(Node<K, V> u, Node<K, V> v) {
        if(u.getPro() == null) {
            this.root = v;
        }else if(u.equals(u.getPro().getLeft())) {
            u.getPro().setLeft(v);
        }else {
            u.getPro().setRight(v);
        }
        if(v != null) {
            v.setPro(u.getPro());
        }
    }

    private void fixup(Node<K, V> z) {
        Node<K, V> p = z.getPro();
        while(p != null) {
            if(calcBalanceFactor(p) == MAX_LEFT) {
                fixupAfterInsert(p, MAX_LEFT);
            }else if(calcBalanceFactor(p) == MAX_RIGHT) {
                fixupAfterInsert(p, MAX_RIGHT);
            }
            p = p.getPro();
        }
    }

    private void fixupAfterInsert(Node<K, V> x, int type) {
        if(type == MAX_LEFT) {
            Node<K, V> left = x.getLeft();
            if(left.getLeft() != null) {
                rightRotate(x);
            }else if (left.getRight() != null) {
                leftRotate(left);
                rightRotate(x);
            }
        }else if(type == MAX_RIGHT) {
            Node<K, V> right = x.getRight();
            if(right.getRight() != null) {
                leftRotate(x);
            }else if(right.getLeft() != null) {
                rightRotate(right);
                leftRotate(x);
            }
        }
    }

    private void leftRotate(Node<K, V> x) {
        Node<K, V> y = x.getRight();
        x.setRight(y.getLeft());

        if(y.getLeft() != null) {
            y.getLeft().setPro(x);
        }
        y.setPro(x.getPro());
        if(x.getPro() == null) {
            this.root = y;
        }else if(x == x.getPro().getLeft()) {
            x.getPro().setLeft(y);
        }else {
            x.getPro().setRight(y);
        }

        y.setLeft(x);
        x.setPro(y);
    }

    private void rightRotate(Node<K, V> x) {
        Node<K, V> y = x.getLeft();
        x.setLeft(y.getRight());

        if(y.getRight() != null) {
            y.getRight().setPro(x);
        }
        y.setPro(x.getPro());
        if(x.getPro() == null) {
            this.root = y;
        }else if(x == x.getPro().getLeft()) {
            x.getPro().setLeft(y);
        }else {
            x.getPro().setRight(y);
        }

        y.setRight(x);
        x.setPro(y);
    }

    private int getDepth(Node<K, V> node) {
        if(node == null) {
            return 0;
        }
        return 1 + Math.max(getDepth(node.getLeft()), getDepth(node.getRight()));
    }

    private int calcBalanceFactor(Node<K, V> node) {
        if(node == null) {
            return 0;
        }
        return getDepth(node.getLeft()) - getDepth(node.getRight());
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<Node<K, V>> {

        private List<Node<K, V>> array;

        private int cur;

        public Iter() {
            cur = 0;
            array = new ArrayList<>();
            Stack<Node<K, V>> stack = new Stack<>();
            Node<K, V> next = root;
            while(true) {
                while(next != null) {
                    stack.push(next);
                    next = next.getLeft();
                }
                if(stack.isEmpty()) break;
                next = stack.pop();
                array.add(next);
                next = next.getRight();
            }
        }

        @Override
        public boolean hasNext() {
            return cur < array.size();
        }

        @Override
        public Node<K, V> next() {
            Node<K, V> node = array.get(cur);
            cur ++;
            return node;
        }
    }
}
