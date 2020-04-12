package structs.stack.demo;

public class LinkStack<T> {

    private Node top;
    private int size;

    public LinkStack() {
        top = null;
    }

    public LinkStack(T element) {
        top = new Node(element, null);
        size ++;
    }

    public int length() {
        return size;
    }

    public void push(T element) {
        top = new Node(element, top);
        size ++;
    }

    public T pop() {
        Node oldTop = top;
        top = top.next;
        oldTop.next = null;
        size --;
        return oldTop.data;
    }

    public T peek() {
        return top.data;
    }

    public boolean empty() {
        return size == 0;
    }

    public void clear() {
        for(Node x = top; x != null;) {
            Node next = x.next;
            x.data = null;
            x.next = null;
            x = next;
        }
        size = 0;
    }

    @Override
    public String toString() {
        if(empty()) {
            return "[]";
        }else {
            StringBuilder sb = new StringBuilder("[");
            for(Node x = top; x != null; x = x.next) {
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
        LinkStack<String> stack = new LinkStack<>();
        stack.push("aaa");
        stack.push("bbb");
        stack.push("ccc");
        stack.push("ddd");
        System.out.println(stack);
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack);
    }
}
