package structs.queue.demo;

public class LinkQueue<T> {

    private Node front;
    private Node rear;
    private int size;

    public LinkQueue() {
        front = null;
        rear = null;
    }

    public LinkQueue(T element) {
        front = new Node(element, null);
        rear = front;
        size ++;
    }

    public int length() {
        return size;
    }

    public void add(T element) {
        if(front == null) {
            front = new Node(element, null);
            rear = front;
        }else {
            Node newNode = new Node(element, null);
            rear.next = newNode;
            rear = newNode;
        }
        size ++;
    }

    public T remove() {
        Node oldVal = front;
        front = front.next;
        oldVal.next = null;
        size --;
        return oldVal.data;
    }

    public T element() {
        return front.data;
    }

    public boolean empty() {
        return size == 0;
    }

    public void clear() {
        for(Node x = front; x != null;) {
            Node next = x.next;
            x.data = null;
            x.next = null;
            x = next;
        }
        front = null;
        rear = null;
        size = 0;
    }

    @Override
    public String toString() {
        if(empty()) {
            return "[]";
        }else {
            StringBuilder sb = new StringBuilder("[");
            for(Node x = front; x != null; x = x.next) {
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
        LinkQueue<String> queue = new LinkQueue<>();
        queue.add("aaa");
        queue.add("bbb");
        queue.add("ccc");
        queue.add("ddd");
        queue.add("eee");
        queue.add("fff");
        queue.add("ggg");
        System.out.println(queue);
        System.out.println(queue.element());

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue);

        queue.add("aaa");
        queue.add("bbb");
        queue.add("ccc");
    }
}
