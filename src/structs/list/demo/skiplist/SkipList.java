package structs.list.demo.skiplist;

import java.util.Random;

public class SkipList<T> {

    private SkipListNode<T> head, tail;

    private int nodes; // 节点总数

    private int listLevel; // 层数

    private Random random; // 用于抛硬币

    private static final double PROBABILITY = 0.5; // 向上提升一个概率

    public SkipList() {
        random = new Random();
        clear();
    }

    // 清空跳跃表
    public void clear() {
        head = new SkipListNode<>(SkipListNode.HEAD_KEY, null);
        tail = new SkipListNode<>(SkipListNode.TAIL_KEY, null);
        horizontalLink(head, tail);
        listLevel = 0;
        nodes = 0;
    }

    public boolean isEmpty() {
        return nodes == 0;
    }

    public int size() {
        return nodes;
    }

    // 查找是否存储key, 存在则返回该节点, 否则返回null
    public SkipListNode<T> search(int key) {
        SkipListNode<T> p = findNode(key);
        if(key == p.getKey()) {
            return p;
        }else {
            return null;
        }
    }

    // 向跳跃表中添加 key-value
    public void put(int k, T v) {
        SkipListNode<T> p = findNode(k);
        if(k == p.getKey()) {
            p.value = v;
            return;
        }
        SkipListNode<T> q = new SkipListNode<>(k, v);
        backList(p, q);
        int currentLevle = 0; // 当前所在层级是0
        // 抛硬币
        while(random.nextDouble() < PROBABILITY) {
            if(currentLevle >= listLevel) {
                listLevel ++;
                SkipListNode<T> p1 = new SkipListNode<>(SkipListNode.HEAD_KEY, null);
                SkipListNode<T> p2 = new SkipListNode<>(SkipListNode.TAIL_KEY, null);
                horizontalLink(p1, p2);
                verticalLick(p1, head);
                verticalLick(p2, tail);
                head = p1;
                tail = p2;
            }

            // 将p移动到上一层
            while(p.up == null) {
                p = p.left;
            }
            p = p.up;

            SkipListNode<T> e= new SkipListNode<>(k, null);
            backList(p, e); // 将e插入到p的后面
            verticalLick(e, q); // 将e和q上下链接
            q = e;
            currentLevle ++;
        }
        nodes ++;
    }

    /**
     * 打印出原始数据
     * */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        if (isEmpty()) {
            return "跳跃表为空！";
        }
        StringBuilder builder=new StringBuilder();
        SkipListNode<T> p=head;
        while (p.down!=null) {
            p=p.down;
        }

        while (p.left!=null) {
            p=p.left;
        }
        if (p.right!=null) {
            p=p.right;
        }
        while (p.right!=null) {
            builder.append(p);
            builder.append("\n");
            p=p.right;
        }

        return builder.toString();
    }

    // node1 后面插入node2
    private void backList(SkipListNode<T> node1, SkipListNode<T> node2) {
        node2.left = node1;
        node2.right = node1.right;
        node1.right.left = node2;
        node1.right = node2;
    }

    // 在最底层, 找到要插入的位置前面的那个 key
    private SkipListNode<T> findNode(int key) {
        SkipListNode<T> p = head;
        while(true) {
            while(p.right.key != SkipListNode.TAIL_KEY && p.right.key <= key) {
                p = p.right;
            }
            if(p.down != null) {
                p = p.down;
            }else {
                break;
            }
        }
        return p;
    }

    // 水平双向链接
    private void horizontalLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node1.right = node2;
        node2.left = node1;
    }

    // 垂直链接
    private void verticalLick(SkipListNode<T> node1, SkipListNode<T> node2) {
        node1.down = node2;
        node2.up = node1;
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SkipList<String> list=new SkipList<String>();
//        System.out.println(list);
        list.put(2, "yan");
        list.put(1, "co");
        list.put(3, "feng");
        list.put(1, "cao");//测试同一个key值
        list.put(4, "曹");
        list.put(6, "丰");
        list.put(5, "艳");
        System.out.println(list);
        System.out.println(list.size());
    }
}
