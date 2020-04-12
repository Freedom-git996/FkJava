package structs.tree.practice.redblack;

import java.io.Serializable;
import java.util.*;

public class RedBlackTree<K, V> implements Serializable, Iterable<RedBlackNode<K, V>> {

    private RedBlackNode<K, V> root;

    private int size;

    private int BLACK = 0;

    private int RED = 1;

    public RedBlackTree() {
        this.size = 0;
    }

    public void put(K key, V value) {
        RedBlackNode<K, V> current = new RedBlackNode<>(key, value, RED);
        this.size ++;

        RedBlackNode<K, V> prev = null;
        RedBlackNode<K, V> next = root;
        while(next != null) {
            prev = next;
            if(next.compareTo(current) > 0) {
                next = next.getLeft();
            }else if(next.compareTo(current) < 0) {
                next = next.getRight();
            }else {
                next.setValue(value);
                size --;
                return;
            }
        }

        current.setPre(prev);
        if(prev == null) {
            root = current;
        }else if(prev.compareTo(current) > 0) {
            prev.setLeft(current);
        }else if(prev.compareTo(current) < 0) {
            prev.setRight(current);
        }

        fixPut(current);
    }

    // 情况一 c = root : 新增插入节点默认为红色，修改C当前节点颜色为黑色
    // 情况二 c.parent = black : 新增节点C, 颜色为红色
    // 情况三 c.parent = red && c.uncle = red : 新增C当前节点默认为红色, P父节点变成黑色, U叔叔节点变成黑色, G祖父节点变为红色
    //          如果祖父节点变为红色不满足红黑树的性质, 将祖父节点作为新增节点C, 递归处理1234情况
    // 情况四 c.parent = red && (c.uncle = black || c.uncle is null)
    //          情况一 : CPG三点一线 : 以P父节点为圆心, 旋转G点, 变色P父节点、G祖父节点两点
    //          情况二 : CPG三角关系 : 以C当前新增节点为圆心, 旋转P点, 转为CPG三点一线, 在按三点一线处理
    public void fixPut(RedBlackNode<K, V> current) {
        while(current.getPre() != null && current.getPre().getColor() == RED) {
            if(current.getPre().getPre() != null) {
                if(current.getPre().equals(current.getPre().getPre().getLeft())) {
                    RedBlackNode<K, V> uncle = current.getPre().getPre().getRight();
                    if(uncle != null && uncle.getColor() == RED) {
                        uncle.setColor(BLACK);
                        current.getPre().setColor(BLACK);
                        current.getPre().getPre().setColor(RED);
                        current = current.getPre().getPre();
                    }else {
                        if(current.equals(current.getPre().getRight())) {
                            current = current.getPre();
                            this.leftRotate(current);
                        }
                        current.getPre().setColor(BLACK);
                        current.getPre().getPre().setColor(RED);
                        this.rightRotate(current.getPre().getPre());
                    }
                }else {
                    RedBlackNode<K, V> uncle = current.getPre().getPre().getLeft();
                    if(uncle != null && uncle.getColor() == RED) {
                        uncle.setColor(BLACK);
                        current.getPre().setColor(BLACK);
                        current.getPre().getPre().setColor(RED);
                        current = current.getPre().getPre();
                    }else {
                        if(current.equals(current.getPre().getLeft())) {
                            current = current.getPre();
                            this.rightRotate(current);
                        }
                        current.getPre().setColor(BLACK);
                        current.getPre().getPre().setColor(RED);
                        this.leftRotate(current.getPre().getPre());
                    }
                }
            }
        }
        this.root.setColor(BLACK);
    }

    private void leftRotate(RedBlackNode<K, V> current) {
        RedBlackNode<K, V> rightChild = current.getRight();

        current.setRight(rightChild.getLeft());
        if(rightChild.getLeft() != null) {
            rightChild.getLeft().setPre(current);
        }

        rightChild.setPre(current.getPre());
        if(current.getPre() == null) {
            root = rightChild;
        }else if(rightChild.compareTo(current.getPre()) < 0) {
            current.getPre().setLeft(rightChild);
        }else if(rightChild.compareTo(current.getPre()) > 0) {
            current.getPre().setRight(rightChild);
        }

        rightChild.setLeft(current);
        current.setPre(rightChild);
    }

    private void rightRotate(RedBlackNode<K, V> current) {
        RedBlackNode<K, V> leftChild = current.getLeft();

        current.setLeft(leftChild.getRight());
        if(leftChild.getRight() != null) {
            leftChild.getRight().setPre(current);
        }

        leftChild.setPre(current.getPre());
        if(current.getPre() == null) {
            root = leftChild;
        }else if(leftChild.compareTo(current.getPre()) < 0) {
            current.getPre().setLeft(leftChild);
        }else if(leftChild.compareTo(current.getPre()) > 0) {
            current.getPre().setRight(leftChild);
        }

        leftChild.setRight(current);
        current.setPre(leftChild);
    }

    public void inorder(RedBlackNode<K, V> current) {
        if(current != null) {
            inorder(current.getLeft());
            System.out.println(current.getKey() + ":" + current.getValue());
            inorder(current.getRight());
        }
    }

    public V get(K key) {
        RedBlackNode<K, V> target = getRedBlackNode(key);
        return target == null ? null : target.getValue();
    }

    public RedBlackNode<K, V> getRedBlackNode(K key) {
        if(this.getRoot() == null) {
            return null;
        }
        RedBlackNode<K, V> next = root;
        while(next != null && !next.getKey().equals(key)) {
            if(key.hashCode() - next.getKey().hashCode() < 0) {
                next = next.getLeft();
            }else if (key.hashCode() - next.getKey().hashCode() > 0) {
                next = next.getRight();
            }
        }
        return next;
    }

    public int getSize() {
        return size;
    }

    public RedBlackNode<K, V> getRoot() {
        return root;
    }

    @Override
    public Iterator<RedBlackNode<K, V>> iterator() {
        return new Ite();
    }

    private class Ite implements Iterator<RedBlackNode<K, V>> {

        private List<RedBlackNode> array = null;

        int count = 0;

        public Ite() {
            count = 0;
            array = new ArrayList<>();
            Stack<RedBlackNode> stack = new Stack<>();
            RedBlackNode<K, V> next = root;
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
            return count < array.size();
        }

        @Override
        public RedBlackNode<K, V> next() {
            RedBlackNode<K, V> node = array.get(count);
            count ++;
            return node;
        }
    }

    // TODO 删除操作
}
