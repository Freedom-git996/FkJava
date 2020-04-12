package structs.list.demo;

public class LinkList<T> {

    private Node header;
    private Node tail;
    private int size;

    public LinkList() {
        header = null;
        tail = null;
    }

    public LinkList(T element) {
        header = new Node(element, null);
        tail = header;
        size ++;
    }

    public int length() {
        return size;
    }

    public T get(int index) {
        return getNodeByIndex(index).data;
    }

    public int locate(T element) {
        Node current = header;
        for(int i = 0; i < size && current != null; i ++, current = current.next) {
            if(current.data.equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public void insert(T element, int index) {
        if(index < 0 && index > size) {
            throw new IndexOutOfBoundsException("线性表索引越界");
        }
        if(header == null) {
            add(element);
        }else {
            if(index == 0) {
                addAtHeader(element);
            }else {
                Node prev = getNodeByIndex(index - 1);
                prev.next = new Node(element, prev.next);
                size ++;
            }
        }
    }

    public void add(T element) {
        if(header == null) {
            header = new Node(element, null);
            tail = header;
        }else {
            Node newNode = new Node(element, null);
            tail.next = newNode;
            tail = newNode;
        }
        size ++;
    }

    public void addAtHeader(T element) {
        header = new Node(element, header);
        if(tail == null) {
            tail = header;
        }
        size ++;
    }

    public T delete(int index) {
        if(index < 0 && index > size - 1) {
            throw new IndexOutOfBoundsException("线性表索引越界");
        }
        Node del = null;
        if(index == 0) {
            del = header;
            header = header.next;
        }else {
            Node prev = getNodeByIndex(index - 1);
            del = prev.next;
            prev.next = del.next;
            del.next = null;
        }
        size --;
        return del.data;
    }

    public T remove() {
        return delete(size - 1);
    }

    public boolean empty() {
        return this.size == 0;
    }

    public void clear() {
        for(Node x = header; x != null;) {
            Node next = x.next;
            x.data = null;
            x.next = null;
            x = next;
        }
        header = null;
        tail = null;
        size = 0;
    }

    private Node getNodeByIndex(int index) {
        if(index < 0 && index > size - 1) {
            throw new IndexOutOfBoundsException("线性表索引越界");
        }
        Node current = header;
        for(int i = 0; i < size && current != null; i ++, current = current.next) {
            if(i == index) {
                return current;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if(empty()) {
            return "[]";
        }else {
            StringBuilder sb = new StringBuilder("[");
            for(Node x = header; x != null; x = x.next) {
                sb.append(x.data.toString()).append(",");
            }
            int len = sb.length();
            return sb.delete(len - 1, len).append("]").toString();
        }
    }

    private class Node {

        private T data;
        private Node next;

        public Node() {

        }

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkList<String> linkList = new LinkList<>();
        linkList.insert("aaa", 0);
        linkList.add("bbb");
        linkList.add("ccc");
        linkList.insert("ddd", 1);
        System.out.println(linkList);
        linkList.delete(2);
        System.out.println(linkList);
        System.out.println(linkList.locate("ccc"));
        System.out.println(linkList.get(2));
        linkList.clear();
        System.out.println(linkList);
    }
}
