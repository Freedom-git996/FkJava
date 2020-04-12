package structs.tree.demo.redblack;

import com.sun.org.apache.regexp.internal.RE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Tree<K, V> implements Serializable, Iterable<Node<K, V>> {

    private Node<K, V> root;

    private int size;

    private int BLACK = 0;

    private int RED = 1;

    public Tree() {
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public Node<K, V> getRoot() {
        return root;
    }

    public void put(K key, V value) {
        Node<K, V> z = new Node<>();
        z.setKey(key);
        z.setValue(value);
        z.setColor(RED);
        this.size ++;

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
                this.size --;
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

        this.fixup(z);
    }

    // 情况一 c = root : 新增插入节点默认为红色，修改C当前节点颜色为黑色
    // 情况二 c.parent = black : 新增节点C, 颜色为红色
    // 情况三 c.parent = red && c.uncle = red : 新增C当前节点默认为红色, P父节点变成黑色, U叔叔节点变成黑色, G祖父节点变为红色
    //          如果祖父节点变为红色不满足红黑树的性质, 将祖父节点作为新增节点C, 递归处理1234情况
    // 情况四 c.parent = red && (c.uncle = black || c.uncle is null)
    //          情况一 : CPG三点一线 : 以P父节点为圆心, 旋转G点, 变色P父节点、G祖父节点两点
    //          情况二 : CPG三角关系 : 以C当前新增节点为圆心, 旋转P点, 转为CPG三点一线, 在按三点一线处理
    private void fixup(Node<K, V> z) {
        // 默认新增节点 z, 即 C->RED
        while(z.getPro() != null && z.getPro().getColor() == RED) {
            // P->RED
            if(z.getPro().getPro() != null) {
                if(z.getPro().equals(z.getPro().getPro().getLeft())) {
                    // 如果 P=G.left
                    Node<K, V> y = z.getPro().getPro().getRight();
                    if(y != null && y.getColor() == RED) {
                        // 如果 P->RED && U->RED
                        // 那么 P->BLACK, U->BLACK, G-BLACK, z = G, 继续 while
                        z.getPro().setColor(BLACK);
                        y.setColor(BLACK);
                        z.getPro().getPro().setColor(RED);
                        z = z.getPro().getPro();
                    }else {
                        // 如果 P->RED, U->BLACK
                        if(z.equals(z.getPro().getRight())) {
                            // 如果 C=P.right
                            z = z.getPro();
                            this.leftRotate(z);
                        }
                        z.getPro().setColor(BLACK);
                        z.getPro().getPro().setColor(RED);
                        this.rightRotate(z.getPro().getPro());
                    }
                }else if(z.getPro().equals(z.getPro().getPro().getRight())) {
                    // 如果 P=G.right
                    Node<K, V> y = z.getPro().getPro().getLeft();
                    if(y != null && y.getColor() == RED) {
                        // 如果 P->RED && U->RED
                        z.getPro().setColor(BLACK);
                        y.setColor(BLACK);
                        z.getPro().getPro().setColor(RED);
                        z = z.getPro().getPro();
                    }else {
                        // 如果 P->RED && U->BLACK
                        if(z.equals(z.getPro().getLeft())) {
                            // 如果 C=P.left
                            z = z.getPro();
                            this.rightRotate(z);
                        }
                        z.getPro().setColor(BLACK);
                        z.getPro().getPro().setColor(RED);
                        this.leftRotate(z.getPro().getPro());
                    }
                }
            }
        }

        this.root.setColor(BLACK);
    }

    // x --> P 圆心
    // y --> C
    private void leftRotate(Node<K, V> x) {
        Node<K, V> y = x.getRight();
        x.setRight(y.getLeft());

        if(y.getLeft() != null) {
            y.getLeft().setPro(x);
        }
        y.setPro(x.getPro());
        if(x.getPro() == null) {
            this.root = y;
        }else if(x.equals(x.getPro().getLeft())) {
            x.getPro().setLeft(y);
        }else {
            x.getPro().setRight(y);
        }

        y.setLeft(x);
        x.setPro(y);
    }

    // x --> G
    // y --> P 圆心
    private void rightRotate(Node<K, V> x) {
        Node<K, V> y = x.getLeft();
        x.setLeft(y.getRight());

        if(y.getRight() != null) {
            y.getRight().setPro(x);
        }
        y.setPro(x.getPro());
        if(x.getPro() == null) {
            this.root = y;
        }else if(x.equals(x.getPro().getLeft())) {
            x.getPro().setLeft(y);
        }else {
            x.getPro().setRight(y);
        }

        y.setRight(x);
        x.setPro(y);
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

    // 中序遍历
    public void inorder(Node<K, V> x) {
        if(x != null) {
            inorder(x.getLeft());
            System.out.println(x.getKey() + ":" + x.getValue());
            inorder(x.getRight());
        }
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<Node<K, V>> {

        List<Node<K, V>> array;

        int cur;

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

    public void remove(K key) {
        Node<K, V> z = getNode(key);
        Node<K, V> y = z;
        Node<K, V> x;

        int oColor = y.getColor();

        if(z.getLeft() == null) {
            x = z.getRight();
            this.RBTransplant(z, z.getRight());
        }else if(z.getRight() == null) {
            x = z.getLeft();
            this.RBTransplant(z, z.getLeft());
        }else {
            y = this.getMinimum(z.getRight());
            oColor = y.getColor();
            x = y.getLeft();
            if(y.getPro().equals(z)) {
                x.setPro(y);
            }else {
                this.RBTransplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setPro(y);
            }
            this.RBTransplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setPro(y);
            y.setColor(z.getColor());
        }

        if(oColor == this.BLACK) {
            this.RBRemoveFixup(x);
        }

        this.size --;
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

    private void RBRemoveFixup(Node<K, V> x) {
        while(x != null && !x.equals(this.root) && x.getColor() == this.BLACK) {
            if(x.equals(x.getPro().getLeft())) {
                Node<K, V> w = x.getPro().getRight();
                if(w.getColor() == RED) {
                    w.setColor(BLACK);
                    x.getPro().setColor(RED);
                    this.leftRotate(x.getPro());
                    w = x.getPro().getRight();
                }

                if(w.getLeft().getColor() == BLACK && w.getRight().getColor() == BLACK) {
                    w.setColor(RED);
                    x = x.getPro();
                }else if(w.getRight().getColor() == BLACK) {
                    w.getLeft().setColor(BLACK);
                    w.setColor(RED);
                    this.rightRotate(w);
                    w = x.getPro().getRight();
                }

                w.setColor(x.getPro().getColor());
                x.getPro().setColor(BLACK);
                w.getRight().setColor(BLACK);
                this.leftRotate(x.getPro());
                x = root;
            }else {
                Node<K, V> w = x.getPro().getLeft();
                if(w.getColor() == this.RED) {
                    w.setColor(BLACK);
                    x.getPro().setColor(RED);
                    this.rightRotate(x.getPro());
                    w = x.getPro().getLeft();
                }

                if (w.getRight().getColor() == this.BLACK && w.getLeft().getColor() == this.BLACK) {
                    w.setColor(RED);
                    x = x.getPro();
                } else if (w.getLeft().getColor() == BLACK) {
                    w.getRight().setColor(BLACK);
                    w.setColor(RED);
                    this.leftRotate(x);
                    w = x.getPro().getLeft();
                }

                w.setColor(x.getPro().getColor());
                x.getPro().setColor(BLACK);
                w.getLeft().setColor(BLACK);
                this.rightRotate(x.getPro());
                x = root;
            }
        }
        if(x != null) {
            x.setColor(BLACK);
        }
    }
}
